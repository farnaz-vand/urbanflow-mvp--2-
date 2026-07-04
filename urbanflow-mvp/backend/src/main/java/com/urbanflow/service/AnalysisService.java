package com.urbanflow.service;

import com.urbanflow.dto.AnalysisRequest;
import com.urbanflow.dto.AnalysisResponse;
import com.urbanflow.entity.AnalysisScenario;
import com.urbanflow.repository.AnalysisScenarioRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class AnalysisService {

    private final AnalysisScenarioRepository scenarioRepository;

    public AnalysisService(AnalysisScenarioRepository scenarioRepository) {
        this.scenarioRepository = scenarioRepository;
    }

    public AnalysisResponse analyzeAndSave(AnalysisRequest request) {
        validateRequest(request);

        Double lat = request.getLat();
        Double lng = request.getLng();
        String projectType = normalizeProjectType(request.getProjectType());
        String description = safeText(request.getDescription());

        double roadDensity = calculateRoadDensity(lat, lng);
        double buildingDensity = calculateBuildingDensity(lat, lng);
        double accessibilityScore = calculateAccessibilityScore(lat, lng, projectType);
        double floodRisk = calculateFloodRisk(lat, lng);
        String descriptionSignals = extractDescriptionSignals(description);

        double projectFitScore = calculateProjectFitScore(projectType);
        double descriptionBoost = calculateDescriptionBoost(description);

        double finalScore = calculateFinalScore(
                roadDensity,
                buildingDensity,
                accessibilityScore,
                floodRisk,
                projectFitScore,
                descriptionBoost
        );

        int score = (int) Math.round(finalScore);
        String risk = calculateRiskLevel(score, floodRisk);
        List<String> insights = buildInsights(
                roadDensity,
                buildingDensity,
                accessibilityScore,
                floodRisk,
                projectType,
                descriptionSignals
        );
        String recommendation = buildRecommendation(score, risk, projectType);

        AnalysisScenario scenario = new AnalysisScenario();

        scenario.setScore(score);
        scenario.setRisk(risk);
        scenario.setInsights(insights);
        scenario.setRecommendation(recommendation);

        scenario.setLat(lat);
        scenario.setLng(lng);
        scenario.setProjectType(projectType);
        scenario.setDescription(description);

        // Data Moat feature snapshot
        scenario.setRoadDensity(roadDensity);
        scenario.setBuildingDensity(buildingDensity);
        scenario.setAccessibilityScore(accessibilityScore);
        scenario.setFloodRisk(floodRisk);
        scenario.setDescriptionSignals(descriptionSignals);
        scenario.setFinalScore(finalScore);

        AnalysisScenario savedScenario = scenarioRepository.save(scenario);

        return toResponse(savedScenario);
    }

    public AnalysisResponse getScenario(Long id) {
        return scenarioRepository.findById(id)
                .map(this::toResponse)
                .orElse(null);
    }

    private AnalysisResponse toResponse(AnalysisScenario scenario) {
        return new AnalysisResponse(
                scenario.getId(),
                scenario.getScore(),
                scenario.getRisk(),
                scenario.getInsights(),
                scenario.getRecommendation(),
                scenario.getLat(),
                scenario.getLng(),
                scenario.getProjectType(),
                scenario.getDescription(),
                scenario.getCreatedAt()
        );
    }

    private void validateRequest(AnalysisRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Analysis request cannot be null");
        }

        if (request.getLat() == null) {
            throw new IllegalArgumentException("Latitude is required");
        }

        if (request.getLng() == null) {
            throw new IllegalArgumentException("Longitude is required");
        }

        if (request.getProjectType() == null || request.getProjectType().trim().isEmpty()) {
            throw new IllegalArgumentException("Project type is required");
        }

        if (request.getLat() < -90 || request.getLat() > 90) {
            throw new IllegalArgumentException("Latitude must be between -90 and 90");
        }

        if (request.getLng() < -180 || request.getLng() > 180) {
            throw new IllegalArgumentException("Longitude must be between -180 and 180");
        }
    }

    private String normalizeProjectType(String projectType) {
        return projectType == null
                ? "unknown"
                : projectType.trim().toLowerCase(Locale.ROOT);
    }

    private String safeText(String text) {
        return text == null ? "" : text.trim();
    }

    private double calculateRoadDensity(Double lat, Double lng) {
        double value =
                52
                        + Math.sin(lat * 0.13) * 18
                        + Math.cos(lng * 0.09) * 14;

        return roundOneDecimal(clamp(value, 10, 95));
    }

    private double calculateBuildingDensity(Double lat, Double lng) {
        double value =
                48
                        + Math.cos(lat * 0.11) * 20
                        + Math.sin(lng * 0.08) * 16;

        return roundOneDecimal(clamp(value, 10, 98));
    }

    private double calculateAccessibilityScore(Double lat, Double lng, String projectType) {
        double base =
                58
                        + Math.sin((lat + lng) * 0.05) * 17
                        + Math.cos(lat * 0.07) * 10;

        if ("commercial".equals(projectType)) {
            base += 6;
        } else if ("residential".equals(projectType)) {
            base += 4;
        } else if ("industrial".equals(projectType)) {
            base -= 3;
        } else if ("green".equals(projectType) || "park".equals(projectType)) {
            base += 2;
        }

        return roundOneDecimal(clamp(base, 5, 100));
    }

    private double calculateFloodRisk(Double lat, Double lng) {
        double value =
                34
                        + Math.abs(Math.sin(lat * 0.17)) * 26
                        + Math.abs(Math.cos(lng * 0.12)) * 18;

        return roundOneDecimal(clamp(value, 5, 95));
    }

    private double calculateProjectFitScore(String projectType) {
        if ("residential".equals(projectType)) {
            return 78;
        }

        if ("commercial".equals(projectType)) {
            return 74;
        }

        if ("mixed-use".equals(projectType) || "mixed_use".equals(projectType)) {
            return 82;
        }

        if ("industrial".equals(projectType)) {
            return 64;
        }

        if ("green".equals(projectType) || "park".equals(projectType)) {
            return 76;
        }

        return 68;
    }

    private double calculateDescriptionBoost(String description) {
        if (description == null || description.isBlank()) {
            return 0;
        }

        String normalized = description.toLowerCase(Locale.ROOT);
        double boost = 0;

        if (containsAny(normalized, "metro", "subway", "transit", "bus", "station")) {
            boost += 4;
        }

        if (containsAny(normalized, "school", "hospital", "clinic", "university")) {
            boost += 3;
        }

        if (containsAny(normalized, "park", "green", "walkable", "pedestrian")) {
            boost += 3;
        }

        if (containsAny(normalized, "flood", "drainage", "slope", "risk")) {
            boost -= 4;
        }

        if (containsAny(normalized, "traffic", "congestion", "pollution", "noise")) {
            boost -= 3;
        }

        return boost;
    }

    private double calculateFinalScore(
            double roadDensity,
            double buildingDensity,
            double accessibilityScore,
            double floodRisk,
            double projectFitScore,
            double descriptionBoost
    ) {
        double score =
                38
                        + accessibilityScore * 0.32
                        + projectFitScore * 0.22
                        + roadDensity * 0.08
                        - buildingDensity * 0.10
                        - floodRisk * 0.18
                        + descriptionBoost;

        return roundOneDecimal(clamp(score, 0, 100));
    }

    private String calculateRiskLevel(int score, double floodRisk) {
        if (floodRisk >= 75 || score < 45) {
            return "HIGH";
        }

        if (floodRisk >= 55 || score < 65) {
            return "MEDIUM";
        }

        return "LOW";
    }

    private List<String> buildInsights(
            double roadDensity,
            double buildingDensity,
            double accessibilityScore,
            double floodRisk,
            String projectType,
            String descriptionSignals
    ) {
        List<String> insights = new ArrayList<>();

        if (accessibilityScore >= 75) {
            insights.add("The site has strong accessibility potential for the selected project type.");
        } else if (accessibilityScore >= 55) {
            insights.add("The site has moderate accessibility and may require mobility improvements.");
        } else {
            insights.add("Accessibility is limited and should be improved before development.");
        }

        if (roadDensity >= 70) {
            insights.add("Road density is high, which can support connectivity but may increase congestion risk.");
        } else if (roadDensity < 40) {
            insights.add("Road density is low, which may limit access and service coverage.");
        } else {
            insights.add("Road density is balanced for general urban development.");
        }

        if (buildingDensity >= 75) {
            insights.add("Building density is high, so redevelopment pressure and capacity constraints should be reviewed.");
        } else if (buildingDensity < 35) {
            insights.add("Building density is low, indicating potential room for controlled development.");
        } else {
            insights.add("Building density is within a moderate urban range.");
        }

        if (floodRisk >= 70) {
            insights.add("Flood-related risk indicators are elevated and require mitigation planning.");
        } else if (floodRisk >= 50) {
            insights.add("Flood risk is moderate and should be checked during detailed feasibility assessment.");
        } else {
            insights.add("Flood risk indicators are relatively low.");
        }

        if (!"none".equals(descriptionSignals)) {
            insights.add("Description signals detected: " + descriptionSignals + ".");
        }

        insights.add("Project type evaluated as: " + projectType + ".");

        return insights;
    }

    private String buildRecommendation(int score, String risk, String projectType) {
        if ("HIGH".equals(risk)) {
            return "Proceed with caution. Run a detailed feasibility and risk mitigation study before approving this "
                    + projectType + " project.";
        }

        if (score >= 75) {
            return "Recommended. The site appears suitable for the proposed "
                    + projectType + " project based on current urban indicators.";
        }

        if (score >= 60) {
            return "Conditionally recommended. The project may be viable if accessibility, density, and environmental constraints are addressed.";
        }

        return "Not recommended primarily due to weak accessibility and low road density, despite moderate development capacity.";
    }

    private String extractDescriptionSignals(String description) {
        if (description == null || description.isBlank()) {
            return "none";
        }

        String normalized = description.toLowerCase(Locale.ROOT);
        List<String> signals = new ArrayList<>();

        if (containsAny(normalized, "metro", "subway", "transit", "bus", "station")) {
            signals.add("transit_access");
        }

        if (containsAny(normalized, "school", "hospital", "clinic", "university")) {
            signals.add("public_services");
        }

        if (containsAny(normalized, "park", "green", "walkable", "pedestrian")) {
            signals.add("livability");
        }

        if (containsAny(normalized, "flood", "drainage", "slope", "risk")) {
            signals.add("environmental_risk");
        }

        if (containsAny(normalized, "traffic", "congestion", "pollution", "noise")) {
            signals.add("urban_pressure");
        }

        if (signals.isEmpty()) {
            return "none";
        }

        return String.join(",", signals);
    }

    private boolean containsAny(String text, String... keywords) {
        for (String keyword : keywords) {
            if (text.contains(keyword)) {
                return true;
            }
        }

        return false;
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private double roundOneDecimal(double value) {
        return Math.round(value * 10.0) / 10.0;
    }
}
