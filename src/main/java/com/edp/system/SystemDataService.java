package com.edp.system;


import com.edp.business.models.*;
import com.edp.interfaces.MicroServiceInterface;
import com.edp.system.models.Action;
import com.edp.system.models.ActionRepo;
import com.edp.system.models.Period;
import com.edp.system.models.PeriodRepo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class SystemDataService implements MicroServiceInterface {

    @Autowired
    private ActionRepo actionRepo;
    @Autowired
    private PeriodRepo periodRepo;
    @Autowired
    private ActionsRepo actionsRepo;
    @Autowired
    private ProjectRepo projectRepo;
    @Autowired
    private EmployeeRepo employeeRepo;

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
            Action actionInc = new Action().setId(action1.getString("id"))
                    .setName(action1.getString("name"))
                    .setLabel(action1.getString("name"))
                    .setPeriod(action1.getInt("period"))
                    .setType(action1.getString("type"))
                    .setCompanyType(action1.getString("companyType"))
                    .setProcessId(action1.get("processId").toString())
                    .setPrevious(action1.get("previous").toString())
                    .setStatus("Init");

            actionRepo.saveAll(actionRepo.findById(actionInc.getId()).switchIfEmpty(Mono.just(actionInc))).subscribe();
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

            periodRepo.saveAll(periodRepo.getPeriodByPeriod(periodInc.getPeriod()).switchIfEmpty(Mono.just(periodInc))).subscribe();
        });

    }

    public void importExcelData(){

       JSONArray ActionsList =  this.excelFileRead(systemPath+"/initialization/Actions.xlsx");
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
            actionsRepo.save(actions).subscribe();
        });


        JSONArray projectList =  this.excelFileRead(systemPath+"/initialization/Project.xlsx");
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
            projectRepo.save(project).subscribe();
        });

        JSONArray employeeList =  this.excelFileRead(systemPath+"/initialization/Employee.xlsx");
        employeeList.forEach(detail->{
            JSONObject json = (JSONObject)detail;
            System.out.println(json);
            Employee employee = new Employee().setId(json.getString("employeeID"))
                    .setName(json.getString("employeeName"))
                    .setCompanyType(json.getString("companyName"))
                    .setTitle(json.getString("title"))
                    .setCategory(json.getString("category"))
                    .setPeriod(json.getInt("startAtPeriod"))
//                    .setPeriodOccurs(json.getInt("PeriodOccurs"))
                    ;
            employeeRepo.save(employee).subscribe();
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

    public  JSONArray excelFileRead(String filename) {

        try {

            FileInputStream excelFile = new FileInputStream(new File(filename));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
            int row =0;
            System.out.println(datatypeSheet.getFirstRowNum()+"--"+datatypeSheet.getLastRowNum());

            JSONArray array = new JSONArray();
            ArrayList<String> title = new ArrayList<>();

            while (iterator.hasNext()) {
                //System.out.print(row+ "   ");

                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();
                JSONObject object = new JSONObject();
                int col =0;

                while (cellIterator.hasNext()) {
                    Cell currentCell = cellIterator.next();
                    if(row == 0){
                        title.add(currentCell.getStringCellValue());
                    }
                    else{
                        if (currentCell.getCellType() == CellType.STRING) {
                            //System.out.print(currentCell.getStringCellValue() + "--");
                            object.put(title.get(col),currentCell.getStringCellValue());
                        } else if (currentCell.getCellType() == CellType.NUMERIC) {
                            //System.out.print(currentCell.getNumericCellValue() + "--");
                            object.put(title.get(col),currentCell.getNumericCellValue());
                        }
                    }
                    col++;

                }
                if(!object.keySet().isEmpty()){
                    array.put(object);
                    //System.out.println(object);
                }

                row ++;
            }
            return array;
        } catch (IOException e) {
            e.printStackTrace();
        }
return new JSONArray();
    }


}
