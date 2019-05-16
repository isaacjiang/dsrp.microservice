package com.edp.organization;


import com.edp.interfaces.MicroServiceInterface;
import com.edp.organization.models.*;
import com.edp.system.SystemDataService;
import com.edp.system.Utilities;
import com.edp.system.models.Period;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;


@Service
public class OrganizationDataService implements MicroServiceInterface {

    @Autowired
    private SecUserRepo secUserRepo;

    @Autowired
    private GroupRepo groupRepo;

    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    private SystemDataService systemDataService;
    @Autowired
    private  CompanySummaryRepo companySummaryRepo;

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
        this.importData();
        this.initialization();
    }


    /**
     * the method hard coded admin user into database with role Admin
     */

    public void importData() {
        String systemPath = System.getProperty("user.dir");

        Group groupDefaut = new Group().setId("000000")
                .setGroupName("GroupSuper")
                .setDescription("GroupSuper")
                .setNickname("GroupSuper")
                .setDeleted(false)
                .setAdmin(true)
                .setEnabled(true);
        groupRepo.save(groupDefaut);


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

            Group group2 = groupRepo.getGroupById(groupInc.getId());

            if(group2==null){groupRepo.save(groupInc);}
        });

        groupRepo.findAll().forEach(groupInc -> {
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

                if(companyRepo.getCompanyById(companyA.getId())==null){
                    companyRepo.save(companyA);
                }
                if(companyRepo.getCompanyById(companyB.getId())==null){
                    companyRepo.save(companyB);
                }
            });
        });

    }

    public void initialization(){


        SecUser secUser = new SecUser("Admin", "admin", Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
        secUser.setGroupId("000000");
        secUser.setCompanyId("000");
        secUser.setPermission("0");
        secUser.setUid("000000000000000000000001");
        secUserRepo.save(secUser);

    }


    /**
     * GET ALL Users info from database
     */
    public List<Group> getAllGroups() {
        return groupRepo.findAll();
    }


    public List<SecUser>getAllSecUsers() {
        return secUserRepo.findAll();
    }

    public List<Company>getBaseCompanies() {
        return companyRepo.findByGroupId("000000");
    }

    public List<Company>getAllCompanies() {
        return companyRepo.findAll();
    }
    /**
     * if the user already logged in, the system will login in without system check
     * otherwise will check the database and return the status of logging user
     * if username is not found will return an anonymous user otherwise return username + encoded passwword
     */
    public SecUser getUser(String username) {
        return  secUserRepo.getSecUserByUsername(username);
    }

    public Company getCompany(String companyId) {
        return  companyRepo.getCompanyById(companyId);
    }
    public Group getGroup(String groupId) {
        return  groupRepo.getGroupById(groupId);
    }

    public void setCompanySummarry(String companyId) {
        Company company = companyRepo.getCompanyById(companyId);

            System.out.println(company.getId()+"'   "+companyId);
             CompanySummary companySummary = new CompanySummary()
                    .setCompanyId(company.getId()).setCompanyName(company.getCompanyName())
                    .setGroupId(company.getGroupId()).setGroupName(company.getGroupName())
                    .setPeriod(company.getInPeriod()).setWorkforceTotal(560);

        CompanySummary companySummary1 = companySummaryRepo.getCompanyByCompanyIdAndPeriod(company.getId(), company.getInPeriod());

        companySummaryRepo.save(companySummary1==null?companySummary:companySummary1);


    }

    public CompanySummary getCompanySummarry(String companyId) {
        this.setCompanySummarry(companyId);
        Company company = companyRepo.getCompanyById(companyId);
        return companySummaryRepo.getCompanyByCompanyIdAndPeriod(companyId,company.getInPeriod());
    }

    /**
     * register user with user name, password, email, license
     * the user will be set to ROLE_USER
     */
    public void saveUser(SecUser secUser) {
        SecUser secUser1 = secUserRepo.getSecUserByUsername(secUser.getUsername());

        secUserRepo.save(secUser1==null?secUser:secUser.setUid(secUser1.getUid()));

    }



    /**
     * delete user by username
     */
    public void deleteUser(String username) {

        SecUser secuser = secUserRepo.getSecUserByUsername(username);
        secUserRepo.delete(secuser); // delete user

    }

    /**
     * update user information
     * first delete the user from database and then insert the updated user into database
     */
    public void modifyUser(SecUser secUser) {

        SecUser secUser1 = secUserRepo.getSecUserByUsername(secUser.getUsername());

        secUserRepo.delete(secUser1); // delete the old user info

        secUserRepo.save(secUser); // insert the new user info

    }

    public void activeGroup(String groupId){
        Group group = groupRepo.getGroupById(groupId);

        groupRepo.save(group.setEnabled(true));

    }
    public void activeCompany(String companyId){
        Period currentPeriod =  systemDataService.getCurrentPeriod();
        Company company = companyRepo.getCompanyById(companyId);
        companyRepo.save(company.setEnabled(true).setInPeriod(currentPeriod.getPeriod()));

    }

}
