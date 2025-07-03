package com.example.Enrollment_Service.Service;

import com.example.Enrollment_Service.DTO.CourseDTO;
import com.example.Enrollment_Service.DTO.StudentDTO;
import com.example.Enrollment_Service.Model.*;
import com.example.Enrollment_Service.Repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentService {
    private final EnrollmentRepository repository;
    private final StudentClient studentClient;
    private final CourseClient courseClient;

    @Autowired
    private StreamBridge streamBridge;

    public EnrollmentService(EnrollmentRepository repository, StudentClient studentClient, CourseClient courseClient) {
        this.repository = repository;
        this.studentClient = studentClient;
        this.courseClient = courseClient;
    }

    public Enrollment enroll(Enrollrequest request) {
        // Check if already enrolled
        Enrollment enroll=repository.findByStudentEmailAndCourseName(request.getStudentEmail(), request.getCourseName());
                if(enroll!=null){
                    throw new RuntimeException("Student already enrolled in this course.");
                }

        // Fetch student and course
        StudentDTO student = studentClient.getStudentByEmail(new EmailRequest(request.getStudentEmail())).getBody();
        CourseDTO course = courseClient.getCourseByName(new CourseDTO(request.getCourseName())).getBody();

        // Save enrollment
        Enrollment enrollment = new Enrollment.Builder()
                .studentEmail(student.getEmail())
                .studentName(student.getUsername())
                .courseName(course.getCoursename())
                .build();
        repository.save(enrollment);
        NotificationMessage message = new NotificationMessage(
                student.getEmail(),
                "Enrollment Successful",
                "You have been enrolled in the course: " + course.getCoursename()
        );

        // Send message to RabbitMQ
        streamBridge.send("notificationProducer-out-0", MessageBuilder.withPayload(message).build());

        return enrollment ;
    }

    public boolean validate(Enrollrequest request) {
        Enrollment enrollment=repository.findByStudentEmailAndCourseName(request.getStudentEmail(),request.getCourseName());
        return enrollment!=null;
    }



//    public List<Enrollment> getAllEnrollments() {
//        return repository.findAll();
//    }
}
