package com.edp.organization;


import com.edp.organization.models.Company;
import com.edp.organization.models.CompanySummary;
import com.edp.organization.models.Group;
import com.edp.organization.models.SecUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrganizationWebService {

    @Autowired
    OrganizationDataService organizationDataService;

    Mono<ServerResponse> userNotFound = ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(new SecUser()), SecUser.class);

    public OrganizationWebService() {
    }


    /**
     * GET ALL Users info from database
     */

    public Mono<ServerResponse> getAllSecUsers(ServerRequest request) {
        List<SecUser> secUserList = organizationDataService.getAllSecUsers();

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Flux.fromIterable(secUserList), SecUser.class);
    }


    public Mono<ServerResponse> getAllGroups(ServerRequest request) {
        List<Group> groupList = organizationDataService.getAllGroups().stream().filter(group -> !group.getAdmin()).sorted(Comparator.comparing(Group::getId)).collect(Collectors.toList());

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Flux.fromIterable(groupList), Group.class);
    }

    public Mono<ServerResponse> getBaseCompanies(ServerRequest request) {
        List<Company> companyList = organizationDataService.getBaseCompanies().stream().sorted(Comparator.comparing(Company::getId)).collect(Collectors.toList());
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Flux.fromIterable(companyList), Company.class);
    }

    public Mono<ServerResponse> getAllCompanies(ServerRequest request) {
        List<Company> companyList = organizationDataService.getAllCompanies().stream().sorted(Comparator.comparing(Company::getId)).collect(Collectors.toList());
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Flux.fromIterable(companyList), Company.class);
    }

    public Mono<ServerResponse> getCompanySummary(ServerRequest request) {

        if (request.pathVariables().get("companyId") == null || request.pathVariables().get("companyId").equals("000")) {
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(new CompanySummary()), CompanySummary.class);
        } else {
            String companyId = request.pathVariable("companyId");
            System.out.println("=================" + request.pathVariable("companyId"));
            CompanySummary companySummarry = organizationDataService.getCompanySummarry(companyId);
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(companySummarry), CompanySummary.class);
        }

    }


    /**
     * if the user already logged in, the system will login in without system check
     * otherwise will check the database and return the status of logging user
     * if username is not found will return an anonymous user otherwise return username + encoded passwword
     */
    public Mono<ServerResponse> getSecUserStatus(ServerRequest request) {

        //System.out.println(request.pathVariables());
        if (request.pathVariables().get("username") == null) {
            return userNotFound;
        } else {
            String username = request.pathVariable("username");
//            System.out.println(username+"==================");
            SecUser secUser = organizationDataService.getUser(username);
            if (secUser != null) {
                secUser.setAuthenticated(true);
            } else {
                secUser = new SecUser();
            }
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(secUser), SecUser.class);
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
        SecUser secUser = organizationDataService.getUser(username1);
        if (secUser != null) {
            secUser.setAuthenticated(true);
        } else {
            secUser = new SecUser();
        }
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(secUser), SecUser.class);

    }

    /**
     * failure login
     */
    public Mono<ServerResponse> getUserFailureStatus(ServerRequest request) {
        // System.out.println(request.headers().asHttpHeaders().get("Authorization"));

        String username = String.valueOf(request.headers().asHttpHeaders().get("Authorization")).replace("[Basic ", "").replace("]", "");
        String username1 = new String(Base64.getDecoder().decode(username)).split(":")[0];
        System.out.println(username1 + "----------------------------------- Authenticate Failure.");
        SecUser secUser = organizationDataService.getUser(username1);
        if (secUser != null) {
            secUser.setAuthenticated(false);
        } else {
            secUser = new SecUser();
        }
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(secUser), SecUser.class);


    }


    /**
     * register user with user name, password, email, license
     * the user will be set to ROLE_USER
     */

    public Mono<ServerResponse> registerUser(ServerRequest request) {
        Mono<SecUser> secUserMono = request.bodyToMono(SecUser.class).map(user -> {
            String role = "ROLE_USER";
            if (user.getPermission().equals("0")) {
                role = "ROLE_ADMIN";
            }
            SecUser secUser = new SecUser(user.getUsername(), user.getPassword(), role);
            organizationDataService.saveUser(secUser);
            return secUser;
        });

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(secUserMono, SecUser.class).switchIfEmpty(userNotFound);
    }

    public Mono<ServerResponse> userJoinGroup(ServerRequest request) {
        Mono<SecUser> secUserMono = request.bodyToMono(SecUser.class)
                .map(user -> {
                    SecUser secUser = organizationDataService.getUser(user.getUsername());
                    secUser.setCompanyId(user.getCompanyId()).setGroupId(user.getGroupId());
                    organizationDataService.saveUser(secUser);
                    organizationDataService.activeGroup(secUser.getGroupId());
                    organizationDataService.activeCompany(secUser.getCompanyId());
                    return secUser;
                });

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
