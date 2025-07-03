package com.example.student_service.Service;

import com.example.student_service.Model.NotificationMessage;
import com.example.student_service.Model.Student;
import com.example.student_service.Model.StudentDTO;
import com.example.student_service.Model.StudentRequest;
import com.example.student_service.Repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private StreamBridge streamBridge;

    public String registerStudent(Student user) {
        Student student=studentRepo.findByEmail(user.getEmail());

        if(student!=null){
            throw new IllegalArgumentException("Already registered");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save the student in the database
        studentRepo.save(user);
        NotificationMessage message = new NotificationMessage(
                user.getEmail(),
                "Registration Successful",
                "Welcome " + user.getUsername() + "! You have successfully registered."
        );

        System.out.println("Sending message: " + message.getEmail());

        streamBridge.send("notificationProducer-out-0", MessageBuilder.withPayload(message).build());
        return "Student registered successfully!";
    }
    public String authenticateStudent(StudentRequest user) {

        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );

            if (!authentication.isAuthenticated()) {
                throw new BadCredentialsException("Invalid email or password");
            }

            Student student = studentRepo.findByEmail(user.getEmail());
            if (student == null) {
                throw new UsernameNotFoundException("Student not found with email: " + user.getEmail());
            }

            return jwtService.generateToken(student.getEmail(), student.getRole());

        } catch (BadCredentialsException | UsernameNotFoundException ex) {
            throw ex; // Let global exception handler catch and convert to proper HTTP response
        } catch (Exception ex) {
            throw new RuntimeException("Login failed: " + ex.getMessage(), ex);
        }
    }

    public ResponseEntity<StudentDTO> getStudentByEmail(String email) {
        Student student = studentRepo.findByEmail(email);
                if(student==null) {
                     throw new RuntimeException("Student not found");
                }

        StudentDTO dto = new StudentDTO.Builder()
                .email(student.getEmail())
                .username(student.getUsername())
                .build();
        return ResponseEntity.ok(dto);
    }


}
