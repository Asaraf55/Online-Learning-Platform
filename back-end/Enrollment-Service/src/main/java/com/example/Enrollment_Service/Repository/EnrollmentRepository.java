package com.example.Enrollment_Service.Repository;

import com.example.Enrollment_Service.Model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {
    Enrollment findByStudentEmailAndCourseName(String studentEmail, String courseName);
}
