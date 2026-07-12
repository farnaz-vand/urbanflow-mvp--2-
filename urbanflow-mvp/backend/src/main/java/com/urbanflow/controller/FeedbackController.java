package com.urbanflow.controller;

import com.urbanflow.dto.FeedbackRequestDto;
import com.urbanflow.entity.DecisionFeedback;
import com.urbanflow.repository.DecisionFeedbackRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final DecisionFeedbackRepository feedbackRepository;

    public FeedbackController(DecisionFeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @PostMapping
    public ResponseEntity<?> submitFeedback(@RequestBody FeedbackRequestDto request) {
        System.out.println("=== FEEDBACK CONTROLLER CALLED ===");
        System.out.println("Received: " + request);
        
        try {
            DecisionFeedback feedback = new DecisionFeedback();
            feedback.setScenarioId(request.getScenarioId());
            feedback.setUserDecision(request.getUserDecision());
            feedback.setConfidence(request.getConfidence());
            feedback.setReason(request.getReason() != null ? request.getReason() : "");
            feedback.setSelectedAlternative(request.getSelectedAlternative() != null ? request.getSelectedAlternative() : "");
            // feedback.setCreatedAt(LocalDateTime.now());

            System.out.println("Saving feedback: " + feedback);
            
            DecisionFeedback savedFeedback = feedbackRepository.save(feedback);
            
            System.out.println("Saved with ID: " + savedFeedback.getId());

            return ResponseEntity.ok(Map.of(
                    "message", "Feedback saved successfully",
                    "id", savedFeedback.getId(),
                    "scenarioId", savedFeedback.getScenarioId()
            ));
            
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Failed to save: " + e.getMessage()
            ));
        }
    }
}