package com.example.course_service.Repository;

import com.example.course_service.Model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepo extends JpaRepository<Course,Long> {
    Optional<Course> findByCoursename(String coursename);
}
