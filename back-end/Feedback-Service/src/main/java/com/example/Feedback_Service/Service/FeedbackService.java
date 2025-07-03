package com.example.Feedback_Service.Service;

import com.example.Feedback_Service.DTO.EnrollmentValidationDTO;
import com.example.Feedback_Service.Model.EnrollmentClient;
import com.example.Feedback_Service.Model.Feedback;
import com.example.Feedback_Service.Model.NotificationMessage;
import com.example.Feedback_Service.Repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepo;
    private final EnrollmentClient enrollmentClient;
    @Autowired
    private StreamBridge streamBridge;

    public FeedbackService(FeedbackRepository feedbackRepository, EnrollmentClient enrollmentClient) {
        this.feedbackRepo = feedbackRepository;
        this.enrollmentClient = enrollmentClient;
    }

    public Feedback submitFeedback(Feedback feedback) {
        boolean enrolled = enrollmentClient.isStudentEnrolled(
                new EnrollmentValidationDTO(feedback.getStudentEmail(), feedback.getCourseName()));

        if (!enrolled) {
            throw new IllegalArgumentException("Student is not enrolled in this course.");
        }

        if (feedbackRepo.existsByStudentEmailAndCourseName(feedback.getStudentEmail(), feedback.getCourseName())) {
            throw new IllegalArgumentException("Feedback already submitted for this course by the student.");
        }
        feedbackRepo.save(feedback);
        NotificationMessage message = new NotificationMessage(
                feedback.getStudentEmail(),
                "Feedback Submitted",
                "Thank you for submitting feedback for course: " + feedback.getCourseName()
        );

        streamBridge.send("notificationProducer-out-0", MessageBuilder.withPayload(message).build());
        return feedback;
    }

    public List<Feedback> getFeedbackForCourse(String courseName) {
        return feedbackRepo.findByCourseName(courseName);
    }

    public Optional<Feedback> getFeedbackByStudentAndCourse(String studentEmail, String courseName) {
        return feedbackRepo.findByStudentEmailAndCourseName(studentEmail, courseName);
    }

    public Feedback updateFeedback(Feedback updatedFeedback, String requesterEmail, boolean isAdmin) {
        Optional<Feedback> existingFeedbackOpt = feedbackRepo.findByStudentEmailAndCourseName(
                updatedFeedback.getStudentEmail(), updatedFeedback.getCourseName());

        if (existingFeedbackOpt.isEmpty()) {
            throw new IllegalArgumentException("Feedback not found for the given student and course.");
        }

        if (!isAdmin && !updatedFeedback.getStudentEmail().equals(requesterEmail)) {
            throw new SecurityException("You are not authorized to update this feedback.");
        }

        Feedback existingFeedback = existingFeedbackOpt.get();
        existingFeedback.setFeedbackText(updatedFeedback.getFeedbackText());
        existingFeedback.setRating(updatedFeedback.getRating());

        return feedbackRepo.save(existingFeedback);
    }

    public void deleteFeedback(String studentEmail, String courseName, String requesterEmail, boolean isAdmin) {
        if (!isAdmin && !studentEmail.equals(requesterEmail)) {
            throw new SecurityException("You are not authorized to delete this feedback.");
        }

        Optional<Feedback> feedback = feedbackRepo.findByStudentEmailAndCourseName(studentEmail, courseName);
        if (feedback.isEmpty()) {
            throw new IllegalArgumentException("Feedback not found.");
        }

        feedbackRepo.deleteByStudentEmailAndCourseName(studentEmail, courseName);
    }
}
