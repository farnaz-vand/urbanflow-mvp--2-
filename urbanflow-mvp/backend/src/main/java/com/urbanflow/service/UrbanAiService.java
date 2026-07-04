package com.urbanflow.service;

import com.urbanflow.dto.AiAnalysisInput;
import com.urbanflow.dto.AiExplanation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UrbanAiService {

    private final OpenAiClient openAiClient;

    @Value("${urbanflow.ai.enabled:true}")
    private boolean aiEnabled;

    public UrbanAiService(OpenAiClient openAiClient) {
        this.openAiClient = openAiClient;
    }

    public AiExplanation generateExplanation(AiAnalysisInput input) {
        if (!aiEnabled) {
            return buildFallback(input);
        }

        try {
            String prompt = buildPrompt(input);
            AiExplanation result = openAiClient.generateExplanation(prompt);

            if (result == null
                    || result.getInsights() == null
                    || result.getInsights().isEmpty()
                    || result.getRecommendation() == null
                    || result.getRecommendation().isBlank()
                    || result.getExplanation() == null
                    || result.getExplanation().isBlank()) {
                return buildFallback(input);
            }

            return result;
        } catch (Exception ex) {
            return buildFallback(input);
        }
    }

    private String buildPrompt(AiAnalysisInput input) {
        return """
                You are a senior urban planning and real estate investment analyst.

                You will receive:
                - score (0-100)
                - risk level
                - project type
                - location coordinates (lat/lng)
                - optional description

                Your task:
                1. Generate 3-5 concise insights about this project
                2. Provide a clear recommendation
                3. Explain the risk in a professional consulting tone

                Rules:
                - Do NOT change the score or risk
                - Do NOT hallucinate specific real-world infrastructure
                - Be conservative and realistic
                - Keep output structured and concise
                - Return ONLY valid JSON
                - Do not wrap JSON in markdown

                Input:
                Score: %d
                Risk: %s
                Project Type: %s
                Location: %s, %s
                Description: %s

                Output JSON:
                {
                  "insights": [],
                  "recommendation": "",
                  "explanation": ""
                }
                """.formatted(
                input.getScore(),
                safe(input.getRisk()),
                safe(input.getProjectType()),
                input.getLat() != null ? input.getLat().toString() : "N/A",
                input.getLng() != null ? input.getLng().toString() : "N/A",
                safe(input.getDescription())
        );
    }

    private String safe(String value) {
        return value == null || value.isBlank() ? "N/A" : value;
    }

    private AiExplanation buildFallback(AiAnalysisInput input) {
        String risk = safe(input.getRisk());
        Integer score = input.getScore() != null ? input.getScore() : 0;
        String projectType = safe(input.getProjectType());

        return new AiExplanation(
                Arrays.asList(
                        "The project shows a score of " + score + ", which should be interpreted alongside the stated risk level.",
                        "The current assessment suggests a " + risk + " risk profile for this " + projectType + " opportunity.",
                        "Further validation of local planning, access, and market assumptions is advisable before execution."
                ),
                buildFallbackRecommendation(risk),
                "This explanation is generated from the deterministic scoring result. " +
                        "The reported risk level should be used as the primary decision anchor, while location context and project type " +
                        "should be reviewed through local due diligence."
        );
    }

    private String buildFallbackRecommendation(String risk) {
        if ("high".equalsIgnoreCase(risk)) {
            return "Proceed only with caution and additional due diligence before capital commitment.";
        }
        if ("medium".equalsIgnoreCase(risk)) {
            return "Proceed selectively, with validation of planning, access, and market assumptions.";
        }
        return "The project appears relatively favorable, but should still undergo standard feasibility review.";
    }
}
