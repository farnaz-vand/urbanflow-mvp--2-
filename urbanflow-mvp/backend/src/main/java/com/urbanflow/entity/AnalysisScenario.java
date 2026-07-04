package com.urbanflow.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "analysis_scenarios")
public class AnalysisScenario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer score;

    private String risk;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "scenario_insights", joinColumns = @JoinColumn(name = "scenario_id"))
    @Column(name = "insight")
    private List<String> insights;

    @Column(columnDefinition = "TEXT")
    private String recommendation;

    private Double roadDensity;

    private Double buildingDensity;

    private Double accessibilityScore;

    private Double floodRisk;

    private String projectType;

    @Column(columnDefinition = "TEXT")
    private String descriptionSignals;

    private Double finalScore;

    private Double lat;

    private Double lng;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(columnDefinition = "TEXT")
    private String aiExplanationText;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "scenario_ai_insights", joinColumns = @JoinColumn(name = "scenario_id"))
    @Column(name = "ai_insight")
    private List<String> aiInsights;

    @Column(columnDefinition = "TEXT")
    private String aiRecommendation;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    public List<String> getInsights() {
        return insights;
    }

    public void setInsights(List<String> insights) {
        this.insights = insights;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
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

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getAiExplanationText() {
        return aiExplanationText;
    }

    public void setAiExplanationText(String aiExplanationText) {
        this.aiExplanationText = aiExplanationText;
    }

    public List<String> getAiInsights() {
        return aiInsights;
    }

    public void setAiInsights(List<String> aiInsights) {
        this.aiInsights = aiInsights;
    }

    public String getAiRecommendation() {
        return aiRecommendation;
    }

    public void setAiRecommendation(String aiRecommendation) {
        this.aiRecommendation = aiRecommendation;
    }
}
