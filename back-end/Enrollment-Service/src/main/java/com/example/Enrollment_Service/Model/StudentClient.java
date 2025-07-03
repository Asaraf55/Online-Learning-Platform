package com.example.Enrollment_Service.Model;

import com.example.Enrollment_Service.DTO.StudentDTO;
import com.example.Enrollment_Service.Security.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "STUDENT-SERVICE",configuration = FeignClientConfig.class)
public interface StudentClient {
    @PostMapping("/email")
    public ResponseEntity<StudentDTO> getStudentByEmail(@RequestBody EmailRequest request);

}
