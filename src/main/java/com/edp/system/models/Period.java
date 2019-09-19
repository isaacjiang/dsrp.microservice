package com.edp.system.models;

import com.edp.organization.models.Company;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.concurrent.ConcurrentHashMap;


@Document(collection = "sys:period")
public class Period {
    @Id
    private String id;
    @NotNull
    private int period;
    private String label;
    private ConcurrentHashMap<String, Company> companies;
    private String status;
    private boolean enabled;

    public Period() {
    }

    public String getId() {
        return id;
    }

    public Period setId(String id) {
        this.id = id;
        return this;
    }

    public int getPeriod() {
        return period;
    }

    public Period setPeriod(int period) {
        this.period = period;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public Period setLabel(String label) {
        this.label = label;
        return this;
    }

    public ConcurrentHashMap<String, Company> getCompanies() {
        return companies;
    }

    public Period setCompanies(ConcurrentHashMap<String, Company> companies) {
        this.companies = companies;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Period setStatus(String status) {
        this.status = status;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Period setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }
}
