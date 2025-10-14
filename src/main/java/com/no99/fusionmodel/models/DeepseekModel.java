package com.no99.fusionmodel.models;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.Map;
import java.util.List;

@Component
public class DeepseekModel implements Model{
    String name = "Deepseek";

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String requestData(String query, String apiKey, String baseUrl) {
        try {
            // Create headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            // Create request body
            Map<String, Object> requestBody = Map.of(
                "model", "deepseek-chat",
                "messages", List.of(
                    Map.of(
                        "role", "user",
                        "content", query
                    )
                ),
                "stream", false
            );

            // Create HTTP entity
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            // Make API call using exchange method for better type safety
            ResponseEntity<String> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.POST,
                request,
                String.class
            );

            // Parse response manually to avoid type safety issues
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                String responseBody = response.getBody();
                // For a simple implementation, you could parse the JSON response here
                // This is a basic approach - for production code, consider using Jackson ObjectMapper
                if (responseBody.contains("\"content\":")) {
                    int contentStart = responseBody.indexOf("\"content\":\"") + 11;
                    int contentEnd = responseBody.indexOf("\"", contentStart);
                    if (contentStart > 10 && contentEnd > contentStart) {
                        return responseBody.substring(contentStart, contentEnd);
                    }
                }
            }

            return "Error: Invalid response from Deepseek API";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
