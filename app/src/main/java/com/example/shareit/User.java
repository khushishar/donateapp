package com.example.shareit;

public class User {

    private String name, phone, email, usertype, userId;

    public User() {
    }

    public User(String name, String phone, String email, String usertype, String userId) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.usertype = usertype;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsertype() {
        return usertype;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }
}
