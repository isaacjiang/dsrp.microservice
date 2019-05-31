package com.edp.account;


import com.edp.account.models.AccJournalEntry;
import com.edp.account.models.AccJournalEntryRepo;
import com.edp.account.models.AccTitle;
import com.edp.account.models.AccTitleRepo;
import com.edp.business.models.Employee;
import com.edp.business.models.EmployeeRepo;
import com.edp.business.models.Forecasting;
import com.edp.business.models.ForecastingRepo;
import com.edp.interfaces.MicroServiceInterface;
import com.edp.organization.OrganizationDataService;
import com.edp.system.SystemDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AccountDataService implements MicroServiceInterface {

    @Autowired
    private AccTitleRepo accTitleRepo;

    @Autowired
    private AccJournalEntryRepo accJournalEntryRepo;
    @Autowired
    private OrganizationDataService organizationDataService;

    public AccountDataService() {

    }


    @Override
    public MicroServiceInterface start() {
        Thread thr = new Thread(this, this.getClass().getName());
        thr.setName("Service@Account");
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


    public void initialization(){



    }

    public void bookkeeping(AccJournalEntry accJournalEntry){
//        if (accJournalEntryRepo.)
        accJournalEntryRepo.save(accJournalEntry);

    }




}
