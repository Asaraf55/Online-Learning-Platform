package com.example.Feedback_Service.DTO;

public class EnrollmentValidationDTO {
    private String studentEmail;
    private String courseName;
    public EnrollmentValidationDTO(){}

    public EnrollmentValidationDTO(String studentEmail, String courseName) {
        this.courseName=courseName;
        this.studentEmail=studentEmail;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
