package com.edp.business.models;

import com.edp.system.Utilities;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "actions")
public class Actions {
    @Id
    private String id;
    private String category;
    private String name;
    private double stressIndex;
    private int immediateIncrementalCost;
    private String companyType;
    private double legitimacyIndex;
    private int periodStart;
    private int periodOccurs;
    private int cosChange;
    private double competenceIndex;
    private double associatedCost;
    private double adaptabilityIndex;

    public Actions(){
        setId(Utilities.GenerateId());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getStressIndex() {
        return stressIndex;
    }

    public void setStressIndex(double stressIndex) {
        this.stressIndex = stressIndex;
    }

    public int getImmediateIncrementalCost() {
        return immediateIncrementalCost;
    }

    public void setImmediateIncrementalCost(int immediateIncrementalCost) {
        this.immediateIncrementalCost = immediateIncrementalCost;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public double getLegitimacyIndex() {
        return legitimacyIndex;
    }

    public void setLegitimacyIndex(double legitimacyIndex) {
        this.legitimacyIndex = legitimacyIndex;
    }

    public int getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(int periodStart) {
        this.periodStart = periodStart;
    }

    public int getPeriodOccurs() {
        return periodOccurs;
    }

    public void setPeriodOccurs(int periodOccurs) {
        this.periodOccurs = periodOccurs;
    }

    public int getCosChange() {
        return cosChange;
    }

    public void setCosChange(int costChange) {
        this.cosChange = costChange;
    }

    public double getCompetenceIndex() {
        return competenceIndex;
    }

    public void setCompetenceIndex(double competenceIndex) {
        this.competenceIndex = competenceIndex;
    }

    public double getAssociatedCost() {
        return associatedCost;
    }

    public void setAssociatedCost(double associatedCost) {
        this.associatedCost = associatedCost;
    }

    public double getAdaptabilityIndex() {
        return adaptabilityIndex;
    }

    public void setAdaptabilityIndex(double adaptabilityIndex) {
        this.adaptabilityIndex = adaptabilityIndex;
    }
}
