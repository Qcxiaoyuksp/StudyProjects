package com.textfightinggame.model;

public class User {
    private final String id;
    private final String username;
    private String password;
    private final String phone;
    private boolean status;

    public User(String id, String username, String password, String phone, boolean status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

