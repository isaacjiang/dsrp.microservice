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
    private int headcount;
    private double stressIndex;
    private double legitimacyIndex;
    private double competenceIndex;
    private double adaptabilityIndex;


    public Project(){
        setId(Utilities.GenerateId());
    }

    public String getId() {
        return id;
    }

    public Project setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Project setName(String name) {
        this.name = name;
        return this;
    }

    public String getCompanyType() {
        return companyType;
    }

    public Project setCompanyType(String companyType) {
        this.companyType = companyType;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Project setStatus(String status) {
        this.status = status;
        return this;
    }

    public int getPeriodStart() {
        return periodStart;
    }

    public Project setPeriodStart(int periodStart) {
        this.periodStart = periodStart;
        return this;
    }

    public int getPeriodEnd() {
        return periodEnd;
    }

    public Project setPeriodEnd(int periodEnd) {
        this.periodEnd = periodEnd;
        return this;
    }

    public int getLowestCost() {
        return lowestCost;
    }

    public Project setLowestCost(int lowestCost) {
        this.lowestCost = lowestCost;
        return this;
    }

    public String getStrategicLogic() {
        return strategicLogic;
    }

    public Project setStrategicLogic(String strategicLogic) {
        this.strategicLogic = strategicLogic;
        return this;
    }

    public String getMarket() {
        return market;
    }

    public Project setMarket(String market) {
        this.market = market;
        return this;
    }

    public int getCostAtPeriod2() {
        return costAtPeriod2;
    }

    public Project setCostAtPeriod2(int costAtPeriod2) {
        this.costAtPeriod2 = costAtPeriod2;
        return this;
    }

    public int getCostAtPeriod3() {
        return costAtPeriod3;
    }

    public Project setCostAtPeriod3(int costAtPeriod3) {
        this.costAtPeriod3 = costAtPeriod3;
        return this;
    }

    public int getCostAtPeriod4() {
        return costAtPeriod4;
    }

    public Project setCostAtPeriod4(int costAtPeriod4) {
        this.costAtPeriod4 = costAtPeriod4;
        return this;
    }

    public int getCostAtPeriod5() {
        return costAtPeriod5;
    }

    public Project setCostAtPeriod5(int costAtPeriod5) {
        this.costAtPeriod5 = costAtPeriod5;
        return this;
    }

    public int getCostAtPeriod6() {
        return costAtPeriod6;
    }

    public Project setCostAtPeriod6(int costAtPeriod6) {
        this.costAtPeriod6 = costAtPeriod6;
        return this;
    }

    public int getCostAtPeriod7() {
        return costAtPeriod7;
    }

    public Project setCostAtPeriod7(int costAtPeriod7) {
        this.costAtPeriod7 = costAtPeriod7;
        return this;
    }

    public int getHeadcount() {
        return headcount;
    }

    public Project setHeadcount(int headcount) {
        this.headcount = headcount;
        return this;
    }

    public double getStressIndex() {
        return stressIndex;
    }

    public Project setStressIndex(double stressIndex) {
        this.stressIndex = stressIndex;
        return this;
    }

    public double getLegitimacyIndex() {
        return legitimacyIndex;
    }

    public Project setLegitimacyIndex(double legitimacyIndex) {
        this.legitimacyIndex = legitimacyIndex;
        return this;
    }

    public double getCompetenceIndex() {
        return competenceIndex;
    }

    public Project setCompetenceIndex(double competenceIndex) {
        this.competenceIndex = competenceIndex;
        return this;
    }

    public double getAdaptabilityIndex() {
        return adaptabilityIndex;
    }

    public Project setAdaptabilityIndex(double adaptabilityIndex) {
        this.adaptabilityIndex = adaptabilityIndex;
        return this;
    }
}
