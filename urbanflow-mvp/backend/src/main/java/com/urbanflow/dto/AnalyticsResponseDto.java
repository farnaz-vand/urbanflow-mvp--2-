package com.urbanflow.dto;

import java.util.Map;

public class AnalyticsResponseDto {

    private long totalAnalyses;
    private long totalFeedback;
    private double agreementRate;
    private double disagreementRate;
    private double averageScore;
    private Map<String, Long> projectTypeDistribution;

    public long getTotalAnalyses() {
        return totalAnalyses;
    }

    public void setTotalAnalyses(long totalAnalyses) {
        this.totalAnalyses = totalAnalyses;
    }

    public long getTotalFeedback() {
        return totalFeedback;
    }

    public void setTotalFeedback(long totalFeedback) {
        this.totalFeedback = totalFeedback;
    }

    public double getAgreementRate() {
        return agreementRate;
    }

    public void setAgreementRate(double agreementRate) {
        this.agreementRate = agreementRate;
    }

    public double getDisagreementRate() {
        return disagreementRate;
    }

    public void setDisagreementRate(double disagreementRate) {
        this.disagreementRate = disagreementRate;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public Map<String, Long> getProjectTypeDistribution() {
        return projectTypeDistribution;
    }

    public void setProjectTypeDistribution(Map<String, Long> projectTypeDistribution) {
        this.projectTypeDistribution = projectTypeDistribution;
    }
}
