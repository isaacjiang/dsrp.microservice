package com.edp.system.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "sys:task")
public class Task {
    @Id
    private String id;
    private String name;
    private String label;
    private String type;
    private int period;
    private String companyType;
    private String processId;
    private String previous;
    private String status;

    public Task() {
    }

    public String getId() {
        return id;
    }

    public Task setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Task setName(String name) {
        this.name = name;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public Task setLabel(String label) {
        this.label = label;
        return this;
    }

    public String getType() {
        return type;
    }

    public Task setType(String type) {
        this.type = type;
        return this;
    }

    public int getPeriod() {
        return period;
    }

    public Task setPeriod(int period) {
        this.period = period;
        return this;
    }

    public String getCompanyType() {
        return companyType;
    }

    public Task setCompanyType(String companyType) {
        this.companyType = companyType;
        return this;
    }

    public String getProcessId() {
        return processId;
    }

    public Task setProcessId(String processId) {
        this.processId = processId;
        return this;
    }

    public String getPrevious() {
        return previous;
    }

    public Task setPrevious(String previous) {
        this.previous = previous;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Task setStatus(String status) {
        this.status = status;
        return this;
    }
}
