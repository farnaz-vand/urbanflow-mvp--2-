package com.urbanflow.dto;

import java.time.LocalDateTime;

public class FeedbackResponseDto {

    private Long id;
    private Long scenarioId;
    private String userDecision;
    private String confidence;
    private String reason;
    private String selectedAlternative;
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(Long scenarioId) {
        this.scenarioId = scenarioId;
    }

    public String getUserDecision() {
        return userDecision;
    }

    public void setUserDecision(String userDecision) {
        this.userDecision = userDecision;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSelectedAlternative() {
        return selectedAlternative;
    }

    public void setSelectedAlternative(String selectedAlternative) {
        this.selectedAlternative = selectedAlternative;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
