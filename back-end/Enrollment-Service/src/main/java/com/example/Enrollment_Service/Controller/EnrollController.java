package com.example.Enrollment_Service.Controller;

import com.example.Enrollment_Service.Model.Enrollment;
import com.example.Enrollment_Service.Model.Enrollrequest;
import com.example.Enrollment_Service.Service.EnrollmentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enrollments")
public class EnrollController {

    private final EnrollmentService enrollmentService;

    public EnrollController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/enrollment")
    public Enrollment enrollStudent(@RequestBody Enrollrequest request) {
            return enrollmentService.enroll(request);
    }

    @PostMapping("/validate")
   public boolean isStudentEnrolled(@RequestBody Enrollrequest request){
       return enrollmentService.validate(request);
    }
}
