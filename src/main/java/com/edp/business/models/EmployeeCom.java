package com.edp.business.models;

import com.edp.system.Utilities;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "employee")
public class EmployeeCom {
    @Id
    private String id;
    private String name;
    private String status;
    private int period;

    public EmployeeCom() {
        setId(Utilities.GenerateId());
    }

    public String getId() {
        return id;
    }

    public EmployeeCom setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public EmployeeCom setName(String name) {
        this.name = name;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public EmployeeCom setStatus(String status) {
        this.status = status;
        return this;
    }


    public int getPeriod() {
        return period;
    }

    public EmployeeCom setPeriod(int period) {
        this.period = period;
        return this;
    }
}
