package com.example.demo.dto;
public class RegisterRequest {
    private String name; private String email; private String password;
    public RegisterRequest() {}
    public RegisterRequest(String n, String e, String p) { this.name = n; this.email = e; this.password = p; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}