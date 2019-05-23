package com.edp.organization.models;


import com.edp.system.Utilities;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;


@Document(collection = "org:users")
public class SecUser {
    @Id
    private String uid;
    private String groupId;
    private String companyId;
    private String permission = "1";
    private Boolean authenticated =false;
    private String password;
    private String username;
    private String authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;



    public SecUser() { //default
        setUid(Utilities.GenerateId());
        setUsername("Anonymous");
        setPassword(passwordEncode("Anonymous"));
        setAuthorities("ROLE_ANONYMOUS");
    }

    public SecUser(String username, String password) {
        setUid(Utilities.GenerateId());
        setUsername(username);
        setPassword(passwordEncode(password));
        setAuthorities("ROLE_USER");
    }

    public SecUser(String username, String password, String authorities) {
        setUid(Utilities.GenerateId());
        setUsername(username);
        setPassword(passwordEncode(password));
        setAuthorities(authorities);


    }

    private String passwordEncode(String password) {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return encoder.encode(password);
    }


    public String getUid() {
        return uid;
    }

    public SecUser setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getGroupId() {
        return groupId;
    }

    public SecUser setGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getCompanyId() {
        return companyId;
    }

    public SecUser setCompanyId(String companyId) {
        this.companyId = companyId;
        return this;
    }

    public String getPermission() {
        return permission;
    }

    public SecUser setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public Boolean getAuthenticated() {
        return authenticated;
    }

    public SecUser setAuthenticated(Boolean authenticated) {
        this.authenticated = authenticated;
        return this;
    }

    public Boolean isAuthenticated() {

        return authenticated;
    }

    public Boolean isAnonymous() {
        return getAuthorities().equals("ROLE_ANONYMOUS");
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }


    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }



}
