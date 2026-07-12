package com.urbanflow.service;

import com.urbanflow.entity.AnalysisScenario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class AiAnalysisService {

    @Value("${ai.api-key}")
    private String apiKey;

    @Value("${ai.model-url:https://api.gapgpt.app/v1/chat/completions}")
    private String modelUrl;

    @Value("${ai.model-name:gpt-4o}")
    private String modelName;

    @Value("${urbanflow.ai.enabled:true}")
    private boolean aiEnabled;

    private final RestClient restClient;

    public AiAnalysisService() {
        this.restClient = RestClient.create();
    }

    public String generateExpertReport(AnalysisScenario scenario) {
        String cleanKey = (apiKey != null) ? apiKey.trim() : "";
        String cleanUrl = (modelUrl != null) ? modelUrl.trim() : "";

        if (!aiEnabled || cleanKey.isEmpty() || !cleanKey.startsWith("sk-")) {
            return "Smart analysis is currently disabled (API key not configured).";
        }

        // Advanced, critical, and professional English prompt
        String prompt = String.format(
            "Act as a senior urban planning and environmental engineering consultant. Write a critical and precise feasibility assessment report for this scenario.\n" +
            "⚠️ CRITICAL INSTRUCTION: Do not just dryly repeat the numbers. Analyze their real-world impact and implications. (For example: 'An accessibility score of 31 for an industrial project represents a critical logistical bottleneck', or 'A flood risk of 57 requires immediate structural mitigation').\n\n" +
            "Raw Scenario Data:\n" +
            "- Project Type: %s\n" +
            "- Overall Score: %s out of 100\n" +
            "- Overall Risk Level: %s\n" +
            "- Flood Risk: %s\n" +
            "- Accessibility Score: %s\n" +
            "- Road Density: %s\n" +
            "- Building Density: %s\n\n" +
            "The response must strictly follow this two-part structure:\n" +
            "1. 🔴 Critical Situation Analysis: (One concise paragraph highlighting the main weaknesses, threats, and spatial conflicts of this specific location for this specific project type).\n" +
            "2. 🟢 Executive Recommendations: (Two or three highly specific, actionable engineering or managerial actions, such as constructing detention basins, rezoning, or developing heavy transport corridors).\n\n" +
            "Tone: Highly professional, academic, authoritative, and suitable for presentation to a city council or environmental agency. Write strictly in English.",
            scenario.getProjectType(),
            scenario.getScore(),
            scenario.getRisk(),
            scenario.getFloodRisk(),
            scenario.getAccessibilityScore(),
            scenario.getRoadDensity(),
            scenario.getBuildingDensity()
        );

        try {
            Map<String, Object> requestBody = Map.of(
                "model", modelName.trim(),
                "messages", List.of(
                    Map.of("role", "system", "content", "You are an AI assistant, urban data analyst, and senior consulting engineer who writes precise, critical, and professional feasibility reports in English."),
                    Map.of("role", "user", "content", prompt)
                ),
                "temperature", 0.8 // Slightly higher temperature for deeper, more nuanced analysis
            );

            Map<String, Object> response = restClient.post()
                .uri(cleanUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + cleanKey)
                .body(requestBody)
                .retrieve()
                .body(Map.class);

            if (response != null && response.containsKey("choices")) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
                if (!choices.isEmpty()) {
                    Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                    return (String) message.get("content");
                }
            }
            return "Smart analysis is unavailable (Invalid response from server).";

        } catch (Exception e) {
            System.err.println("=== AI API ERROR ===");
            System.err.println("Error: " + e.getMessage());
            return "Error retrieving smart analysis. Please check API settings or internet connection.";
        }
    }
}