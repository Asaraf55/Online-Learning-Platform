package com.example.course_service.Service;

import com.example.course_service.Model.Course;
import com.example.course_service.Model.CourseDTO;
import com.example.course_service.Repository.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
   @Autowired
    private CourseRepo courseRepo;


    public List<Course> getAll() {
        return courseRepo.findAll();
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepo.findById(id);
    }


    public Course createCourse(Course course) {
        return courseRepo.save(course);
    }

    public void deleteCourse(Long id) {
        courseRepo.deleteById(id);
    }

    public ResponseEntity<CourseDTO> getCourseByName(String coursename) {
        Course course = courseRepo.findByCoursename(coursename)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        CourseDTO dto = new CourseDTO.Builder()
                .coursename(course.getCoursename())
                .build();

        return ResponseEntity.ok(dto);
    }


}
