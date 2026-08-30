package com.foodie.app.models.requests;

public class RegisterRequest {
    private String full_name;
    private String email;
    private String password;

    public RegisterRequest(String full_name, String email, String password) {
        this.full_name = full_name;
        this.email = email;
        this.password = password;
    }

    public String getFullName() { return full_name; }
    public void setFullName(String full_name) { this.full_name = full_name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
