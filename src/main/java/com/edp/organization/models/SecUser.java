package com.edp.organization.models;


import com.edp.system.Utilities;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Collections;


@Document(collection = "users")
public class SecUser extends User {
    @Id
    private String uid;
    private String groupId;
    private String companyId;
    private String permission = "1";
    private Boolean authenticated =false;



    public SecUser() { //default
        super("Anonymous", Utilities.passwordEncode("Anonymous"), Collections.singleton(new SimpleGrantedAuthority("ROLE_ANONYMOUS")));
        setUid(Utilities.GenerateId());
    }

    public SecUser(String username, String password) {
        super(username, Utilities.passwordEncode(password), Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        setUid(Utilities.GenerateId());
    }

    public SecUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, Utilities.passwordEncode(password), authorities);
        setUid(Utilities.GenerateId());
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
        return getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ANONYMOUS"));
    }


    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return super.getAuthorities();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return super.isEnabled();
    }

    @Override
    public String toString() {
        return super.toString() + "; Anonymous: " + isAnonymous() + "; Authenticated: " + isAuthenticated() + "; ID: " + getUid();
    }


}
