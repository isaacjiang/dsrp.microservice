package com.edp.account.models;

import com.edp.system.Utilities;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "acc_title")
public class AccTitle {
    @Id
    private String id;
    private String type;
    private String title;
    private String description;
    private int isSummary;
    private int level;



    public AccTitle(){
        setId(Utilities.GenerateId());
    }

    public String getId() {
        return id;
    }

    public AccTitle setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public AccTitle setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public AccTitle setType(String type) {
        this.type = type;
        return this;
    }

    public AccTitle setDescription(String description) {
        this.description = description;
        return this;
    }

    public int isSummary() {
        return isSummary;
    }

    public AccTitle setSummary(int summary) {
        isSummary = summary;
        return this;
    }

    public int getLevel() {
        return level;
    }

    public AccTitle setLevel(int level) {
        this.level = level;
        return this;
    }
}
