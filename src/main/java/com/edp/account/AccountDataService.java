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

import java.util.*;
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
        this.accountScheduleTask();


//        bookkeeping("000001002",1,"AB011","1",100,null);
//        bookkeeping("000001002",1,"AB011","2",200,null);
//        bookkeeping("000001002",1,"AB011","3",300,null);
//        bookkeeping("000001002",1,"AB011","4",400,null);
//        bookkeeping("000001002",1,"AB011","5",500,null);

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
        this.getAllAccountBook().stream()
                .filter(accountBook -> accountBook.getPeriod() == systemDataService.getCurrentPeriod().getPeriod()-3)
                .sorted(Comparator.comparing(AccountBook::getCompanyId))
                .forEach(accountBook -> {
            // System.out.println(accountBook.getCompanyId()+accountBook.getId());

           // 1-14
            this.titlePlus(accountBook,new ArrayList<>(Arrays.asList("AA011", "AA012")), "AA021", 1);
            this.titlePlus(accountBook,"AA011", "AA025", 0.32);
            this.titlePlus(accountBook,"AA012", "AA026", 0.32);
            this.titlePlus(accountBook,new ArrayList<>(Arrays.asList("AA025", "AA026")), "AA029", 1);
            this.titlePlus(accountBook,new ArrayList<>(Arrays.asList("AA031", "AA032", "AA033")), "AA041", 1);
            this.titlePlus(accountBook,"AA031", "AA131", 0.25);
            this.titlePlus(accountBook,"AA032", "AA132", 0.25);
            this.titlePlus(accountBook,"AA033", "AA133", 0.25);
            this.titlePlus(accountBook,new ArrayList<>(Arrays.asList("AA131", "AA132", "AA133")), "AA141", 1);
            this.titlePlus(accountBook,new ArrayList<>(Arrays.asList("AA021", "AA041")), "AA200", 1);
            this.titlePlus(accountBook,new ArrayList<>(Arrays.asList("AA029", "AA141")), "AA201", 1);
            this.titleMinus(accountBook,"AA200", "AA201", "AA202", 1);
            this.titlePlus(accountBook,new ArrayList<>(Arrays.asList("AA202", "AA210")), "AA211", 1);
            this.titlePlus(accountBook,new ArrayList<>(Arrays.asList("AA021", "AA041")), "AB017", 0.05);
            this.titlePlus(accountBook,new ArrayList<>(Arrays.asList("AB010", "AB011", "AB012", "AB013", "AB014", "AB015", "AB016", "AB017")), "AB021", 1);
            this.titleMinus(accountBook,"AA211", "AB021", "AB031", 1);

            // 15-36
            this.titlePlus(accountBook,"AA011", "BA012", 0.1);
            this.titlePlus(accountBook,"AA012", "BA013", 0.1);
            this.titlePlus(accountBook,"AA041", "BA014", 0.1);
            this.titlePlus(accountBook,"AA025", "BA015", 0.1);
            this.titlePlus(accountBook,"AA026", "BA016", 0.1);
            this.titlePlus(accountBook,"AA141", "BA017", 0.1);
            this.titleTrans(accountBook,new ArrayList<>(Arrays.asList("BA040", "BA041")), "BA040", accountBook.getPeriod() + 1);
            this.titlePlus(accountBook,new ArrayList<>(Arrays.asList("BA040", "BA041")), "BA042", 1);

            this.titleTrans(accountBook,new ArrayList<>(Arrays.asList("BA061", "BA062")), "BA061", accountBook.getPeriod() + 1);
           // this.titleMinus(accountBook,"BA061", "BA062", "BA061", 1)
            this.titlePlus(accountBook,new ArrayList<>(Arrays.asList("BA061", "BA062")), "BA063", 1);

            this.titlePlus(accountBook,"AA025", "BB011", 0.1);
            this.titlePlus(accountBook,"AA026", "BB012", 0.1);
            this.titlePlus(accountBook,"AA141", "BB013", 0.1);
            this.titlePlus(accountBook,"AA011", "BB014", 0.2);
            this.titlePlus(accountBook,"AA012", "BB015", 0.2);
            this.titlePlus(accountBook,"AA041", "BB016", 0.2);
            this.titlePlus(accountBook,new ArrayList<>(Arrays.asList("BB011", "BB012", "BB013", "BB014", "BB015", "BB016")), "BB021", 1);

            this.titleTrans(accountBook,new ArrayList<>(Arrays.asList("BB031", "BB032")), "BB031", accountBook.getPeriod() + 1);
            this.titleTrans(accountBook,"BB042", "BB042", accountBook.getPeriod() + 1);
            this.titlePlus(accountBook,new ArrayList<>(Arrays.asList("BB041", "BB042")), "BB050", 1);
            this.titlePlus(accountBook,new ArrayList<>(Arrays.asList("BB031", "BB032", "BB041", "BB042")), "BB060", 1);
            this.titleTrans(accountBook,"BB111", "BB111", accountBook.getPeriod() + 1);

        // 37
            this.titlePlus(accountBook,"AB061", "CA022", 1);

            this.titleTrans(accountBook,"BA012", "CA023", accountBook.getPeriod());
            this.titleMinus(accountBook,"CA023", "BA012", "CA023", 1);
            this.titleTrans(accountBook,"BA013", "CA024", accountBook.getPeriod());
            this.titleMinus(accountBook,"CA024", "BA013", "CA024", 1);
            this.titleTrans(accountBook,"BA014", "CA025", accountBook.getPeriod());
            this.titleMinus(accountBook,"CA025", "BA014", "CA025", 1);
            this.titleTrans(accountBook,"BA015", "CA026", accountBook.getPeriod());
            this.titleMinus(accountBook,"CA026", "BA015", "CA026", 1);
            this.titleTrans(accountBook,"BA016", "CA027", accountBook.getPeriod());
            this.titleMinus(accountBook,"CA027", "BA016", "CA027", 1);
            this.titleTrans(accountBook,"BA017", "CA028", accountBook.getPeriod());
            this.titleMinus(accountBook,"CA028", "BA017", "CA028", 1);

            this.titleTrans(accountBook,"BB011", "CA029", accountBook.getPeriod());
            this.titleMinus(accountBook,"BB011", "CA029", "CA029", 1);
            this.titleTrans(accountBook,"BB012", "CA030", accountBook.getPeriod());
            this.titleMinus(accountBook,"BB012", "CA030", "CA030", 1);
            this.titleTrans(accountBook,"BB013", "CA031", accountBook.getPeriod());
            this.titleMinus(accountBook,"BB013", "CA031", "CA031", 1);
            this.titleTrans(accountBook,"BB014", "CA032", accountBook.getPeriod());
            this.titleMinus(accountBook,"BB014", "CA032", "CA032", 1);
            this.titleTrans(accountBook,"BB015", "CA033", accountBook.getPeriod());
            this.titleMinus(accountBook,"BB015", "CA033", "CA033", 1);
            this.titleTrans(accountBook,"BB016", "CA034", accountBook.getPeriod());
            this.titleMinus(accountBook,"BB016", "CA034", "CA034", 1);
            this.titlePlus(accountBook,"BB032", "CA053", 1);
            this.titlePlus(accountBook,"BB112", "CA054", 1);
            this.titlePlus(accountBook,"BA061", "CA071", -1);
            this.titlePlus(accountBook,"BB042", "CA081", 1);

        //54
            this.titlePlus(accountBook,"BB031", "AB041", 0.05);
            this.titleMinus(accountBook,"AB031", "AB041", "AB051", 1);
            this.titlePlus(accountBook,"BA042", "AB061", 0.12);
            this.titleMinus(accountBook,"AB051", "AB061", "AB071", 1);
            this.titlePlus(accountBook,"AB071", "AB081", 0.2);
            this.titlePlus(accountBook,"AB071", "AB100", 0.8);

        //60
            this.titleTrans(accountBook,"BA011", "CA011", accountBook.getPeriod() + 1);
            this.titlePlus(accountBook,"AB100", "CA021", 1);

            this.titlePlus(accountBook,new ArrayList<>(Arrays.asList("CA021", "CA022", "CA023", "CA024", "CA025", "CA026", "CA027", "CA028", "CA029", "CA030", "CA031", "CA032", "CA033", "CA034")), "CA041", 1);

            this.titlePlus(accountBook,"BA041", "CA051", -1);
            this.titlePlus(accountBook,new ArrayList<>(Arrays.asList("CA011", "CA041", "CA051", "CA053", "CA054", "CA071", "CA081")), "CA091", 1);

            this.titleMinus(accountBook,"CA091", "CA071", "CA061", 1);

        //66
            this.titlePlus(accountBook,"CA091", "BA011", 1);
            this.titlePlus(accountBook,new ArrayList<>(Arrays.asList("BA011", "BA012", "BA013", "BA014", "BA015", "BA016", "BA017")), "BA021", 1);

        //68
            this.titleTrans(accountBook,"BA043", "BA043", accountBook.getPeriod() + 1) ; //todo
                    System.out.println(accountBook.getCompanyId()+"  #  "+accountBook.getId() + "    "+ titleSum(accountBook,"BA043"));

            this.titlePlus(accountBook,new ArrayList<>(Arrays.asList("AB061", "BA043")), "BA043", 1);//TODO LOOP NEED TO BE SOLVE
            this.titleMinus(accountBook,"BA042", "BA043", "BA051", 1);
            this.titleMinus(accountBook,"AB051", "AB061", "AB071", 1);

            this.titlePlus(accountBook,new ArrayList<>(Arrays.asList("BA021", "BA051", "BA063")), "BA100", 1);

            this.titleTrans(accountBook,"BB113", "BB113", accountBook.getPeriod());
//            this.titlePlus(accountBook,new ArrayList<>(Arrays.asList("AB100", "BB113")), "BB113", 1); //TODO LOOP NEED TO BE SOLVE
            this.titlePlus(accountBook,new ArrayList<>(Arrays.asList("BB111", "BB112", "BB113")), "BB121", 1);
            this.titlePlus(accountBook,new ArrayList<>(Arrays.asList("BB021", "BB060", "BB121")), "BB131", 1);

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

    private void titlePlus(AccountBook accountBook,ArrayList<String> sourceTitleIdList,String destTitleId,double rate){
        double value = sourceTitleIdList.stream().mapToDouble(titleId -> titleSum(accountBook, titleId)).sum();
        bookkeeping(accountBook.getCompanyId(),accountBook.getPeriod(),destTitleId,sourceTitleIdList.toString(),value*rate,"Sum"+destTitleId);
    }
    private void titlePlus(AccountBook accountBook,String sourceTitleId,String destTitleId,double rate){
       titlePlus( accountBook, new ArrayList<>(Collections.singletonList(sourceTitleId)), destTitleId, rate);
    }

    private void titleMinus(AccountBook accountBook,String titleIdA,String titleIdB,String destTitleId,double rate){
        double value1 = titleSum(accountBook,titleIdA);
        double value2 = titleSum(accountBook,titleIdB);
        bookkeeping(accountBook.getCompanyId(),accountBook.getPeriod(),destTitleId,titleIdA+"-"+titleIdB,(value1-value2)*rate,"Minus:"+titleIdA+"-"+titleIdB);

    }
    private void titleTrans(AccountBook accountBook,String sourceTitleId,String destTitleId, int destPeriod){
        double value1 = titleSum(accountBook,sourceTitleId);
        if(accountBook.getPeriod()<8) bookkeeping(accountBook.getCompanyId(),destPeriod,destTitleId,sourceTitleId,value1,"Trans "+sourceTitleId);
    }
    private void titleTrans(AccountBook accountBook,ArrayList<String> sourceTitleIdList,String destTitleId, int destPeriod){
        double value1 =sourceTitleIdList.stream().mapToDouble(titleId-> titleSum(accountBook,titleId)).sum();
        if(accountBook.getPeriod()<8) bookkeeping(accountBook.getCompanyId(),destPeriod,destTitleId,sourceTitleIdList.toString(),value1,"Trans "+sourceTitleIdList.toString());
    }
}
