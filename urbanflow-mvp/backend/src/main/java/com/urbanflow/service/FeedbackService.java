package com.urbanflow.service;

import com.urbanflow.dto.FeedbackRequestDto;
import com.urbanflow.dto.FeedbackResponseDto;
import com.urbanflow.entity.AnalysisScenario;
import com.urbanflow.entity.DecisionFeedback;
import com.urbanflow.repository.AnalysisScenarioRepository;
import com.urbanflow.repository.DecisionFeedbackRepository;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    private final AnalysisScenarioRepository analysisScenarioRepository;
    private final DecisionFeedbackRepository decisionFeedbackRepository;

    public FeedbackService(
            AnalysisScenarioRepository analysisScenarioRepository,
            DecisionFeedbackRepository decisionFeedbackRepository
    ) {
        this.analysisScenarioRepository = analysisScenarioRepository;
        this.decisionFeedbackRepository = decisionFeedbackRepository;
    }

    public FeedbackResponseDto saveFeedback(FeedbackRequestDto request) {
        AnalysisScenario scenario = analysisScenarioRepository.findById(request.getScenarioId())
                .orElseThrow(() -> new RuntimeException("Scenario not found with id: " + request.getScenarioId()));

        DecisionFeedback feedback = new DecisionFeedback();
        feedback.setScenario(scenario);
        feedback.setUserDecision(request.getUserDecision());
        feedback.setConfidence(request.getConfidence());
        feedback.setReason(request.getReason());
        feedback.setSelectedAlternative(request.getSelectedAlternative());

        feedback.setRoadDensity(scenario.getRoadDensity());
        feedback.setBuildingDensity(scenario.getBuildingDensity());
        feedback.setAccessibilityScore(scenario.getAccessibilityScore());
        feedback.setFloodRisk(scenario.getFloodRisk());
        feedback.setProjectType(scenario.getProjectType());
        feedback.setDescriptionSignals(scenario.getDescriptionSignals());
        feedback.setFinalScore(scenario.getFinalScore());

        DecisionFeedback saved = decisionFeedbackRepository.save(feedback);

        FeedbackResponseDto response = new FeedbackResponseDto();
        response.setId(saved.getId());
        response.setScenarioId(saved.getScenario().getId());
        response.setUserDecision(saved.getUserDecision().name());
        response.setConfidence(saved.getConfidence().name());
        response.setReason(saved.getReason());
        response.setSelectedAlternative(saved.getSelectedAlternative());
        response.setCreatedAt(saved.getCreatedAt());

        return response;
    }
}
