package com.edp.business;


import com.edp.account.AccountDataService;
import com.edp.business.models.Employee;
import com.edp.business.models.EmployeeRepo;
import com.edp.business.models.Forecasting;
import com.edp.business.models.ForecastingRepo;
import com.edp.interfaces.MicroServiceInterface;

import com.edp.fileservice.AttachmentService;
import com.edp.system.DatabaseService;
import com.edp.system.SystemDataService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;


@Service
public class BusinessDataService implements MicroServiceInterface {

    @Autowired
    private ForecastingRepo forecastingRepo;
    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private DatabaseService databaseService;

    @Autowired
    private AttachmentService attachmentService;


    @Autowired
    private SystemDataService systemDataService;

    @Autowired
    private AccountDataService accountDataService;

    private double x = 1000.2;

    public BusinessDataService() {

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
//        System.out.println("Schedule business ..... " + new Date().getTime());
        databaseService.getOpdb().getCollection("employee").find().forEach((Consumer<? super Document>) document -> {
            // System.out.println("db   "+document);
        });
//        System.out.println("db   "+attachmentService.getGridFsTemplate());


        //  new AccountBook("000001001",1).save();

        //   accountDataService.bookkeeping("000001001",1,"AB013",null,x,"IO TEST");
        x += 1.2;

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
//        String systemPath = System.getProperty("user.dir");
//
//        Group groupDefaut = new Group().setId("000000")
//                .setGroupName("GroupSuper")
//                .setDescription("GroupSuper")
//                .setNickname("GroupSuper")
//                .setDeleted(false)
//                .setAdmin(true)
//                .setEnabled(true);
//        groupRepo.save(groupDefaut).subscribe();


    }

    public void initialization() {

//
//        SecUser secUser = new SecUser("Admin", "admin", Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
//        secUser.setGroupId("000000");
//        secUser.setCompanyId("000");
//        secUser.setPermission("0");
//        secUser.setUid("000000000000000000000001");
//        secUserRepo.saveAll(Flux.just(secUser)).subscribe();

    }


    /**
     * GET ALL Forecasting info from database
     */
    public List<Forecasting> getForecastingByCompanyId(String companyId) {
        return forecastingRepo.getForecastingByCompanyId(companyId);
    }

    public Forecasting getForecastingByCompanyIdAndPeriod(String companyId, int period) {
        return forecastingRepo.getForecastingByCompanyIdAndPeriod(companyId, period);
    }

    public void saveForecasting(Forecasting forecasting) {

        Forecasting forecasting1 = forecastingRepo.getForecastingByCompanyIdAndPeriod(forecasting.getCompanyId(), forecasting.getPeriod());

        forecastingRepo.save(forecasting1 == null ? forecasting : forecasting.setId(forecasting1.getId()));
    }


    /**
     * GET ALL employees info from database
     */

    public List<Employee> getEmployeesByCompanyIdAndPeriod(String companyId, int period) {
        return employeeRepo.getEmployeesByCompanyTypeAndPeriod(companyId, period);
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepo.save(employee);
//        Forecasting forecasting1 = forecastingRepo.getForecastingByCompanyIdAndPeriod(forecasting.getCompanyId(), forecasting.getPeriod());

//        forecastingRepo.save(forecasting1==null?forecasting:forecasting.setId(forecasting1.getId()));
    }

}
