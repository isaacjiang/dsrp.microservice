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
        this.initialization();



        bookkeeping("000001002",1,"AB011","1",100,null);
        bookkeeping("000001002",1,"AB011","2",200,null);
        bookkeeping("000001002",1,"AB011","3",300,null);
        bookkeeping("000001002",1,"AB011","4",400,null);
        bookkeeping("000001002",1,"AB011","5",500,null);

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
    private void importData() {
        String systemPath = System.getProperty("user.dir");
        accountIniList = Utilities.JSONArrayFileReader(systemPath + "/initialization/account_ini.json");
    }


    public void initialization() {
        organizationDataService.getAllCompanies().stream().filter(company -> company.getEnabled() && company.getCompanyType().equals("LegacyCo")).forEach(this::initAccoutBookCom);


    }

    private void initAccoutBookCom(Company company) {
        accountIniList.forEach(accJournalEntry -> {
            JSONObject aje = (JSONObject) accJournalEntry;
            bookkeeping(company.getId(), aje.getInt("period"), aje.getString("accountDescID"), "0", aje.getDouble("value"), aje.getString("comments"));
        });
    }

    public AccountBook createAccountBook(String companyId, int period) {
        AccountBook accountBook = new AccountBook(companyId, period);
        accountBookRepo.save(accountBook);
        return accountBook;
    }

    public AccountBook getAccountBook(String companyId, int period) {
        AccountBook accountBook = accountBookRepo.getAccountBookByCompanyIdAndPeriod(companyId, period);
        if (accountBook == null) {
            accountBook = createAccountBook(companyId, period);
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

    public List<AccountBook> getAllAccountBook(){
        return accountBookRepo.findAll();

    }

    public void accountScheduleTask(){
        this.getAllAccountBook().forEach(accountBook -> {
            // System.out.println(accountBook.getCompanyId()+accountBook.getId());
            System.out.println(accountBook.getId()+"  :  "+titleSum(accountBook,"AB011"));

        });
    }

    private double titleSum(AccountBook accountBook, String titleId){
        List<AccJournalEntry> ajeList = accJournalEntryRepo.getAccJournalEntrysByAccountBookAndTitleId(accountBook, titleId);
        double total = 0.0;
        if (ajeList !=null){
            total = ajeList.stream().mapToDouble(AccJournalEntry::getValue).sum();
        }
        return total;
    }

    private void titlePlus(AccountBook accountBook,List<String> sourceTitleIdList,String destTitleId,double rate){
        double value = sourceTitleIdList.stream().mapToDouble(titleId -> titleSum(accountBook, titleId)).sum();
        bookkeeping(accountBook.getCompanyId(),accountBook.getPeriod(),destTitleId,sourceTitleIdList.toString(),value*rate,"Sum"+destTitleId);
    }
    private void titleMinus(AccountBook accountBook,String sourceTitleId,String destTitleId,double rate){
        double value1 = titleSum(accountBook,sourceTitleId);
        double value2 = titleSum(accountBook,destTitleId);
        bookkeeping(accountBook.getCompanyId(),accountBook.getPeriod(),destTitleId,sourceTitleId,(value1-value2)*rate,"Minus:"+sourceTitleId+"-"+destTitleId);

    }
    private void titleTrans(AccountBook accountBook,String sourceTitleId,String destTitleId, double rate){
        double value1 = titleSum(accountBook,sourceTitleId);
        if(accountBook.getPeriod()<8) bookkeeping(accountBook.getCompanyId(),accountBook.getPeriod()+1,destTitleId,sourceTitleId,value1*rate,"Trans "+sourceTitleId);
    }


}
