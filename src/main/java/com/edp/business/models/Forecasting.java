package com.edp.business.models;

import com.edp.system.Utilities;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "forecasting")
public class Forecasting {
    @Id
    private String id;
    private String groupId;
    private String companyId;
    private int period;
    private int b2b;
    private int b2c;
    private int newoffering;
    private int total;

    public Forecasting() {
        setId(Utilities.GenerateId());
    }

    public String getId() {
        return id;
    }

    public Forecasting setId(String id) {
        this.id = id;
        return this;
    }

    public String getGroupId() {
        return groupId;
    }

    public Forecasting setGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getCompanyId() {
        return companyId;
    }

    public Forecasting setCompanyId(String companyId) {
        this.companyId = companyId;
        return this;
    }

    public int getPeriod() {
        return period;
    }

    public Forecasting setPeriod(int period) {
        this.period = period;
        return this;
    }

    public int getB2b() {
        return b2b;
    }

    public Forecasting setB2b(int b2b) {
        this.b2b = b2b;
        return this;
    }

    public int getB2c() {
        return b2c;
    }

    public Forecasting setB2c(int b2c) {
        this.b2c = b2c;
        return this;
    }

    public int getNewoffering() {
        return newoffering;
    }

    public Forecasting setNewoffering(int newoffering) {
        this.newoffering = newoffering;
        return this;
    }

    public int getTotal() {
        return total;
    }

    public Forecasting setTotal(int total) {
        this.total = total;
        return this;
    }
}
