package com.no99.aigateway.models;

import com.no99.aigateway.dto.ChatCompletionRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DoubaoModel implements Model{
    String name = "Doubao";

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String requestData(List<ChatCompletionRequest.Message> messages, String apiKey, String baseUrl) {
        try {
            // Create headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            // Convert ChatCompletionRequest.Message to API format
            List<Map<String, String>> apiMessages = messages.stream()
                .map(msg -> Map.of(
                    "role", msg.getRole(),
                    "content", msg.getContent()
                ))
                .collect(Collectors.toList());

            // Create request body for Doubao API
            Map<String, Object> requestBody = Map.of(
                "model", "doubao-seed-1-6-250615",
                "messages", apiMessages,
                "stream", false
            );

            // Create HTTP entity
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            // Make API call using exchange method
            ResponseEntity<String> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.POST,
                request,
                String.class
            );

            // Parse response
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                String responseBody = response.getBody();
                if (responseBody.contains("\"content\":")) {
                    int contentStart = responseBody.indexOf("\"content\":\"") + 11;
                    int contentEnd = responseBody.indexOf("\"", contentStart);
                    if (contentStart > 10 && contentEnd > contentStart) {
                        return responseBody.substring(contentStart, contentEnd);
                    }
                }
            }

            return "Error: Invalid response from Doubao API";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
