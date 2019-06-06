package com.edp.account.models;

import com.edp.system.Utilities;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "acc_journal_entry")
public class AccJournalEntry {
    @Id
    private String id;
    private AccountBook accountBook;
    private String titleId;
    private String title;
    private double value;
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

    public AccountBook getAccountBook() {
        return accountBook;
    }

    public AccJournalEntry setAccountBook(AccountBook accountBook) {
        this.accountBook = accountBook;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public AccJournalEntry setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getTitleId() {
        return titleId;
    }

    public AccJournalEntry setTitleId(String titleId) {
        this.titleId = titleId;
        return this;
    }

    public double getValue() {
        return value;
    }

    public AccJournalEntry setValue(double value) {
        this.value = value;
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

    @Override
    public int hashCode() { return this.id.hashCode(); }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AccJournalEntry) {
            return Objects.equals(this.id, ((AccJournalEntry) obj).id);
        }
        return false;
    }

}
