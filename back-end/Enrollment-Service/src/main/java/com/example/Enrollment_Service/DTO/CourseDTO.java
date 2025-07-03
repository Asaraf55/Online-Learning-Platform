package com.example.Enrollment_Service.DTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


public class CourseDTO {
    private String coursename;

    public String getCoursename() {
        return coursename;
    }

    public CourseDTO(String coursename) {
        this.coursename = coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }
}
