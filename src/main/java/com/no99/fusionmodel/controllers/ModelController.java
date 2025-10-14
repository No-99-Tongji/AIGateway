package com.no99.fusionmodel.controllers;

import com.no99.fusionmodel.dto.ChatCompletionRequest;
import com.no99.fusionmodel.dto.ChatCompletionResponse;
import com.no99.fusionmodel.adapters.Adapter;
import com.no99.fusionmodel.adapters.ApiInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/chat/completions")
public class ModelController {

    @Autowired
    private ApplicationContext applicationContext;

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping
    public ResponseEntity<ChatCompletionResponse> createChatCompletion(
            @RequestBody ChatCompletionRequest request) {

        try {
            // 获取对应模型的适配器
            String adapterBeanName = request.getModel().toLowerCase() + "Adapter";
            Adapter adapter = applicationContext.getBean(adapterBeanName, Adapter.class);
            ApiInfo apiInfo = adapter.adapt();

            // 构建请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiInfo.getApiKey());

            // 构建请求体 - 转换为目标API格式
            Map<String, Object> requestBody = Map.of(
                "model", getActualModelName(request.getModel()),
                "messages", request.getMessages(),
                "stream", false,
                "temperature", request.getTemperature() != null ? request.getTemperature() : 0.7,
                "max_tokens", request.getMaxTokens() != null ? request.getMaxTokens() : 1000
            );

            HttpEntity<Map<String, Object>> httpRequest = new HttpEntity<>(requestBody, headers);

            // 调用实际的AI服务
            ResponseEntity<Map> response = restTemplate.postForEntity(
                apiInfo.getBaseUrl(),
                httpRequest,
                Map.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                // 解析响应并转换为OpenAI格式
                Map<String, Object> responseBody = response.getBody();
                String content = extractContent(responseBody);

                // 构建OpenAI格式的响应
                ChatCompletionResponse chatResponse = new ChatCompletionResponse(
                    "chatcmpl-" + UUID.randomUUID().toString().substring(0, 8),
                    request.getModel(),
                    content
                );

                return ResponseEntity.ok(chatResponse);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ChatCompletionResponse("error", request.getModel(), "Failed to get response from AI service"));
            }

        } catch (Exception e) {
            // 返回错误响应
            ChatCompletionResponse errorResponse = new ChatCompletionResponse(
                "error",
                request.getModel(),
                "Error: " + e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * 根据请求的模型名称获取实际的模型名称
     */
    private String getActualModelName(String requestModel) {
        switch (requestModel.toLowerCase()) {
            case "deepseek":
                return "deepseek-chat";
            case "qwen":
                return "qwen-turbo";
            case "doubao":
                return "doubao-lite-4k";
            default:
                return requestModel;
        }
    }

    /**
     * 从AI服务响应中提取内容
     */
    @SuppressWarnings("unchecked")
    private String extractContent(Map<String, Object> responseBody) {
        try {
            List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
            if (choices != null && !choices.isEmpty()) {
                Map<String, Object> firstChoice = choices.get(0);
                Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
                if (message != null) {
                    return (String) message.get("content");
                }
            }
        } catch (Exception e) {
            // 如果解析失败，尝试简单的字符串解析
            String responseStr = responseBody.toString();
            if (responseStr.contains("\"content\":\"")) {
                int start = responseStr.indexOf("\"content\":\"") + 11;
                int end = responseStr.indexOf("\"", start);
                if (end > start) {
                    return responseStr.substring(start, end);
                }
            }
        }
        return "Unable to extract response content";
    }

    /**
     * 获取支持的模型列表 (可选的额外接口)
     */
    @GetMapping("/models")
    public ResponseEntity<Map<String, Object>> getModels() {
        List<Map<String, Object>> models = List.of(
            Map.of("id", "deepseek", "object", "model", "owned_by", "deepseek"),
            Map.of("id", "qwen", "object", "model", "owned_by", "qwen"),
            Map.of("id", "doubao", "object", "model", "owned_by", "doubao")
        );

        return ResponseEntity.ok(Map.of(
            "object", "list",
            "data", models
        ));
    }
}
