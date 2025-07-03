package com.example.Enrollment_Service.Model;

import com.example.Enrollment_Service.DTO.CourseDTO;
import com.example.Enrollment_Service.Security.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "COURSE-SERVICE", configuration = FeignClientConfig.class)
public interface CourseClient {
    @PostMapping("/course/name")
    public abstract ResponseEntity<CourseDTO> getCourseByName(@RequestBody CourseDTO request);
}
