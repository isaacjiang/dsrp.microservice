package com.edp.business.models;

import com.edp.system.Utilities;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "employee")
public class Employee {
    @Id
    private String id;
    private String category;
    private String title;
    private String companyType;
    private String movable;
    private int salary;
    private String name;
    private String status;
    private double stressIndex;
    private double legitimacyIndex;
    private double competenceIndex;
    private double adaptabilityIndex;
    private int period;
    private String avatarId;
    private String resumeId;

    public Employee(){
        setId(Utilities.GenerateId());
    }

    public String getId() {
        return id;
    }

    public Employee setId(String id) {
        this.id = id;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public Employee setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Employee setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getCompanyType() {
        return companyType;
    }

    public Employee setCompanyType(String companyType) {
        this.companyType = companyType;
        return this;
    }

    public String getMovable() {
        return movable;
    }

    public Employee setMovable(String movable) {
        this.movable = movable;
        return this;
    }

    public int getSalary() {
        return salary;
    }

    public Employee setSalary(int salary) {
        this.salary = salary;
        return this;
    }

    public String getName() {
        return name;
    }

    public Employee setName(String name) {
        this.name = name;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Employee setStatus(String status) {
        this.status = status;
        return this;
    }

    public double getStressIndex() {
        return stressIndex;
    }

    public Employee setStressIndex(double stressIndex) {
        this.stressIndex = stressIndex;
        return this;
    }

    public double getLegitimacyIndex() {
        return legitimacyIndex;
    }

    public Employee setLegitimacyIndex(double legitimacyIndex) {
        this.legitimacyIndex = legitimacyIndex;
        return this;
    }

    public double getCompetenceIndex() {
        return competenceIndex;
    }

    public Employee setCompetenceIndex(double competenceIndex) {
        this.competenceIndex = competenceIndex;
        return this;
    }

    public double getAdaptabilityIndex() {
        return adaptabilityIndex;
    }

    public Employee setAdaptabilityIndex(double adaptabilityIndex) {
        this.adaptabilityIndex = adaptabilityIndex;
        return this;
    }

    public int getPeriod() {
        return period;
    }

    public Employee setPeriod(int period) {
        this.period = period;
        return this;
    }

    public String getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
    }

    public String getResumeId() {
        return resumeId;
    }

    public void setResumeId(String resumeId) {
        this.resumeId = resumeId;
    }
}
