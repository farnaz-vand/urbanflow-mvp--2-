package com.urbanflow.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanflow.dto.AiExplanation;
import com.urbanflow.dto.openai.OpenAiChatRequest;
import com.urbanflow.dto.openai.OpenAiChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class OpenAiClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${openai.api-key:}")
    private String apiKey;

    @Value("${openai.model:gpt-4o-mini}")
    private String model;

    @Value("${openai.chat-url:https://api.openai.com/v1/chat/completions}")
    private String chatUrl;

    public OpenAiClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public AiExplanation generateExplanation(String prompt) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("OpenAI API key is missing");
        }

        OpenAiChatRequest request = new OpenAiChatRequest(
                model,
                Arrays.asList(
                        new OpenAiChatRequest.Message("system",
                                "You are a senior urban planning and real estate investment analyst. " +
                                        "Return only valid JSON with fields: insights, recommendation, explanation."),
                        new OpenAiChatRequest.Message("user", prompt)
                ),
                0.3
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<OpenAiChatRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<OpenAiChatResponse> response = restTemplate.exchange(
                chatUrl,
                HttpMethod.POST,
                entity,
                OpenAiChatResponse.class
        );

        OpenAiChatResponse body = response.getBody();

        if (body == null || body.getChoices() == null || body.getChoices().isEmpty()) {
            throw new IllegalStateException("OpenAI response is empty");
        }

        String content = body.getChoices().get(0).getMessage().getContent();

        try {
            return objectMapper.readValue(content, AiExplanation.class);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to parse AI JSON response", e);
        }
    }
}
