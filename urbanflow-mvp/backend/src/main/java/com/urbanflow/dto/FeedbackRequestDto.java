package com.urbanflow.dto;

import com.urbanflow.entity.UserDecision;
import com.urbanflow.entity.FeedbackConfidence;

public class FeedbackRequestDto {

    private Long scenarioId;
    private UserDecision userDecision;
    private FeedbackConfidence confidence;
    private String reason;
    private String selectedAlternative;

    public Long getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(Long scenarioId) {
        this.scenarioId = scenarioId;
    }

    public UserDecision getUserDecision() {
        return userDecision;
    }

    public void setUserDecision(UserDecision userDecision) {
        this.userDecision = userDecision;
    }

    public FeedbackConfidence getConfidence() {
        return confidence;
    }

    public void setConfidence(FeedbackConfidence confidence) {
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
}
