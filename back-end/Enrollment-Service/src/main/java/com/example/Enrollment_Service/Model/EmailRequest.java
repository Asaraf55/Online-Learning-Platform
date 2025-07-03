package com.example.Enrollment_Service.Model;

public class EmailRequest {
    private String email;

    public EmailRequest(String studentEmail) {
        this.email = studentEmail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
