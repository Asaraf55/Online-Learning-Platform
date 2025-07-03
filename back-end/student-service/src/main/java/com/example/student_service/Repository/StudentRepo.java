package com.example.student_service.Repository;

import com.example.student_service.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface StudentRepo extends JpaRepository<Student,Integer> {
    Student findByUsername(String username);
    Student findByEmail(String email);


}
