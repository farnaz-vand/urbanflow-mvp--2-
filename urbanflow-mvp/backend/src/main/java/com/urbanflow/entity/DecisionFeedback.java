package com.urbanflow.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "decision_feedback")
public class DecisionFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "scenario_id", nullable = false)
    private AnalysisScenario scenario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserDecision userDecision;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FeedbackConfidence confidence;

    @Column(columnDefinition = "TEXT")
    private String reason;

    private String selectedAlternative;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private Double roadDensity;

    private Double buildingDensity;

    private Double accessibilityScore;

    private Double floodRisk;

    private String projectType;

    @Column(columnDefinition = "TEXT")
    private String descriptionSignals;

    private Double finalScore;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public DecisionFeedback() {
    }

    public Long getId() {
        return id;
    }

    public AnalysisScenario getScenario() {
        return scenario;
    }

    public void setScenario(AnalysisScenario scenario) {
        this.scenario = scenario;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Double getRoadDensity() {
        return roadDensity;
    }

    public void setRoadDensity(Double roadDensity) {
        this.roadDensity = roadDensity;
    }

    public Double getBuildingDensity() {
        return buildingDensity;
    }

    public void setBuildingDensity(Double buildingDensity) {
        this.buildingDensity = buildingDensity;
    }

    public Double getAccessibilityScore() {
        return accessibilityScore;
    }

    public void setAccessibilityScore(Double accessibilityScore) {
        this.accessibilityScore = accessibilityScore;
    }

    public Double getFloodRisk() {
        return floodRisk;
    }

    public void setFloodRisk(Double floodRisk) {
        this.floodRisk = floodRisk;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getDescriptionSignals() {
        return descriptionSignals;
    }

    public void setDescriptionSignals(String descriptionSignals) {
        this.descriptionSignals = descriptionSignals;
    }

    public Double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(Double finalScore) {
        this.finalScore = finalScore;
    }
}
