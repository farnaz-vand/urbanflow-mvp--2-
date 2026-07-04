package com.urbanflow.dto;

public class AiAnalysisInput {
    private Integer score;
    private String risk;
    private String projectType;
    private Double lat;
    private Double lng;
    private String description;

    public AiAnalysisInput() {
    }

    public AiAnalysisInput(Integer score, String risk, String projectType, Double lat, Double lng, String description) {
        this.score = score;
        this.risk = risk;
        this.projectType = projectType;
        this.lat = lat;
        this.lng = lng;
        this.description = description;
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

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
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
}
