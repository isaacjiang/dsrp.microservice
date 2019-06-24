package com.edp.account.models;


import com.edp.system.Utilities;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "index_entry")
public class IndexEntry {

    @Id
    private String indexId;
    private AccountBook accountBook;
    private String indexType;
    private String indexName;
    private double indexValue;
    private String titleId;
    private String reference;
    private String memo;

    public IndexEntry(){
        setIndexId(Utilities.GenerateId());
    }

    public String getIndexId() {
        return indexId;
    }

    public IndexEntry setIndexId(String indexId) {
        this.indexId = indexId;
        return this;
    }

    public AccountBook getAccountBook() {
        return accountBook;
    }

    public IndexEntry setAccountBook(AccountBook accountBook) {
        this.accountBook = accountBook;
        return this;
    }

    public String getIndexType() {
        return indexType;
    }

    public IndexEntry setIndexType(String indexType) {
        this.indexType = indexType;
        return this;
    }

    public String getIndexName() {
        return indexName;
    }

    public IndexEntry setIndexName(String indexName) {
        this.indexName = indexName;
        return this;
    }

    public double getIndexValue() {
        return indexValue;
    }

    public IndexEntry setIndexValue(double indexValue) {
        this.indexValue = indexValue;
        return this;
    }

    public String getTitleId() {
        return titleId;
    }

    public IndexEntry setTitleId(String titleId) {
        this.titleId = titleId;
        return this;
    }

    public String getReference() {
        return reference;
    }

    public IndexEntry setReference(String reference) {
        this.reference = reference;
        return this;
    }

    public String getMemo() {
        return memo;
    }

    public IndexEntry setMemo(String memo) {
        this.memo = memo;
        return this;
    }
}

