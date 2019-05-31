package com.edp.account.models;

import com.edp.system.Utilities;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "acc_journal_entry")
public class AccJournalEntry {
    @Id
    private String id;
    private String type;
    private String companyId;
    private int period;
    private String titleId;
    private String title;
    private double debits;
    private double credits;
    private String reference;
    private String memo;



    public AccJournalEntry(){
        setId(Utilities.GenerateId());
    }

    public String getId() {
        return id;
    }

    public AccJournalEntry setId(String id) {
        this.id = id;
        return this;
    }

    public String getCompanyId() {
        return companyId;
    }

    public AccJournalEntry setCompanyId(String companyId) {
        this.companyId = companyId;
        return this;
    }

    public int getPeriod() {
        return period;
    }

    public AccJournalEntry setPeriod(int period) {
        this.period = period;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public AccJournalEntry setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getType() {
        return type;
    }

    public AccJournalEntry setType(String type) {
        this.type = type;
        return this;
    }

    public String getTitleId() {
        return titleId;
    }

    public AccJournalEntry setTitleId(String titleId) {
        this.titleId = titleId;
        return this;
    }

    public double getDebits() {
        return debits;
    }

    public AccJournalEntry setDebits(double debits) {
        this.debits = debits;
        return this;
    }

    public double getCredits() {
        return credits;
    }

    public AccJournalEntry setCredits(double credits) {
        this.credits = credits;
        return this;
    }

    public String getReference() {
        return reference;
    }

    public AccJournalEntry setReference(String reference) {
        this.reference = reference;
        return this;
    }

    public String getMemo() {
        return memo;
    }

    public AccJournalEntry setMemo(String memo) {
        this.memo = memo;
        return this;
    }


}
