package com.edp.system;


import com.edp.account.models.AccTitle;
import com.edp.account.models.AccTitleRepo;
import com.edp.business.models.*;
import com.edp.interfaces.MicroServiceInterface;
import com.edp.system.models.Period;
import com.edp.system.models.PeriodRepo;
import com.edp.system.models.Task;
import com.edp.system.models.TaskRepo;
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
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


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
    private BudgetRepo budgetRepo;
    @Autowired
    private CorporateAcquisitionRepo corporateAcquisitionRepo;

    @Autowired
    private ResourceRepo resourceRepo;
    @Autowired
    private NegotiationRepo negotiationRepo;

    @Autowired
    private AccTitleRepo accTitleRepo;

    private String systemPath = System.getProperty("user.dir");

    public SystemDataService() {
    }

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
        JSONArray actionList = Utilities.JSONArrayFileReader(systemPath + "/initialization/action.json");
        actionList.forEach(action -> {
            JSONObject action1 = (JSONObject) action;
            Task taskInc = new Task().setId(action1.getString("companyType").substring(0, 1) + action1.getString("id"))
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

        JSONArray periodList = Utilities.JSONArrayFileReader(systemPath + "/initialization/period.json");
        periodList.forEach(period -> {
            JSONObject period1 = (JSONObject) period;
            // System.out.println(period1);
            Period periodInc = new Period().setId(period1.getString("id"))
                    .setPeriod(period1.getInt("period"))
                    .setLabel(period1.getString("label"))
                    .setStatus(period1.getString("status"))
                    .setCompanies(new ConcurrentHashMap<>())
                    .setEnabled(period1.getBoolean("enabled"));

            Period period2 = periodRepo.getPeriodByPeriod(periodInc.getPeriod());

            periodRepo.save(period2 == null ? periodInc : period2);
        });
    }

    public void importExcelData() {

        JSONArray ActionsList = this.excelFileRead(systemPath + "/initialization/Actions.xlsx");
        ActionsList.forEach(action -> {
            JSONObject json = (JSONObject) action;
            Actions actions = new Actions();
            actions.setId(json.getString("actionID"));
            actions.setCategory(json.getString("category"));
            actions.setName(json.getString("actionName"));
            actions.setStressIndex(json.getDouble("stressIndex"));
            actions.setImmediateIncrementalCost(json.getInt("immediateIncrementalCost"));
            actions.setCompanyType(json.getString("companyName"));
            actions.setLegitimacyIndex(json.getDouble("legitimacy"));
            actions.setPeriodStart(json.getInt("periodStart"));
            actions.setPeriodOccurs(json.getInt("PeriodOccurs"));
            actions.setCosChange(json.getInt("COSChange"));
            actions.setCompetenceIndex(json.getDouble("competenceIndex"));
            actions.setAssociatedCost(json.getDouble("associatedCost"));
            actions.setAdaptabilityIndex(json.getDouble("adaptabilityIndex"));
            actionsRepo.save(actions);
        });

        JSONArray projectList = this.excelFileRead(systemPath + "/initialization/Project.xlsx");
        projectList.forEach(detail -> {
            JSONObject json = (JSONObject) detail;
            Project project = new Project();
            project.setId(json.getString("projectID"));
            project.setStatus(json.getString("status"));
            project.setStressIndex(json.getInt("stress"));
            project.setLowestCost(json.getInt("lowerOfCost"));
            project.setCompanyType(json.getString("company"));
            project.setStrategicLogic(json.getString("strategicLogic"));
            project.setName(json.getString("projectName"));
            project.setHeadcount(json.getInt("headcount"));
            project.setCompetenceIndex(json.getDouble("competence"));
            project.setMarket(json.getString("market"));
            project.setPeriodStart(json.getInt("startAtPeriod"));
            project.setCostAtPeriod2(json.getInt("costAtPeriod2"));
            project.setCostAtPeriod3(json.getInt("costAtPeriod3"));
            project.setCostAtPeriod4(json.getInt("costAtPeriod4"));
            project.setCostAtPeriod5(json.getInt("costAtPeriod5"));
            project.setCostAtPeriod6(json.getInt("costAtPeriod6"));
            project.setCostAtPeriod7(json.getInt("costAtPeriod7"));
            project.setFinalAtPeriod(json.getInt("finalAtPeriod"));
            projectRepo.save(project);
        });

        JSONArray employeeList = this.excelFileRead(systemPath + "/initialization/Employee.xlsx");
        employeeList.forEach(detail -> {
            JSONObject json = (JSONObject) detail;
            Employee employee = new Employee();
            employee.setStatus(json.getString("status"));
            employee.setCategory(json.getString("category"));
            employee.setId(json.getString("employeeID"));
            employee.setTitle(json.getString("title"));
            employee.setLegitimacyIndex(json.getInt("legitimacy"));
            employee.setCompanyType(json.getString("companyName"));
            employee.setPeriod(json.getInt("startAtPeriod"));
            System.out.println(json.get("willingToMove").getClass());
            employee.setWillingToMove(json.getBoolean("willingToMove"));
            employee.setMinimumSalary(json.getInt("minimumSalary"));
            employee.setName(json.getString("employeeName"));
            employee.setCompetenceIndex(json.getDouble("competenceIndexEffect"));
            employeeRepo.save(employee);
        });

        JSONArray budgetList = this.excelFileRead(systemPath + "/initialization/Budget.xlsx");
        budgetList.forEach(detail -> {
            JSONObject json = (JSONObject) detail;
            Budget budget = new Budget();
            budget.setId(json.getString("budgetDescID"));
            budget.setBudgetType(json.getString("budgetDescType"));
            budget.setBudgetName(json.getString("budgetDescName"));
            budget.setSummaryFlag(json.getBoolean("summaryFLag"));
            budget.setParent(json.getString("parent"));
            budget.setBudgetLevel(json.getInt("budgetDescLevel"));
            budget.setAccountDescId(json.getString("accountDescID"));
            budget.setCalculateFlag(json.getBoolean("calculateFlag"));
            budget.setDescription(json.getString("description"));
            budgetRepo.save(budget);
        });

        JSONArray corporateAcquisitionList = this.excelFileRead(systemPath + "/initialization/CorporateAcquisition.xlsx");
        corporateAcquisitionList.forEach(detail -> {
            JSONObject json = (JSONObject) detail;
            CorporateAcquisition corporateAcquisition = new CorporateAcquisition();
            corporateAcquisition.setId(json.getInt("acquisID"));
            corporateAcquisition.setDevelopmentCost(json.getInt("developmentCost"));
            corporateAcquisition.setName(json.getString("name"));
            corporateAcquisition.setCompanyId(json.getString("companyID"));
            corporateAcquisition.setLegitimacy(json.getDouble("legitimacy"));
            corporateAcquisition.setCompany(json.getString("company"));
            corporateAcquisition.setStartAtPeriod(json.getInt("startAtPeriod"));
            corporateAcquisition.setPlatformIndex(json.getDouble("platform index"));
            corporateAcquisition.setCompetenceIndex(json.getDouble("competenceIndex"));
            corporateAcquisition.setMinimumBid(json.getDouble("minimumBid"));
            corporateAcquisition.setType(json.getString("type"));
            corporateAcquisition.setNumberDevelopmentEmployees(json.getInt("numberDevelopmentEmployees"));
            corporateAcquisition.setNumberOfCustomers(json.getInt("numberOfCustomers"));

            corporateAcquisitionRepo.save(corporateAcquisition);
        });

        JSONArray negotiationList = this.excelFileRead(systemPath + "/initialization/Negotiation.xlsx");
        negotiationList.forEach(detail -> {
            JSONObject json = (JSONObject) detail;
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
        JSONArray nicheList = this.excelFileRead(systemPath + "/initialization/Niche.xlsx");
        nicheList.forEach(detail -> {
            JSONObject json = (JSONObject) detail;
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

        JSONArray resourceList = this.excelFileRead(systemPath + "/initialization/Resource.xlsx");
        resourceList.forEach(detail -> {
            JSONObject json = (JSONObject) detail;
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

        JSONArray workforceList = this.excelFileRead(systemPath + "/initialization/Workforce.xlsx");
        workforceList.forEach(detail -> {
            JSONObject json = (JSONObject) detail;
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
                    .setRecommendBase(json.getInt("recommend_base"));
            workforceRepo.save(workforce);
        });

        JSONArray accTitleList = this.excelFileRead(systemPath + "/initialization/Account.xlsx");
        accTitleList.forEach(detail -> {
            JSONObject json = (JSONObject) detail;
//             System.out.println(json);

            AccTitle accTitle = new AccTitle().setId(json.getString("accountDescID"))
                    .setTitle(json.getString("accountDescName"))
                    .setDescription(json.getString("accountDescription"))
                    .setType(json.getString("accountDescType"))
                    .setSummary(json.getInt("summaryFLag"))
                    .setLevel(json.getInt("accountDescLevel"));
            accTitleRepo.save(accTitle);
        });

    }

    public void initialization() {
        Period period = periodRepo.getPeriodByPeriod(1).setStatus("Active");
        periodRepo.save(period);
    }

    public Period getCurrentPeriod() {
        return periodRepo.getPeriodByStatus("Active");
    }

    public List<Task> getActionByCompany(String companyType, int period) {
        return taskRepo.getActionsByCompanyTypeAndPeriod(companyType, period);
    }

    public JSONArray excelFileRead(String filename) {
        try {
            FileInputStream excelFile = new FileInputStream(new File(filename));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
            int row = 0;
            System.out.println(filename + ":  " + datatypeSheet.getFirstRowNum() + "--" + datatypeSheet.getLastRowNum());

            JSONArray array = new JSONArray();
            ArrayList<String> title = new ArrayList<>();

            while (iterator.hasNext()) {
                //System.out.print(row+ "   ");
                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();
                JSONObject object = new JSONObject();
                int col = 0;

                while (cellIterator.hasNext()) {
                    Cell currentCell = cellIterator.next();
                    if (row == 0) {
//                        System.out.print(currentCell.getStringCellValue() + "--");
                        title.add(currentCell.getStringCellValue());
                    } else {
                        if (currentCell.getCellType() == CellType.STRING) {
                            //System.out.print(currentCell.getStringCellValue() + "--");
                            object.put(title.get(col), currentCell.getStringCellValue());
                        } else if (currentCell.getCellType() == CellType.NUMERIC) {
                            //System.out.print(currentCell.getNumericCellValue() + "--");
                            object.put(title.get(col), currentCell.getNumericCellValue());
                        } else if (currentCell.getCellType() == CellType.BOOLEAN) {
                            object.put(title.get(col), currentCell.getBooleanCellValue());
                        }
                    }
                    col++;
                }
                if (!object.keySet().isEmpty()) {
                    array.put(object);
                    //System.out.println(object);
                }
                row++;
            }
            return array;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }
}
