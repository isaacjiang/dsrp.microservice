package com.edp.business.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "corporateacquisition")
public class CorporateAcquisition {
    @Id
    private int id;
    private int developmentCost;
    private String name;
    private String companyId;
    private double legitimacy;
    private String company;
    private int startAtPeriod;
    private double platformIndex;
    private double competenceIndex;
    private double minimumBid;
    private String type;
    private int numberDevelopmentEmployees;
    private int numberOfCustomers;

    public CorporateAcquisition() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDevelopmentCost() {
        return developmentCost;
    }

    public void setDevelopmentCost(int developmentCost) {
        this.developmentCost = developmentCost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public double getLegitimacy() {
        return legitimacy;
    }

    public void setLegitimacy(double legitimacy) {
        this.legitimacy = legitimacy;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getStartAtPeriod() {
        return startAtPeriod;
    }

    public void setStartAtPeriod(int startAtPeriod) {
        this.startAtPeriod = startAtPeriod;
    }

    public double getPlatformIndex() {
        return platformIndex;
    }

    public void setPlatformIndex(double platformIndex) {
        this.platformIndex = platformIndex;
    }

    public double getCompetenceIndex() {
        return competenceIndex;
    }

    public void setCompetenceIndex(double competenceIndex) {
        this.competenceIndex = competenceIndex;
    }

    public double getMinimumBid() {
        return minimumBid;
    }

    public void setMinimumBid(double minimumBid) {
        this.minimumBid = minimumBid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumberDevelopmentEmployees() {
        return numberDevelopmentEmployees;
    }

    public void setNumberDevelopmentEmployees(int numberDevelopmentEmployees) {
        this.numberDevelopmentEmployees = numberDevelopmentEmployees;
    }

    public int getNumberOfCustomers() {
        return numberOfCustomers;
    }

    public void setNumberOfCustomers(int numberOfCustomers) {
        this.numberOfCustomers = numberOfCustomers;
    }
}
