package com.edp.organization;


import com.edp.interfaces.MicroServiceInterface;
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

import java.util.Base64;
import java.util.Collections;


@Service
public class OrganizationDataService implements ReactiveUserDetailsService, MicroServiceInterface {

    @Autowired
    SecUserRepo secUserRepo;

    public OrganizationDataService() {

    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return secUserRepo.getUserDetailsByUsername(username);
    }



    @Override
    public MicroServiceInterface start() {
        Thread thr = new Thread(this, this.getClass().getName());
        thr.setName("Service@Organization");
        thr.start();
        return this;
    }

    @Override
    public void schedule() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void run() {
      this.initAdminUser();
    }


    /**
     * the method hard coded admin user into database with role Admin
     */

    public void initAdminUser() {
        SecUser secUser = new SecUser("Admin", passwordEncode("admin"), Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
        secUser.setEmail(null);
        secUser.setPermission("Admin");
        secUser.setUid("000000000000000000000001");
        secUserRepo.saveAll(Flux.just(secUser)).subscribe();
    }


    /**
     * Encoding method from passwordEncoderFactory
     */
    public String passwordEncode(String password) {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return encoder.encode(password);
    }

    /**
     * GET ALL Users info from database
     */
    public Mono<ServerResponse> getAll(ServerRequest request) {
        Flux<SecUser> secUserFlux = secUserRepo.findAll();

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(secUserFlux, SecUser.class);
    }

    /**
     * if the user already logged in, the system will login in without system check
     * otherwise will check the database and return the status of logging user
     * if username is not found will return an anonymous user otherwise return username + encoded passwword
     */
    public Mono<ServerResponse> getUserStatus(ServerRequest request) {
        // build notFound response
        Mono<ServerResponse> notFound = ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(new SecUser().setAnonymous(true)), SecUser.class);
        try {
            String username = request.pathVariable("username");
            Mono<SecUser> user = secUserRepo.getSecUserByUsername(username).cache();
            return user.block() == null ? notFound : ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(user.map(userDetails -> userDetails.setAuthenticated(false)), SecUser.class);

        } catch (Exception e) {
            return notFound;
        }

    }

    /**
     * this method is used for checking if a given username exists before registering user
     * for the given user name from json object
     * if the user name is found, return a non-anonymous user
     * otherwise return an anonymous user
     */
    public Mono<ServerResponse> checkUserStatus(ServerRequest request) {
        // the frontend will use the status of anonymous to determine whether the username exist or not
        Mono<ServerResponse> notFound = ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(new SecUser().setAnonymous(true)), SecUser.class);
        Mono<ServerResponse> Found = ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(new SecUser().setAnonymous(false)), SecUser.class);
        String username = request.pathVariable("username");

        // build notFound response
        Mono<SecUser> user = secUserRepo.getSecUserByUsername(username).cache();

        if (user.block() == null) {
            System.out.println("new user");
        } else {
            System.out.println("user already exist, try a different name");
        }

        return user.block() == null ? notFound : Found;
    }

    /**
     * Logout User
     */
    public Mono<ServerResponse> UserLogout(ServerRequest request) {

        System.out.println("logging out");

        Mono<ServerResponse> notFound = ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(new SecUser().setAnonymous(true)), SecUser.class);


        return notFound;
    }


    /**
     * success login
     */
    public Mono<ServerResponse> getUserSuccessStatus(ServerRequest request) {

        try {
            String username = String.valueOf(request.headers().asHttpHeaders().get("Authorization")).replace("[Basic ", "").replace("]", "");
            String username1 = new String(Base64.getDecoder().decode(username)).split(":")[0];
            System.out.println(username1 + "----------------------------------- Authenticate Success.");

            Mono<SecUser> user = secUserRepo.getSecUserByUsername(username1);

            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(user.map(userDetails -> userDetails.setAuthenticated(true)), SecUser.class);
        } catch (Exception e) {
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(new SecUser().setAnonymous(true)), SecUser.class);
        }
    }

    /**
     * failure login
     */
    public Mono<ServerResponse> getUserFailureStatus(ServerRequest request) {

        // build notFound response
        Mono<ServerResponse> notFound = ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(new SecUser().setAnonymous(true)), SecUser.class);

        try {
            String username = String.valueOf(request.headers().asHttpHeaders().get("Authorization")).replace("[Basic ", "").replace("]", "");
            String username1 = new String(Base64.getDecoder().decode(username)).split(":")[0];
            System.out.println(username1 + "----------------------------------- Authenticate Failure.");

            Mono<SecUser> user = secUserRepo.getSecUserByUsername(username1).cache();
            return user.block() == null ? notFound : ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(user.map(userDetails -> userDetails.setAuthenticated(false)), SecUser.class);

        } catch (Exception e) {
            return notFound;
        }
    }

    /**
     * register user with user name, password, email, license
     * the user will be set to ROLE_USER
     */
    public Mono<ServerResponse> registerUser(ServerRequest request) {

        System.out.println("----------------------------------- registering user");

        request.bodyToMono(String.class).subscribe(d -> { //parse request to string object
            JSONObject data = new JSONObject(d);
            String name = data.getString("key");
            String password = data.getString("password");
            String email = data.getString("email");
            String license = data.getString("license");

            // set user to ROLE_USER
            SecUser secUser1 = new SecUser(name, passwordEncode(password), Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
            //secUser1.setId(); //this will assign user with random ID
            secUser1.setAnonymous(false);
            secUser1.setEmail(email);
//            secUser1.setLicense(license);
            secUser1.setPermission("User");
            secUserRepo.saveAll(Flux.just(secUser1)).subscribe();
            System.out.println(name + "-----------------------------------  Register success");
        });

        //return value doesnot matter
        Mono<ServerResponse> notFound = ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(new SecUser().setAnonymous(true)), SecUser.class);
        return notFound;
    }

    /**
     * delete user by username
     */
    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        String username = request.pathVariable("username");

        Mono<SecUser> secuser = secUserRepo.getSecUserByUsername(username);
        SecUser secuser1 = secuser.block();
        secUserRepo.deleteAll(Flux.just(secuser1)).subscribe(); // delete user

        //return value doesnot matter
        Mono<ServerResponse> notFound = ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(new SecUser().setAnonymous(true)), SecUser.class);
        return notFound;

    }

    /**
     * update user information
     * first delete the user from database and then insert the updated user into database
     */
    public Mono<ServerResponse> modifyUser(ServerRequest request) {

        request.bodyToMono(String.class).subscribe(d -> {
            JSONObject data = new JSONObject(d);
            String username = data.getString("key");
            String email = data.getString("email");

            Mono<SecUser> secuser = secUserRepo.getSecUserByUsername(username);
            SecUser secuser1 = secuser.block();
            secUserRepo.deleteAll(Flux.just(secuser1)).subscribe(); // delete the old user info

            secuser1.setEmail(email);//update email
            secUserRepo.saveAll(Flux.just(secuser1)).subscribe(); // insert the new user info
        });

        //return value doesnot matter
        Mono<ServerResponse> notFound = ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(new SecUser().setAnonymous(true)), SecUser.class);
        return notFound;
    }


}
