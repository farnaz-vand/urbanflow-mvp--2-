package com.urbanflow.service;

import com.urbanflow.dto.AnalyticsResponseDto;
import com.urbanflow.entity.AnalysisScenario;
import com.urbanflow.entity.DecisionFeedback;
import com.urbanflow.entity.UserDecision;
import com.urbanflow.repository.AnalysisScenarioRepository;
import com.urbanflow.repository.DecisionFeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    private final AnalysisScenarioRepository scenarioRepository;
    private final DecisionFeedbackRepository feedbackRepository;

    public AnalyticsService(
            AnalysisScenarioRepository scenarioRepository,
            DecisionFeedbackRepository feedbackRepository
    ) {
        this.scenarioRepository = scenarioRepository;
        this.feedbackRepository = feedbackRepository;
    }

    public AnalyticsResponseDto getAnalytics() {
        List<AnalysisScenario> scenarios = scenarioRepository.findAll();
        List<DecisionFeedback> feedbacks = feedbackRepository.findAll();

        long totalAnalyses = scenarios.size();
        long totalFeedback = feedbacks.size();

        double agreementRate = 0.0;
        double disagreementRate = 0.0;

        if (totalFeedback > 0) {
            // استفاده از countByUserDecision که در ریپازیتوری تعریف کردیم
            long agreeCount = feedbackRepository.countByUserDecision(UserDecision.AGREE);
            long disagreeCount = feedbackRepository.countByUserDecision(UserDecision.DISAGREE);

            agreementRate = (double) agreeCount / totalFeedback;
            disagreementRate = (double) disagreeCount / totalFeedback;
        }

        // محاسبه میانگین امتیاز به کمک Stream
        double averageScore = scenarios.stream()
                .filter(s -> s.getFinalScore() != null)
                .mapToDouble(AnalysisScenario::getFinalScore)
                .average()
                .orElse(0.0);

        // محاسبه توزیع نوع پروژه‌ها
        Map<String, Long> projectTypeDistribution = scenarios.stream()
                .filter(s -> s.getProjectType() != null)
                .collect(Collectors.groupingBy(
                        AnalysisScenario::getProjectType,
                        Collectors.counting()
                ));

        AnalyticsResponseDto dto = new AnalyticsResponseDto();
        dto.setTotalAnalyses(totalAnalyses);
        dto.setTotalFeedback(totalFeedback);
        dto.setAgreementRate(agreementRate);
        dto.setDisagreementRate(disagreementRate);
        dto.setAverageScore(averageScore);
        dto.setProjectTypeDistribution(projectTypeDistribution);

        return dto;
    }
}
