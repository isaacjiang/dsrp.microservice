package com.edp.business.models;

import com.edp.system.Utilities;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "project")
public class Project {
    @Id
    private String id;
    private String name;
    private String companyType;
    private String status;
    private int periodStart;
    private int periodEnd;
    private int lowestCost;
    private String strategicLogic;
    private String market;
    private int costAtPeriod2;
    private int costAtPeriod3;
    private int costAtPeriod4;
    private int costAtPeriod5;
    private int costAtPeriod6;
    private int costAtPeriod7;
    private int finalAtPeriod;
    private int headcount;
    private double stressIndex;
    private double competenceIndex;

    public Project(){
        setId(Utilities.GenerateId());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(int periodStart) {
        this.periodStart = periodStart;
    }

    public int getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(int periodEnd) {
        this.periodEnd = periodEnd;
    }

    public int getLowestCost() {
        return lowestCost;
    }

    public void setLowestCost(int lowestCost) {
        this.lowestCost = lowestCost;
    }

    public String getStrategicLogic() {
        return strategicLogic;
    }

    public void setStrategicLogic(String strategicLogic) {
        this.strategicLogic = strategicLogic;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public int getCostAtPeriod2() {
        return costAtPeriod2;
    }

    public void setCostAtPeriod2(int costAtPeriod2) {
        this.costAtPeriod2 = costAtPeriod2;
    }

    public int getCostAtPeriod3() {
        return costAtPeriod3;
    }

    public void setCostAtPeriod3(int costAtPeriod3) {
        this.costAtPeriod3 = costAtPeriod3;
    }

    public int getCostAtPeriod4() {
        return costAtPeriod4;
    }

    public void setCostAtPeriod4(int costAtPeriod4) {
        this.costAtPeriod4 = costAtPeriod4;
    }

    public int getCostAtPeriod5() {
        return costAtPeriod5;
    }

    public void setCostAtPeriod5(int costAtPeriod5) {
        this.costAtPeriod5 = costAtPeriod5;
    }

    public int getCostAtPeriod6() {
        return costAtPeriod6;
    }

    public void setCostAtPeriod6(int costAtPeriod6) {
        this.costAtPeriod6 = costAtPeriod6;
    }

    public int getCostAtPeriod7() {
        return costAtPeriod7;
    }

    public void setCostAtPeriod7(int costAtPeriod7) {
        this.costAtPeriod7 = costAtPeriod7;
    }

    public int getFinalAtPeriod() {
        return finalAtPeriod;
    }

    public void setFinalAtPeriod(int finalAtPeriod) {
        this.finalAtPeriod = finalAtPeriod;
    }

    public int getHeadcount() {
        return headcount;
    }

    public void setHeadcount(int headcount) {
        this.headcount = headcount;
    }

    public double getStressIndex() {
        return stressIndex;
    }

    public void setStressIndex(double stressIndex) {
        this.stressIndex = stressIndex;
    }

    public double getCompetenceIndex() {
        return competenceIndex;
    }

    public void setCompetenceIndex(double competenceIndex) {
        this.competenceIndex = competenceIndex;
    }
}
