package com.example.shareit;

public class User {

    private String name, phone, email, userId;

    public User() {
    }

    public User(String name, String phone, String email, String userId) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.userId = userId;
    }

    public String getName() {
        return name;
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
