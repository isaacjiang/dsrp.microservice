package com.edp.business.models;

import com.edp.system.Utilities;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "budget")
public class Budget {
    @Id
    private String id;
    private String budgetType;
    private String budgetName;
    private boolean summaryFlag;
    private String parent;
    private int budgetLevel;
    private String accountDescId;
    private boolean calculateFlag;
    private String description;

    public Budget() {
        setId(Utilities.GenerateId());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBudgetType() {
        return budgetType;
    }

    public void setBudgetType(String budgetType) {
        this.budgetType = budgetType;
    }

    public String getBudgetName() {
        return budgetName;
    }

    public void setBudgetName(String budgetName) {
        this.budgetName = budgetName;
    }

    public boolean getSummaryFlag() {
        return summaryFlag;
    }

    public void setSummaryFlag(boolean summaryFlag) {
        this.summaryFlag = summaryFlag;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public int getBudgetLevel() {
        return budgetLevel;
    }

    public void setBudgetLevel(int budgetLevel) {
        this.budgetLevel = budgetLevel;
    }

    public String getAccountDescId() {
        return accountDescId;
    }

    public void setAccountDescId(String accountDescId) {
        this.accountDescId = accountDescId;
    }

    public boolean getCalculateFlag() {
        return calculateFlag;
    }

    public void setCalculateFlag(boolean calculateFlag) {
        this.calculateFlag = calculateFlag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
