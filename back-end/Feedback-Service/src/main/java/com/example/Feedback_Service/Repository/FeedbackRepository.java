package com.example.Feedback_Service.Repository;

import com.example.Feedback_Service.Model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Long> {
    boolean existsByStudentEmailAndCourseName(String studentEmail, String courseName);

    List<Feedback> findByCourseName(String courseName);

    Optional<Feedback> findByStudentEmailAndCourseName(String studentEmail, String courseName);

    void deleteByStudentEmailAndCourseName(String studentEmail, String courseName);
}
