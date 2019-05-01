package com.edp.organization;


import com.edp.interfaces.MicroServiceInterface;
import com.edp.organization.models.*;
import com.edp.system.Utilities;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;


@Service
public class OrganizationDataService implements MicroServiceInterface {

    @Autowired
    SecUserRepo secUserRepo;

    @Autowired
    GroupRepo groupRepo;

    @Autowired
    CompanyRepo companyRepo;

    public OrganizationDataService() {

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
       this.initialization();
    }


    /**
     * the method hard coded admin user into database with role Admin
     */

    public void initialization() {
        String systemPath = System.getProperty("user.dir");


        SecUser secUser = new SecUser("Admin", "admin", Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
        secUser.setGroupId("000000");
        secUser.setCompanyId("000");
        secUser.setPermission("0");
        secUser.setUid("000000000000000000000001");
        secUserRepo.saveAll(Flux.just(secUser)).subscribe();

        Group groupDefaut = new Group().setId("000000")
                .setGroupName("GroupSuper")
                .setDescription("GroupSuper")
                .setNickname("GroupSuper")
                .setDeleted(false)
                .setAdmin(true)
                .setEnabled(true);
        groupRepo.save(groupDefaut).subscribe();


        JSONArray groupList = Utilities.JSONArrayFileReader(systemPath+"/initialization/group.json");

        JSONArray companyList = Utilities.JSONArrayFileReader(systemPath+"/initialization/company.json");


        groupList.forEach( group->{
            JSONObject group1 = (JSONObject) group;
//            System.out.println(group);
            Group groupInc = new Group().setId(group1.getString("id"))
                    .setGroupName(group1.getString("groupName"))
                    .setDescription(group1.getString("description"))
                    .setNickname(group1.getString("nickname"))
                    .setDeleted(group1.getBoolean("deleted"))
                    .setAdmin(group1.getBoolean("isAdmin"))
                    .setEnabled(group1.getBoolean("enabled"));
            groupRepo.save(groupInc).subscribe();
        });

        groupRepo.findAll().subscribe(groupInc -> {
//            System.out.println(groupInc.getGroupName());
            companyList.forEach( company->{
                JSONObject company1 = (JSONObject) company;
                Company companyA = new Company().setId(groupInc.getId()+company1.getString("id"))
                        .setGroupId(groupInc.getId())
                        .setGroupName(groupInc.getGroupName())
                        .setCompanyName(company1.getString("companyName"))
                        .setCompanyType(company1.getString("companyType"))
                        .setDescription(company1.getString("description"))
                        .setNickname(company1.getString("nickname"))
                        .setDeleted(company1.getBoolean("deleted"))
                        .setEnabled(company1.getBoolean("enabled"));

                Company companyB = new Company().setId(groupInc.getId()+company1.getString("id"))
                        .setGroupId(groupInc.getId())
                        .setGroupName(groupInc.getGroupName())
                        .setCompanyName(company1.getString("companyName"))
                        .setCompanyType(company1.getString("companyType"))
                        .setDescription(company1.getString("description"))
                        .setNickname(company1.getString("nickname"))
                        .setDeleted(company1.getBoolean("deleted"))
                        .setEnabled(company1.getBoolean("enabled"));

                companyRepo.save(companyA).subscribe();
                companyRepo.save(companyB).subscribe();
            });
        });

    }




    /**
     * GET ALL Users info from database
     */
    public Flux<Group>getAllGroups() {
        return groupRepo.findAll();
    }


    public Flux<SecUser>getAllSecUsers() {
        return secUserRepo.findAll();
    }

    public Flux<Company>getBaseCompanies() {
        return companyRepo.findByGroupId("000000");
    }

    public Flux<Company>getAllCompanies() {
        return companyRepo.findAll();
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
        secUserRepo.saveAll(Mono.just(secUser)).subscribe();

    }
    public void saveUser(Mono<SecUser> secUser) {
        secUserRepo.saveAll(secUser).subscribe();
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
