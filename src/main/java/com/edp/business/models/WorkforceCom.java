package com.edp.business.models;

import com.edp.system.Utilities;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "workforce")
public class WorkforceCom {
    @Id
    private String id;
    private String groupId;
    private String companyId;
    private String functions;
    private int period;
    private int avWage;
    private int exception;
    private int costOfFire;
    private int costOfHire;
    private int avExpense;
    private int coreEmployeeRate;
    private int recommendBase;


    public WorkforceCom(){
        setId(Utilities.GenerateId());
    }

    public String getId() {
        return id;
    }

    public WorkforceCom setId(String id) {
        this.id = id;
        return this;
    }

    public int getPeriod() {
        return period;
    }

    public WorkforceCom setPeriod(int period) {
        this.period = period;
        return this;
    }

    public String getFunctions() {
        return functions;
    }

    public WorkforceCom setFunctions(String functions) {
        this.functions = functions;
        return this;
    }

    public int getAvWage() {
        return avWage;
    }

    public WorkforceCom setAvWage(int avWage) {
        this.avWage = avWage;
        return this;
    }

    public int getException() {
        return exception;
    }

    public WorkforceCom setException(int exception) {
        this.exception = exception;
        return this;
    }

    public int getCostOfFire() {
        return costOfFire;
    }

    public WorkforceCom setCostOfFire(int costOfFire) {
        this.costOfFire = costOfFire;
        return this;
    }

    public int getCostOfHire() {
        return costOfHire;
    }

    public WorkforceCom setCostOfHire(int costOfHire) {
        this.costOfHire = costOfHire;
        return this;
    }

    public int getAvExpense() {
        return avExpense;
    }

    public WorkforceCom setAvExpense(int avExpense) {
        this.avExpense = avExpense;
        return this;
    }

    public int getCoreEmployeeRate() {
        return coreEmployeeRate;
    }

    public WorkforceCom setCoreEmployeeRate(int coreEmployeeRate) {
        this.coreEmployeeRate = coreEmployeeRate;
        return this;
    }

    public int getRecommendBase() {
        return recommendBase;
    }

    public WorkforceCom setRecommendBase(int recommendBase) {
        this.recommendBase = recommendBase;
        return this;
    }
}
