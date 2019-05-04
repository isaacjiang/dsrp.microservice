package com.edp.system.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "business")
public class Action {
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
    public Action(){}

    public String getId() {
        return id;
    }

    public Action setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Action setName(String name) {
        this.name = name;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public Action setLabel(String label) {
        this.label = label;
        return this;
    }

    public String getType() {
        return type;
    }

    public Action setType(String type) {
        this.type = type;
        return this;
    }

    public int getPeriod() {
        return period;
    }

    public Action setPeriod(int period) {
        this.period = period;
        return this;
    }

    public String getCompanyType() {
        return companyType;
    }

    public Action setCompanyType(String companyType) {
        this.companyType = companyType;
        return this;
    }

    public String getProcessId() {
        return processId;
    }

    public Action setProcessId(String processId) {
        this.processId = processId;
        return this;
    }

    public String getPrevious() {
        return previous;
    }

    public Action setPrevious(String previous) {
        this.previous = previous;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Action setStatus(String status) {
        this.status = status;
        return this;
    }
}
