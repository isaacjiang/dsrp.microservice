package com.edp.account.models;


import com.sun.org.apache.bcel.internal.generic.PUSH;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
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

    public AccountBook next(){
        return new AccountBook(this.companyId,this.period+1==0?1:this.period+1);
    }
    public AccountBook last(){
        return new AccountBook(this.companyId,this.period-1==0?-1:this.period-1);
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
