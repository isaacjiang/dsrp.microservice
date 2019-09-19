package com.edp.organization.models;

import com.edp.system.Utilities;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "org:company")
public class Company {

    @Id
    private String id;
    private String groupId;
    private String groupName;
    private String companyType;
    private String companyName;
    private String nickname;
    private String description;
    private int inPeriod;
    private Boolean enabled;
    private Boolean deleted;


    public Company() {
        this.id = Utilities.GenerateId();
    }


    public String getId() {
        return id;
    }

    public Company setId(String id) {
        this.id = id;
        return this;
    }

    public String getGroupId() {
        return groupId;
    }

    public Company setGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getGroupName() {
        return groupName;
    }

    public Company setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Company setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public Company setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Company setDescription(String description) {
        this.description = description;
        return this;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public Company setEnabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public int getInPeriod() {
        return inPeriod;
    }

    public Company setInPeriod(int inPeriod) {
        this.inPeriod = inPeriod;
        return this;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public Company setDeleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public String getCompanyType() {
        return companyType;
    }

    public Company setCompanyType(String companyType) {
        this.companyType = companyType;
        return this;
    }
}
