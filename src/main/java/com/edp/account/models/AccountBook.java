package com.edp.account.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection="account_book")
public class AccountBook {

    @Id
    private String id;


    private String type;
    private String companyId;
    private int period;
    private String description;

    public AccountBook(String companyId, int period) {
        this.id = companyId + "#" + period;
        this.companyId = companyId;
        this.period = period;
    }

    public String getId() {
        return id;
    }

    public AccountBook setId(String id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return type;
    }

    public AccountBook setType(String type) {
        this.type = type;
        return this;
    }

    public AccountBook setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getCompanyId() {
        return companyId;
    }

    public AccountBook setCompanyId(String companyId) {
        this.companyId = companyId;
        return this;
    }

    public int getPeriod() {
        return period;
    }

    public AccountBook setPeriod(int period) {
        this.period = period;
        return this;
    }

    public String getDescription() {
        return description;
    }


    @Override
    public int hashCode() { return this.id.hashCode(); }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AccountBook) {
          return Objects.equals(this.id, ((AccountBook) obj).id);
        }
        return false;
    }
}
