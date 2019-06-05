package com.edp.account;


import com.edp.account.models.*;
import com.edp.business.models.Employee;
import com.edp.business.models.EmployeeRepo;
import com.edp.business.models.Forecasting;
import com.edp.business.models.ForecastingRepo;
import com.edp.fileservice.AttachmentService;
import com.edp.interfaces.MicroServiceInterface;
import com.edp.organization.OrganizationDataService;
import com.edp.system.DatabaseService;
import com.edp.system.SystemDataService;
import com.edp.system.Utilities;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
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
    private OrganizationDataService organizationDataService;

    @Autowired
    private DatabaseService databaseService;

    @Autowired
    private SystemDataService systemDataService;


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

//        databaseService.getOpdb().getCollection("project").find().forEach(
//                (Consumer<? super Document>) document -> {
//                    System.out.println("Account schedule runnning     ... "+document);
//                }
//        );

        createAccountBook("000001001",1);
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

    public AccountBook createAccountBook(String companyId, int period){
        Document filter = new Document().append("companyId",companyId).append("period",period);
        AccountBook accountBook = new AccountBook(companyId,period);
        databaseService.getOpdbCollection("acc_book").replaceOne(filter,accountBook.document(), new ReplaceOptions().upsert(true));
        return accountBook;
    }

    public AccountBook getAccountBook(String companyId, int period) {
        BasicDBObject filter = new BasicDBObject().append("companyId", companyId).append("period", period);
        Document accountBook = databaseService.getOpdbCollection("acc_book").find(filter).limit(1).first();
        assert accountBook != null;
        return new AccountBook(accountBook.getString("companyId"), accountBook.getInteger("period"));
    }

    public void bookkeeping(String companyId, int period, String titleId, String reference, double value, String memo){



        AccJournalEntry accJournalEntry = accJournalEntryRepo.getAccJournalEntriesByCompanyIdAndPeriodAndTitleIdAndReference(companyId, period, titleId, reference);

        if(accJournalEntry == null){
            accJournalEntry = new AccJournalEntry();
        }
        accJournalEntry.setCompanyId(companyId).setPeriod(period).setTitleId(titleId).setTitle(accTitleRepo.getAccTitleById(titleId).getTitle()).setReference(reference).setValue(value).setMemo(memo);

        accJournalEntryRepo.save(accJournalEntry);

    }




}
