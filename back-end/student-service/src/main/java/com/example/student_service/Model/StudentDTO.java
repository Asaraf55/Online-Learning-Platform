package com.example.student_service.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    private String username;
    private String email;

    public StudentDTO(Builder builder){
        this.username=builder.username;
        this.email=builder.email;
    }
   public static class Builder{
       private String username;
       private String email;
        public Builder username(String username){
            this.username=username;
            return this;
        }
       public Builder email(String email){
           this.email=email;
           return this;
       }
       public StudentDTO build(){
            return new StudentDTO(this);
       }
   }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
