package com.edp.business.models;

import com.edp.system.Utilities;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "workforce")
public class Workforce {
    @Id
    private String id;
    private String functions;
    private int period;
    private int avWage;
    private int exception;
    private int costOfFire;
    private int costOfHire;
    private int avExpense;
    private int coreEmployeeRate;
    private int recommendBase;


    public Workforce(){
        setId(Utilities.GenerateId());
    }

    public String getId() {
        return id;
    }

    public Workforce setId(String id) {
        this.id = id;
        return this;
    }

    public int getPeriod() {
        return period;
    }

    public Workforce setPeriod(int period) {
        this.period = period;
        return this;
    }

    public String getFunctions() {
        return functions;
    }

    public Workforce setFunctions(String functions) {
        this.functions = functions;
        return this;
    }

    public int getAvWage() {
        return avWage;
    }

    public Workforce setAvWage(int avWage) {
        this.avWage = avWage;
        return this;
    }

    public int getException() {
        return exception;
    }

    public Workforce setException(int exception) {
        this.exception = exception;
        return this;
    }

    public int getCostOfFire() {
        return costOfFire;
    }

    public Workforce setCostOfFire(int costOfFire) {
        this.costOfFire = costOfFire;
        return this;
    }

    public int getCostOfHire() {
        return costOfHire;
    }

    public Workforce setCostOfHire(int costOfHire) {
        this.costOfHire = costOfHire;
        return this;
    }

    public int getAvExpense() {
        return avExpense;
    }

    public Workforce setAvExpense(int avExpense) {
        this.avExpense = avExpense;
        return this;
    }

    public int getCoreEmployeeRate() {
        return coreEmployeeRate;
    }

    public Workforce setCoreEmployeeRate(int coreEmployeeRate) {
        this.coreEmployeeRate = coreEmployeeRate;
        return this;
    }

    public int getRecommendBase() {
        return recommendBase;
    }

    public Workforce setRecommendBase(int recommendBase) {
        this.recommendBase = recommendBase;
        return this;
    }
}
