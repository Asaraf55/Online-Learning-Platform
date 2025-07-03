package com.example.Feedback_Service.Controller;

import com.example.Feedback_Service.Config.JwtUtil;
import com.example.Feedback_Service.Model.Feedback;
import com.example.Feedback_Service.Service.FeedbackService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("feedback")
public class FeedbackController {
    private final FeedbackService feedbackService;
    private final JwtUtil jwtUtil;

    public FeedbackController(FeedbackService feedbackService, JwtUtil jwtUtil) {
        this.feedbackService = feedbackService;
        this.jwtUtil = jwtUtil;
    }



    @PostMapping("/submit")
    public ResponseEntity<Feedback> submitFeedback(@RequestBody Feedback feedback, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        String email = jwtUtil.extractEmail(token);
        feedback.setStudentEmail(email);
        return ResponseEntity.ok(feedbackService.submitFeedback(feedback));
    }

    @GetMapping("/course/{courseName}")
    public ResponseEntity<List<Feedback>> getFeedbackForCourse(@PathVariable String courseName) {
        return ResponseEntity.ok(feedbackService.getFeedbackForCourse(courseName));
    }

    @GetMapping("/student-course")
    public ResponseEntity<Feedback> getFeedbackByStudentAndCourse(@RequestParam String studentEmail,
                                                                  @RequestParam String courseName) {
        Optional<Feedback> feedback = feedbackService.getFeedbackByStudentAndCourse(studentEmail, courseName);
        return feedback.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update")
    public ResponseEntity<Feedback> updateFeedback(@RequestBody Feedback feedback, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        String requesterEmail = jwtUtil.extractEmail(token);
        boolean isAdmin = jwtUtil.isAdmin(token);
        return ResponseEntity.ok(feedbackService.updateFeedback(feedback, requesterEmail, isAdmin));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFeedback(@RequestParam String studentEmail,
                                                 @RequestParam String courseName,
                                                 HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        String requesterEmail = jwtUtil.extractEmail(token);
        boolean isAdmin = jwtUtil.isAdmin(token);
        feedbackService.deleteFeedback(studentEmail, courseName, requesterEmail, isAdmin);
        return ResponseEntity.ok("Feedback deleted successfully.");
    }
}
