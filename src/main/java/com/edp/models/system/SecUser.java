package com.edp.models.system;

import com.edp.system.Tools;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Collections;


@Document(collection = "sys:users")
public class SecUser extends User {
    @Id
    private String uid;
    private Boolean anonymous;
    private Boolean authenticated;

    private String email;
    private String license;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }


    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Boolean getAuthenticated() {
        return authenticated;
    }

    public SecUser setAuthenticated(Boolean is_authenticated) {
        this.authenticated = is_authenticated;
        this.anonymous = !is_authenticated;
        return this;
    }

    public Boolean getAnonymous() {
        return anonymous;
    }

    public SecUser setAnonymous(Boolean is_anonymous) {
        this.anonymous = is_anonymous;
        return this;
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
        return super.toString() + "; Anonymous: " + getAnonymous() + "; Authenticated: " + getAuthenticated() + "; ID: " + getUid();
    }


}
