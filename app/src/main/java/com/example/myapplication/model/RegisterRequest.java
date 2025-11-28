package com.example.myapplication.model;

public class RegisterRequest {
    private String name;
    private String username;
    private String email;
    private String password;

    public RegisterRequest(String name, String username, String email, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
