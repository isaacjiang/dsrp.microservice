package com.edp.business;


import com.edp.business.models.Forecasting;
import com.edp.business.models.ForecastingRepo;
import com.edp.interfaces.MicroServiceInterface;

import com.edp.system.SystemDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@Service
public class BusinessDataService implements MicroServiceInterface {

    @Autowired
    private ForecastingRepo forecastingRepo;



    @Autowired
    private SystemDataService systemDataService;

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

    public void initialization(){

//
//        SecUser secUser = new SecUser("Admin", "admin", Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
//        secUser.setGroupId("000000");
//        secUser.setCompanyId("000");
//        secUser.setPermission("0");
//        secUser.setUid("000000000000000000000001");
//        secUserRepo.saveAll(Flux.just(secUser)).subscribe();

    }


    /**
     * GET ALL Users info from database
     */
    public Flux<Forecasting>getForecastingByCompanyId(String companyId) {
        return forecastingRepo.getForecastingByCompanyId(companyId);
    }
    public Mono<Forecasting>getForecastingByCompanyIdAndPeriod(String companyId,int period) {
        return forecastingRepo.getForecastingByCompanyIdAndPeriod(companyId,period);
    }
    public void saveForecasting(Mono<Forecasting> forecasting) {
        Mono<Forecasting> forecastingMono = forecasting.flatMap(forecasting1 ->
                forecastingRepo.getForecastingByCompanyIdAndPeriod(forecasting1.getCompanyId(),forecasting1.getPeriod())
                .map(forecasting2->forecasting1.setId(forecasting2.getId())).switchIfEmpty(forecasting));
        forecastingRepo.saveAll(forecastingMono).subscribe();
    }


}
