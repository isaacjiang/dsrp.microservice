package com.edp.organization.models;

import com.edp.system.Utilities;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "company_summary")
public class CompanySummary {

    @Id
    private String id;
    private String groupId;
    private String groupName;
    private String companyId;
    private String companyType;
    private String companyName;
    private int employeeTotal;
    private int workforceTotal;
    private int period;


    public CompanySummary() {
        this.id = Utilities.GenerateId();
    }


    public String getId() {
        return id;
    }

    public CompanySummary setId(String id) {
        this.id = id;
        return this;
    }

    public String getGroupId() {
        return groupId;
    }

    public CompanySummary setGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getGroupName() {
        return groupName;
    }

    public CompanySummary setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public String getCompanyName() {
        return companyName;
    }

    public CompanySummary setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }


    public String getCompanyType() {
        return companyType;
    }

    public CompanySummary setCompanyType(String companyType) {
        this.companyType = companyType;
        return this;
    }

    public String getCompanyId() {
        return companyId;
    }

    public CompanySummary setCompanyId(String companyId) {
        this.companyId = companyId;
        return this;
    }

    public int getEmployeeTotal() {
        return employeeTotal;
    }

    public CompanySummary setEmployeeTotal(int employeeTotal) {
        this.employeeTotal = employeeTotal;
        return this;
    }

    public int getWorkforceTotal() {
        return workforceTotal;
    }

    public CompanySummary setWorkforceTotal(int workforceTotal) {
        this.workforceTotal = workforceTotal;
        return this;
    }

    public int getPeriod() {
        return period;
    }

    public CompanySummary setPeriod(int period) {
        this.period = period;
        return this;
    }
}
