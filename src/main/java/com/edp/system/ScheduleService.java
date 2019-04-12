package com.edp.system;


import com.edp.interfaces.MicroServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class ScheduleService implements MicroServiceInterface {




    private ScheduledExecutorService scheduledExecutorService;

    public ScheduleService start() {
        Thread thr = new Thread(this, this.getClass().getName());
        thr.setName("Service@Schedue");
        thr.start();
        return this;
    }

    @Override
    public void run() {
        scheduledExecutorService = Executors.newScheduledThreadPool(10);

        scheduledExecutorService.scheduleAtFixedRate(this::schedule, 20, 60, TimeUnit.SECONDS);


    }


    @Override
    public void schedule() {
       // parametersService.updateMicroServieStatus(new MicroServiceStatus("Service@Schedue","Active",new Date()));
    }

    @Override
    public void stop() {

    }

}
