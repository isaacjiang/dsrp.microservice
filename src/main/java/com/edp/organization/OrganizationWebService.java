package com.edp.organization;


import com.edp.interfaces.MicroServiceInterface;
import com.edp.organization.models.Company;
import com.edp.organization.models.Group;
import com.edp.organization.models.SecUser;
import com.edp.organization.models.SecUserRepo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;


@Service
public class OrganizationWebService  {

    @Autowired
    OrganizationDataService organizationDataService;

    Mono<ServerResponse> userNotFound = ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(new SecUser()), SecUser.class);

    public OrganizationWebService() {}


    /**
     * GET ALL Users info from database
     */

    public Mono<ServerResponse> getAllSecUsers(ServerRequest request) {
        Flux<SecUser> secUserFlux = organizationDataService.getAllSecUsers();

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(secUserFlux, SecUser.class);
    }


    public Mono<ServerResponse> getAllGroups(ServerRequest request) {
        Flux<Group> groupFlux = organizationDataService.getAllGroups().filter(group -> !group.getAdmin()).sort(Comparator.comparing(Group::getId));

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(groupFlux, Group.class);
    }

    public Mono<ServerResponse> getBaseCompanies(ServerRequest request) {
        Flux<Company> companyFlux = organizationDataService.getBaseCompanies().sort(Comparator.comparing(Company::getId));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(companyFlux, Company.class);
    }
    public Mono<ServerResponse> getAllCompanies(ServerRequest request) {
        Flux<Company> companyFlux = organizationDataService.getAllCompanies().sort(Comparator.comparing(Company::getId));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(companyFlux, Company.class);
    }



    /**
     * if the user already logged in, the system will login in without system check
     * otherwise will check the database and return the status of logging user
     * if username is not found will return an anonymous user otherwise return username + encoded passwword
     */
    public Mono<ServerResponse> getSecUserStatus(ServerRequest request) {

        //System.out.println(request.pathVariables());
        if(request.pathVariables().get("username") == null){
            return userNotFound;
        }
        else {
            String username = request.pathVariable("username");
//            System.out.println(username+"==================");
            Mono<SecUser> user = organizationDataService.getUser(username).map(secUser ->  secUser.setAuthenticated(true)).switchIfEmpty(Mono.just(new SecUser()));
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(user, SecUser.class);
        }
    }


    /**
     * Logout User
     */

    public Mono<ServerResponse> UserLogout(ServerRequest request) {
        System.out.println("logging out");
        return userNotFound;
    }

    /**
     * success login
     */
    public Mono<ServerResponse> getUserSuccessStatus(ServerRequest request) {
            String username = String.valueOf(request.headers().asHttpHeaders().get("Authorization")).replace("[Basic ", "").replace("]", "");
            String username1 = new String(Base64.getDecoder().decode(username)).split(":")[0];
            System.out.println(username1 + "----------------------------------- Authenticate Success.");
            Mono<SecUser> user = organizationDataService.getUser(username1).map(secUser ->  secUser.setAuthenticated(true));
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(user, SecUser.class);

    }

    /**
     * failure login
     */
    public Mono<ServerResponse> getUserFailureStatus(ServerRequest request) {
            // System.out.println(request.headers().asHttpHeaders().get("Authorization"));

            String username = String.valueOf(request.headers().asHttpHeaders().get("Authorization")).replace("[Basic ", "").replace("]", "");
            String username1 = new String(Base64.getDecoder().decode(username)).split(":")[0];
            System.out.println(username1 + "----------------------------------- Authenticate Failure.");
            Mono<SecUser> user = organizationDataService.getUser(username1).map(secUser ->  secUser.setAuthenticated(false)).switchIfEmpty(Mono.just(new SecUser()));
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(user, SecUser.class);


    }



    /**
     * register user with user name, password, email, license
     * the user will be set to ROLE_USER
     */

    public Mono<ServerResponse> registerUser(ServerRequest request) {
        Mono<SecUser> secUserMono = request.bodyToMono(SecUser.class).map(user->{
            String role = "ROLE_USER";
            if(user.getPermission().equals("0")){role ="ROLE_ADMIN";}
            return new SecUser(user.getUsername(),user.getPassword(),Collections.singleton(new SimpleGrantedAuthority(role)));
        }).cache();
        organizationDataService.saveUser(secUserMono);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(secUserMono, SecUser.class).switchIfEmpty(userNotFound);
    }

    public Mono<ServerResponse> userJoinGroup(ServerRequest request) {
        Mono<SecUser> secUserMono = request.bodyToMono(SecUser.class)
                .flatMap(user-> organizationDataService.getUser(user.getUsername()).map(user1 -> user1.setCompanyId(user.getCompanyId()).setGroupId(user.getGroupId()))).cache();
        organizationDataService.saveUser(secUserMono);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(secUserMono, SecUser.class).switchIfEmpty(userNotFound);
    }

    /**
     * delete user by username
     */
    public Mono<ServerResponse> deleteUser(ServerRequest request) {
//        String username = request.pathVariable("username");
//
//        Mono<SecUser> secuser = organizationDataService.secUserRepo.getSecUserByUsername(username);
//        SecUser secuser1 = secuser.block();
//        organizationDataService.secUserRepo.deleteAll(Flux.just(secuser1)).subscribe(); // delete user
//
//        //return value doesnot matter

        return userNotFound;

    }

    /**
     * update user information
     * first delete the user from database and then insert the updated user into database
     */
    public Mono<ServerResponse> modifyUser(ServerRequest request) {

        request.bodyToMono(String.class).subscribe(d -> {
//            JSONObject data = new JSONObject(d);
//            String username = data.getString("key");
//            String email = data.getString("email");
//
//            Mono<SecUser> secuser = organizationDataService.secUserRepo.getSecUserByUsername(username);
//            SecUser secuser1 = secuser.block();
//            organizationDataService.secUserRepo.deleteAll(Flux.just(secuser1)).subscribe(); // delete the old user info
//
//            //secuser1.setEmail(email);//update email
//            organizationDataService.secUserRepo.saveAll(Flux.just(secuser1)).subscribe(); // insert the new user info
        });


        return userNotFound;
    }


}
