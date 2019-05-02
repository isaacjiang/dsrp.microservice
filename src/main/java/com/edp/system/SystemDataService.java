package com.edp.system;


import com.edp.interfaces.MicroServiceInterface;
import com.edp.organization.models.Company;
import com.edp.organization.models.Group;
import com.edp.system.models.Action;
import com.edp.system.models.ActionRepo;
import com.edp.system.models.Period;
import com.edp.system.models.PeriodRepo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ConcurrentHashMap;


@Service
public class SystemDataService implements MicroServiceInterface {

    @Autowired
    private ActionRepo actionRepo;
    @Autowired
    private PeriodRepo periodRepo;

    public SystemDataService() {}

    @Override
    public SystemDataService start() {
        Thread thr = new Thread(this, this.getClass().getName());
        thr.setName("Service@SystemDataService");
        thr.start();
        return this;
    }

    @Override
    public void run() {
        this.importData();
        this.initialization();
    }


    @Override
    public void schedule() {

    }

    @Override
    public void stop() {

    }

    public void importData() {
        String systemPath = System.getProperty("user.dir");

        JSONArray actionList = Utilities.JSONArrayFileReader(systemPath+"/initialization/action.json");

        actionList.forEach( action->{
            JSONObject action1 = (JSONObject) action;
//            System.out.println(action1);
            Action actionInc = new Action().setId(action1.getString("id"))
                    .setName(action1.getString("name"))
                    .setLabel(action1.getString("name"))
                    .setPeriod(action1.getInt("period"))
                    .setType(action1.getString("type"))
                    .setCompanyType(action1.getString("companyType"))
                    .setProcessId(action1.get("processId").toString())
                    .setPrevious(action1.get("previous").toString())
                    .setStatus("Init");

            actionRepo.save(actionInc).subscribe();
        });

        JSONArray periodList = Utilities.JSONArrayFileReader(systemPath+"/initialization/period.json");

        periodList.forEach( period->{
            JSONObject period1 = (JSONObject) period;
          // System.out.println(period1);
            Period periodInc = new Period().setId(period1.getString("id"))
                    .setPeriod(period1.getInt("period"))
                    .setLabel(period1.getString("label"))
                    .setStatus(period1.getString("status"))
                    .setCompanies(new ConcurrentHashMap<>())
                    .setEnabled(period1.getBoolean("enabled"));

            periodRepo.save(periodInc).subscribe();
        });

    }

    public void initialization(){

        periodRepo.getPeriodByPeriod(1).subscribe(period -> {
            period.setStatus("Active");
            periodRepo.save(period).subscribe();
        });


    }

    public Mono<Period> getCurrentPeriod() {
        return periodRepo.getPeriodByStatus("Active");
    }

    public Flux<Action> getActionByCompany(String companyType, int period) {
        return actionRepo.getActionsByCompanyTypeAndPeriod(companyType,period);
    }
}
