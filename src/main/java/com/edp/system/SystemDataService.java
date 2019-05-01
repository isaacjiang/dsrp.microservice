package com.edp.system;


import com.edp.interfaces.MicroServiceInterface;
import com.edp.organization.models.Group;
import com.edp.system.models.Action;
import com.edp.system.models.ActionRepo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


@Service
public class SystemDataService implements MicroServiceInterface {

    @Autowired
    ActionRepo actionRepo;

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
        initialization();
    }


    @Override
    public void schedule() {

    }

    @Override
    public void stop() {

    }

    public void initialization() {
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




    }


    public Flux<Action> getAllAction() {
        return actionRepo.findAll();
    }
}
