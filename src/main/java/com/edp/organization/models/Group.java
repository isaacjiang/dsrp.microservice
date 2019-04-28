package com.edp.organization.models;

import com.edp.system.Utilities;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection = "group")
public class Group {

    @Id
    private String id;
    private String groupName;
    private String nickname;
    private String description;
    private Boolean enabled;
    private Boolean deleted;


    public Group() {
        this.id = Utilities.GenerateId();
    }



    public String getId() {
        return id;
    }

    public Group setId(String id) {
        this.id = id;
        return this;
    }

    public String getGroupName() {
        return groupName;
    }

    public Group setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public Group setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Group setDescription(String description) {
        this.description = description;
        return this;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public Group setEnabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }


    public Boolean getDeleted() {
        return deleted;
    }

    public Group setDeleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }


}
