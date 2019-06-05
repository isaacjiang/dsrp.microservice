package com.edp.account.models;

import com.edp.system.DatabaseService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;



public class AccountBook {

    @Autowired
    private DatabaseService databaseService;

    private String id;
    private String companyId;
    private int period;
    private String description;
    List<AccJournalEntry> journalEntries = new ArrayList<>();



    public AccountBook(String companyId, int period ){
     this.id = companyId+"-"+period;
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

    public List<AccJournalEntry> getJournalEntries() {
        return journalEntries;
    }

    public AccountBook setJournalEntries(List<AccJournalEntry> journalEntries) {
        this.journalEntries = journalEntries;
        return this;
    }

    public void save(){
        try{
            System.out.println(id+"  "+databaseService.getOpdb());
            if(!databaseService.getOpdb().collectionExists(id)) {

                databaseService.getOpdb().createCollection(id);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        Document doc = new Document();
       doc.put("id",id);
       doc.append("companyId",companyId);
       doc.append("period",period);
       doc.append("description",description);
       System.out.println(doc);
//       databaseService.getOpdb().getCollection(this.id).insertOne(doc);
    }
}
