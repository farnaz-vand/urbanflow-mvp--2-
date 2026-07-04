package com.urbanflow.dto;

import java.util.List;

public class AiExplanation {
    private List<String> insights;
    private String recommendation;
    private String explanation;

    public AiExplanation() {
    }

    public AiExplanation(List<String> insights, String recommendation, String explanation) {
        this.insights = insights;
        this.recommendation = recommendation;
        this.explanation = explanation;
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

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
