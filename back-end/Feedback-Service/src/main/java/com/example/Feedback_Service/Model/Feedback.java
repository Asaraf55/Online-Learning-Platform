package com.example.Feedback_Service.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String studentEmail;



    private String feedbackText;
    private String courseName;
    private int rating;

    public Feedback(){

    }

    public Feedback(Builder builder){
        this.courseName =builder.coursename;
        this.feedbackText =builder.feedbacktext;
        this.studentEmail =builder.studentemail;

    }
    public static class Builder{
       private String coursename;
       private String feedbacktext;
       private String studentemail;

       public Builder coursename(String name){
           this.coursename=name;
           return this;
       }
        public Builder feedbacktext(String feedbacktext){
            this.feedbacktext=feedbacktext;
            return this;
        }
        public Builder studentemail(String studentemail){
            this.studentemail=studentemail;
            return this;
        }
        public Feedback build(){
           return new Feedback(this);
        }

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }
}
