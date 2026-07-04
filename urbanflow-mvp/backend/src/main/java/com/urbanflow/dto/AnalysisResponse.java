package com.urbanflow.dto;

import java.time.LocalDateTime;
import java.util.List;

public class AnalysisResponse {
    private Long id;
    private Integer score;
    private String risk;
    private List<String> insights;
    private String recommendation;
    private Double lat;
    private Double lng;
    private String projectType;
    private String description;
    private LocalDateTime createdAt;
    private AiExplanation aiExplanation;
    public AnalysisResponse() {}

    public AiExplanation getAiExplanation() {
        return aiExplanation;
    }

    public void setAiExplanation(AiExplanation aiExplanation) {
        this.aiExplanation = aiExplanation;
    }

    public AnalysisResponse(Long id, Integer score, String risk, List<String> insights, String recommendation,
                            Double lat, Double lng, String projectType, String description, LocalDateTime createdAt) {
        this.id = id;
        this.score = score;
        this.risk = risk;
        this.insights = insights;
        this.recommendation = recommendation;
        this.lat = lat;
        this.lng = lng;
        this.projectType = projectType;
        this.description = description;
        this.createdAt = createdAt;
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

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
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
}
