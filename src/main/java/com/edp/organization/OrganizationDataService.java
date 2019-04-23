package com.edp.organization;


import com.edp.interfaces.MicroServiceInterface;
import com.edp.organization.models.*;
import com.edp.system.Tools;
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


@Service
public class OrganizationDataService implements ReactiveUserDetailsService, MicroServiceInterface {

    @Autowired
    SecUserRepo secUserRepo;

    @Autowired
    GroupRepo groupRepo;

    @Autowired
    CompanyRepo companyRepo;

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
        secUser.setGroupId("00000000");
        secUser.setCompanyId("00000000");
        secUser.setPermission("11111111");
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
    public Flux<SecUser>getAllSecUsers() {
        return secUserRepo.findAll();
    }

    /**
     * if the user already logged in, the system will login in without system check
     * otherwise will check the database and return the status of logging user
     * if username is not found will return an anonymous user otherwise return username + encoded passwword
     */
    public Mono<SecUser> getUser(String username) {
        return  secUserRepo.getSecUserByUsername(username);
    }



    /**
     * register user with user name, password, email, license
     * the user will be set to ROLE_USER
     */
    public void saveUser(SecUser secUser) {

        System.out.println("----------------------------------- registering user");
        secUserRepo.saveAll(Mono.just(secUser)).subscribe();

    }

    /**
     * delete user by username
     */
    public void deleteUser(String username) {

        Mono<SecUser> secuser = secUserRepo.getSecUserByUsername(username);
        secUserRepo.deleteAll(secuser).subscribe(); // delete user

    }

    /**
     * update user information
     * first delete the user from database and then insert the updated user into database
     */
    public void modifyUser(SecUser secUser) {

        Mono<SecUser> secUser1 = secUserRepo.getSecUserByUsername(secUser.getUsername());

        secUserRepo.deleteAll(secUser1).subscribe(); // delete the old user info

        secUserRepo.saveAll(Mono.just(secUser)).subscribe(); // insert the new user info

    }


}
