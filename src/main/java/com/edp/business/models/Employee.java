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
    private int minimumSalary;
    private String status;
    private String name;
    private double legitimacyIndex;
    private double competenceIndex;
    private int period;
    private boolean willingToMove;
    private String avatarId;
    private String resumeId;

    public Employee() {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public int getMinimumSalary() {
        return minimumSalary;
    }

    public void setMinimumSalary(int minimumSalary) {
        this.minimumSalary = minimumSalary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLegitimacyIndex() {
        return legitimacyIndex;
    }

    public void setLegitimacyIndex(double legitimacyIndex) {
        this.legitimacyIndex = legitimacyIndex;
    }

    public double getCompetenceIndex() {
        return competenceIndex;
    }

    public void setCompetenceIndex(double competenceIndex) {
        this.competenceIndex = competenceIndex;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public boolean getWillingToMove() {
        return willingToMove;
    }

    public void setWillingToMove(boolean willingToMove) {
        this.willingToMove = willingToMove;
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
