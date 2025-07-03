package com.example.Feedback_Service.Model;

import com.example.Feedback_Service.DTO.EnrollmentValidationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@FeignClient(name = "ENROLLMENT-SERVICE")
public interface EnrollmentClient {

        @PostMapping("enrollments/validate")
        boolean isStudentEnrolled(@RequestBody EnrollmentValidationDTO request);

}
