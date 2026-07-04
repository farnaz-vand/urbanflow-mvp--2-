package com.urbanflow.controller;

import com.urbanflow.dto.FeedbackRequestDto;
import com.urbanflow.dto.FeedbackResponseDto;
import com.urbanflow.service.FeedbackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin(origins = "*")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping
    public ResponseEntity<FeedbackResponseDto> submitFeedback(@RequestBody FeedbackRequestDto request) {
        FeedbackResponseDto saved = feedbackService.saveFeedback(request);
        return ResponseEntity.ok(saved);
    }
}
