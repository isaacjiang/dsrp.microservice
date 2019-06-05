package com.edp.account.models;

import com.edp.system.DatabaseService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;



public class AccountBook {

    private String id;
    private String companyId;
    private int period;
    private String description;

    public AccountBook(String companyId, int period ){
     this.id = companyId+"#"+period;
     this.companyId=companyId;
     this.period = period;
    }

    public String getId() {
        return id;
    }

    public AccountBook setId(String id) {
        this.id = id;
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



    public Document document(){
        Document doc = new Document();
        doc.put("_id",id);
       doc.append("companyId",companyId);
       doc.append("period",period);
       doc.append("description",description);
//       System.out.println(doc);
       return  doc;

    }
}
