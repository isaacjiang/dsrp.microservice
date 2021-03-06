package com.edp.system;


import com.edp.account.models.AccTitle;
import com.edp.account.models.AccTitleRepo;
import com.edp.business.models.*;
import com.edp.interfaces.MicroServiceInterface;
import com.edp.system.models.Task;
import com.edp.system.models.TaskRepo;
import com.edp.system.models.Period;
import com.edp.system.models.PeriodRepo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


@Service
public class SystemDataService implements MicroServiceInterface {

    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private PeriodRepo periodRepo;
    @Autowired
    private ActionsRepo actionsRepo;
    @Autowired
    private ProjectRepo projectRepo;
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private WorkforceRepo workforceRepo;

    @Autowired
    private ResourceRepo resourceRepo;
    @Autowired
    private NegotiationRepo negotiationRepo;

    @Autowired
    private AccTitleRepo accTitleRepo;

    private String systemPath = System.getProperty("user.dir");

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
        this.importJsonData();
        this.importExcelData();
        this.initialization();
    }


    @Override
    public void schedule() {

    }

    @Override
    public void stop() {

    }

    public void importJsonData() {


        JSONArray actionList = Utilities.JSONArrayFileReader(systemPath+"/initialization/action.json");

        actionList.forEach( action->{
            JSONObject action1 = (JSONObject) action;
//            System.out.println(action1);
            Task taskInc = new Task().setId(action1.getString("companyType").substring(0,1)+action1.getString("id"))
                    .setName(action1.getString("name"))
                    .setLabel(action1.getString("name"))
                    .setPeriod(action1.getInt("period"))
                    .setType(action1.getString("type"))
                    .setCompanyType(action1.getString("companyType"))
                    .setProcessId(action1.get("processId").toString())
                    .setPrevious(action1.get("previous").toString())
                    .setStatus("Init");
            Task task2 = taskRepo.getActionById(taskInc.getId());
            taskRepo.save(task2 == null ? taskInc : task2);
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

            Period period2 = periodRepo.getPeriodByPeriod(periodInc.getPeriod());

            periodRepo.save(period2==null?periodInc:period2);
        });

    }

    public void importExcelData(){

       JSONArray ActionsList =  Utilities.excelFileRead(systemPath+"/initialization/Actions.xlsx");
        ActionsList.forEach(action->{
            JSONObject json = (JSONObject)action;
//            System.out.println(json);
            Actions actions = new Actions().setId(json.getString("actionID"))
            .setName(json.getString("actionName"))
            .setCompanyType(json.getString("companyName"))
            .setCategory(json.getString("category"))
            .setPeriodStart(json.getInt("periodStart"))
            .setPeriodOccurs(json.getInt("PeriodOccurs"))
            ;
            actionsRepo.save(actions);
        });


        JSONArray projectList =  Utilities.excelFileRead(systemPath+"/initialization/Project.xlsx");
        projectList.forEach(detail->{
            JSONObject json = (JSONObject)detail;
//            System.out.println(json);
            Project project = new Project().setId(json.getString("projectID"))
                    .setName(json.getString("projectName"))
                    .setCompanyType(json.getString("company"))
//                    .setCategory(json.getString("category"))
//                    .setPeriodStart(json.getInt("periodStart"))
//                    .setPeriodOccurs(json.getInt("PeriodOccurs"))
                    ;
            projectRepo.save(project);
        });

        JSONArray employeeList =  Utilities.excelFileRead(systemPath+"/initialization/Employee.xlsx");
        employeeList.forEach(detail->{
            JSONObject json = (JSONObject)detail;
           // System.out.println(json);
            Employee employee = new Employee().setId(json.getString("employeeID"))
                    .setName(json.getString("employeeName"))
                    .setCompanyType(json.getString("companyName"))
                    .setTitle(json.getString("title"))
                    .setCategory(json.getString("category"))
                    .setPeriod(json.getInt("startAtPeriod"))
                    .setSalary(json.getInt("minimumSalary"))
//                    .setPeriodOccurs(json.getInt("PeriodOccurs"))
                    ;
            employeeRepo.save(employee);
        });


        JSONArray budgetList =  Utilities.excelFileRead(systemPath+"/initialization/Budget.xlsx");
        budgetList.forEach(detail->{
            JSONObject json = (JSONObject)detail;
//            System.out.println(json);
//            Employee employee = new Employee().setId(json.getString("employeeID"))
//                    .setName(json.getString("employeeName"))
//                    .setCompanyType(json.getString("companyName"))
//                    .setTitle(json.getString("title"))
//                    .setCategory(json.getString("category"))
//                    .setPeriod(json.getInt("startAtPeriod"))
//                    .setPeriodOccurs(json.getInt("PeriodOccurs"))
//                    ;
//            employeeRepo.save(employee);
        });


        JSONArray corporateAcquisitionList =  Utilities.excelFileRead(systemPath+"/initialization/CorporateAcquisition.xlsx");
        corporateAcquisitionList.forEach(detail->{
            JSONObject json = (JSONObject)detail;
//            System.out.println(json);
//            Employee employee = new Employee().setId(json.getString("employeeID"))
//                    .setName(json.getString("employeeName"))
//                    .setCompanyType(json.getString("companyName"))
//                    .setTitle(json.getString("title"))
//                    .setCategory(json.getString("category"))
//                    .setPeriod(json.getInt("startAtPeriod"))
//                    .setPeriodOccurs(json.getInt("PeriodOccurs"))
//                    ;
//            employeeRepo.save(employee);
        });

        JSONArray negotiationList =  Utilities.excelFileRead(systemPath+"/initialization/Negotiation.xlsx");
        negotiationList.forEach(detail->{
            JSONObject json = (JSONObject)detail;
           // System.out.println(json);
//            Employee employee = new Employee().setId(json.getString("employeeID"))
//                    .setName(json.getString("employeeName"))
//                    .setCompanyType(json.getString("companyName"))
//                    .setTitle(json.getString("title"))
//                    .setCategory(json.getString("category"))
//                    .setPeriod(json.getInt("startAtPeriod"))
//                    .setPeriodOccurs(json.getInt("PeriodOccurs"))
//                    ;
//            employeeRepo.save(employee);
        });
        JSONArray nicheList =  Utilities.excelFileRead(systemPath+"/initialization/Niche.xlsx");
        nicheList.forEach(detail->{
            JSONObject json = (JSONObject)detail;
            //System.out.println(json);
//            Employee employee = new Employee().setId(json.getString("employeeID"))
//                    .setName(json.getString("employeeName"))
//                    .setCompanyType(json.getString("companyName"))
//                    .setTitle(json.getString("title"))
//                    .setCategory(json.getString("category"))
//                    .setPeriod(json.getInt("startAtPeriod"))
//                    .setPeriodOccurs(json.getInt("PeriodOccurs"))
//                    ;
//            employeeRepo.save(employee);
        });

        JSONArray resourceList =  Utilities.excelFileRead(systemPath+"/initialization/Resource.xlsx");
        resourceList.forEach(detail->{
            JSONObject json = (JSONObject)detail;
         //   System.out.println(json);
//            Employee employee = new Employee().setId(json.getString("employeeID"))
//                    .setName(json.getString("employeeName"))
//                    .setCompanyType(json.getString("companyName"))
//                    .setTitle(json.getString("title"))
//                    .setCategory(json.getString("category"))
//                    .setPeriod(json.getInt("startAtPeriod"))
//                    .setPeriodOccurs(json.getInt("PeriodOccurs"))
//                    ;
//            employeeRepo.save(employee);
        });

        JSONArray workforceList =  Utilities.excelFileRead(systemPath+"/initialization/Workforce.xlsx");
        workforceList.forEach(detail->{
            JSONObject json = (JSONObject)detail;
          //  System.out.println(json);
            Workforce workforce = new Workforce().setId(json.getString("workforceID"))
                                                .setPeriod(json.getInt("period"))
                    .setAvWage(json.getInt("avWage"))
                    .setException(json.getInt("exception"))
                    .setCostOfFire(json.getInt("costOfFire"))
                    .setCostOfHire(json.getInt("costOfHire"))
                    .setFunctions(json.getString("functions"))
                    .setAvExpense(json.getInt("avExpense"))
                    .setCoreEmployeeRate(json.getInt("coreEmployeeRate"))
                    .setRecommendBase(json.getInt("recommend_base"))
                    ;
            workforceRepo.save(workforce);
        });

        JSONArray accTitleList =  Utilities.excelFileRead(systemPath+"/initialization/Account.xlsx");
        accTitleList.forEach(detail->{
            JSONObject json = (JSONObject)detail;
//             System.out.println(json);

            AccTitle accTitle = new AccTitle().setId(json.getString("accountDescID"))
                    .setTitle(json.getString("accountDescName"))
                    .setDescription(json.getString("accountDescription"))
                    .setType(json.getString("accountDescType"))
                    .setSummary(json.getInt("summaryFLag"))
                    .setLevel(json.getInt("accountDescLevel"))
                    ;
            accTitleRepo.save(accTitle);
        });

    }

    public void initialization(){

        Period period = periodRepo.getPeriodByPeriod(1).setStatus("Active");
        periodRepo.save(period);



    }

    public List<Period> getAllPeriod() {
        return periodRepo.findAll().stream().sorted(Comparator.comparing(Period::getPeriod)).collect(Collectors.toList());
    }

    public Period getCurrentPeriod() {
        return periodRepo.getPeriodByStatus("Active");
    }

    public List<Task> getActionByCompany(String companyType, int period) {
        return taskRepo.getActionsByCompanyTypeAndPeriod(companyType,period);
    }



}
