package com.no99.aigateway.models;

import com.no99.aigateway.dto.ChatCompletionRequest;
import java.util.List;

public interface Model {
    String getName();
    String requestData(List<ChatCompletionRequest.Message> messages, String apiKey, String baseUrl);
}
