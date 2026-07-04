package com.urbanflow.controller;

import com.urbanflow.dto.AnalysisRequest;
import com.urbanflow.dto.AnalysisResponse;
import com.urbanflow.service.AnalysisService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AnalysisController {

    private final AnalysisService analysisService;

    public AnalysisController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<AnalysisResponse> analyze(@RequestBody AnalysisRequest request) {
        AnalysisResponse response = analysisService.analyzeAndSave(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/scenario/{id}")
    public ResponseEntity<?> getScenario(@PathVariable Long id) {
        AnalysisResponse response = analysisService.getScenario(id);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Scenario not found");
        }
        return ResponseEntity.ok(response);
    }
}
