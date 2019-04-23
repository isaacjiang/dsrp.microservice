package com.edp.organization.models;

import com.edp.system.Tools;
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

    private String permission;


    public SecUser() { //default
        super("Anonymous", "Anonymous", Collections.singleton(new SimpleGrantedAuthority("Anonymous")));
        setUid(Tools.GenerateId());
    }

    public SecUser(String username, String password) {
        super(username, password, Collections.singleton(new SimpleGrantedAuthority("USER")));
        setUid(Tools.GenerateId());
    }

    public SecUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        setUid(Tools.GenerateId());
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Boolean isAuthenticated() {
        return getCompanyId()!=null && getGroupId() !=null;
    }

    public Boolean isAnonymous() {
        return getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("Anonymous"));
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
