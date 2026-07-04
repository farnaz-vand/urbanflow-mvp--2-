package com.urbanflow.dto;

import java.util.Map;

public class AnalyticsResponse {

    private long totalAnalyses;
    private long totalFeedback;
    private double agreementRate;
    private double disagreementRate;
    private double averageScore;
    private Map<String, Long> projectTypeDistribution;

    public AnalyticsResponse(
            long totalAnalyses,
            long totalFeedback,
            double agreementRate,
            double disagreementRate,
            double averageScore,
            Map<String, Long> projectTypeDistribution
    ) {
        this.totalAnalyses = totalAnalyses;
        this.totalFeedback = totalFeedback;
        this.agreementRate = agreementRate;
        this.disagreementRate = disagreementRate;
        this.averageScore = averageScore;
        this.projectTypeDistribution = projectTypeDistribution;
    }

    public long getTotalAnalyses() {
        return totalAnalyses;
    }

    public long getTotalFeedback() {
        return totalFeedback;
    }

    public double getAgreementRate() {
        return agreementRate;
    }

    public double getDisagreementRate() {
        return disagreementRate;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public Map<String, Long> getProjectTypeDistribution() {
        return projectTypeDistribution;
    }
}
