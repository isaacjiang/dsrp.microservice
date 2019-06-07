package com.edp.account;


import com.edp.account.models.*;
import com.edp.business.models.Employee;
import com.edp.business.models.EmployeeRepo;
import com.edp.business.models.Forecasting;
import com.edp.business.models.ForecastingRepo;
import com.edp.fileservice.AttachmentService;
import com.edp.interfaces.MicroServiceInterface;
import com.edp.organization.OrganizationDataService;
import com.edp.organization.models.Company;
import com.edp.system.DatabaseService;
import com.edp.system.SystemDataService;
import com.edp.system.Utilities;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;


@Service
public class AccountDataService implements MicroServiceInterface {

    @Autowired
    private AccTitleRepo accTitleRepo;

    @Autowired
    private AccJournalEntryRepo accJournalEntryRepo;

    @Autowired
    private AccountBookRepo accountBookRepo;

    @Autowired
    private DatabaseService databaseService;

    @Autowired
    private SystemDataService systemDataService;

    @Autowired
    private OrganizationDataService organizationDataService;

    JSONArray accountIniList;


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

        System.out.println("Account schedule runnning     ... ");

 initialization();
    }

    @Override
    public void stop() {

    }

    @Override
    public void run() {
        this.importData();
    }


    /**
     * the method hard coded admin user into database with role Admin
     */
    private void importData(){
        String systemPath = System.getProperty("user.dir");
        accountIniList = Utilities.JSONArrayFileReader(systemPath+"/initialization/account_ini.json");
    }


    public void initialization() {
        organizationDataService.getAllCompanies().stream().filter(company -> company.getEnabled()&&company.getCompanyType().equals("LegacyCo")).forEach(this::initAccoutBookCom);


    }
    private void initAccoutBookCom(Company company) {
        accountIniList.forEach(accJournalEntry -> {
            JSONObject aje = (JSONObject) accJournalEntry;
            bookkeeping(company.getId(), aje.getInt("period"), aje.getString("accountDescID"), "0", aje.getDouble("value"), aje.getString("comments"));
        });
    }

    public AccountBook createAccountBook(String companyId, int period) {
        AccountBook accountBook = new AccountBook(companyId,period);
       accountBookRepo.save(accountBook);
       return accountBook;
    }

    public AccountBook getAccountBook(String companyId, int period) {
        AccountBook accountBook = accountBookRepo.getAccountBookByCompanyIdAndPeriod(companyId,period);
        if (accountBook== null){
            accountBook =createAccountBook(companyId,period);
        }
        return accountBook;
    }

    public void bookkeeping(String companyId, int period, String titleId, String reference, double value, String memo) {

        AccountBook accountBook = getAccountBook(companyId, period);
        AccJournalEntry accJournalEntry = accJournalEntryRepo.getAccJournalEntryByAccountBookAndTitleIdAndReference(accountBook, titleId, reference);

        if (accJournalEntry == null) {
            accJournalEntry = new AccJournalEntry();
        }
        accJournalEntry.setAccountBook(accountBook).setTitleId(titleId).setTitle(accTitleRepo.getAccTitleById(titleId).getTitle()).setReference(reference).setValue(value).setMemo(memo);
        accJournalEntryRepo.save(accJournalEntry);

    }



}
