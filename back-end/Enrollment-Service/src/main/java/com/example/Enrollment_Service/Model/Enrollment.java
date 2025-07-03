package com.example.Enrollment_Service.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;

@Entity
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentEmail;
    private String studentName;

    private String courseName;

    public Enrollment(){}

    private Enrollment(Builder builder) {
        this.studentEmail = builder.studentEmail;
        this.studentName = builder.studentName;
        this.courseName = builder.courseName;
    }

    // Static Builder class
    public static class Builder {
        private String studentEmail;
        private String studentName;
        private String courseName;

        public Builder studentEmail(String studentEmail) {
            this.studentEmail = studentEmail;
            return this;
        }

        public Builder studentName(String studentName) {
            this.studentName = studentName;
            return this;
        }

        public Builder courseName(String courseName) {
            this.courseName = courseName;
            return this;
        }

        public Enrollment build() {
            return new Enrollment(this);
        }
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
