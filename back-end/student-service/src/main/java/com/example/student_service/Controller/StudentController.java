package com.example.student_service.Controller;

import com.example.student_service.Model.EmailRequest;
import com.example.student_service.Model.Student;
import com.example.student_service.Model.StudentDTO;
import com.example.student_service.Model.StudentRequest;
import com.example.student_service.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController {
    @Autowired
    private StudentService studentService;



    @PostMapping("/register")
    public String registerUser(@RequestBody Student user) {
        return studentService.registerStudent(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody StudentRequest request) {
        return studentService.authenticateStudent(request);
    }

    @PostMapping("/email")
    public ResponseEntity<StudentDTO> getStudentByEmail(@RequestBody EmailRequest request) {
       return studentService.getStudentByEmail(request.getEmail());
    }
}
