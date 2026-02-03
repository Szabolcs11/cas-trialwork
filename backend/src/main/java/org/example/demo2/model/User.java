package org.example.demo2.model;

import java.util.Date;

public class User {
    private int id;
    private String email;
    private boolean isAdmin;
    private Date lastLogin;

    public User() {}

    public User(int id, String email, boolean isAdmin, Date lastLogin) {
        this.id = id;
        this.email = email;
        this.isAdmin = isAdmin;
        this.lastLogin = lastLogin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }
}
