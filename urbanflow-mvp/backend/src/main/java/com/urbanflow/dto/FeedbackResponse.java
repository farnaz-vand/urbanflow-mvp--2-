package com.urbanflow.dto;

public class FeedbackResponse {

    private boolean success;
    private Long feedbackId;
    private String message;

    public FeedbackResponse(boolean success, Long feedbackId, String message) {
        this.success = success;
        this.feedbackId = feedbackId;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public Long getFeedbackId() {
        return feedbackId;
    }

    public String getMessage() {
        return message;
    }
}
