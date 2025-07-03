package com.example.course_service.Model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
public class CourseDTO {
    private String coursename;

    public CourseDTO() {
    }

    public CourseDTO(Builder builder){
        this.coursename =builder.coursename;
    }
   public static class Builder{
        private String coursename;
        public Builder coursename(String name){
            this.coursename=name;
            return this;
        }

        public CourseDTO build(){
            return new CourseDTO(this);
        }
   }
    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }
}
