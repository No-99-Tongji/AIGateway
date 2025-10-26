package com.no99.aigateway.controllers;

import com.no99.aigateway.dto.ChatCompletionRequest;
import com.no99.aigateway.dto.ChatCompletionResponse;
import com.no99.aigateway.adapters.Adapter;
import com.no99.aigateway.adapters.ApiInfo;
import com.no99.aigateway.models.Model;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;
import java.util.UUID;

@Tag(name = "Chat Completions", description = "OpenAI-compatible chat completion API supporting multiple AI models")
@RestController
@RequestMapping("chat/completions")
public class ModelController {

    @Autowired
    private ApplicationContext applicationContext;

    @Operation(
        summary = "Create chat completion",
        description = "Creates a completion for the chat message. Compatible with OpenAI API format. Supports multiple AI models: deepseek, qwen, doubao."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successful response",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ChatCompletionResponse.class))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ChatCompletionResponse.class))
        )
    })
    @PostMapping
    public ResponseEntity<ChatCompletionResponse> createChatCompletion(
            @Parameter(description = "Chat completion request", required = true)
            @RequestBody ChatCompletionRequest request) {

        try {
            // 获取对应的Adapter来获取API信息
            String adapterBeanName = request.getModel().toLowerCase() + "Adapter";
            Adapter adapter = applicationContext.getBean(adapterBeanName, Adapter.class);
            ApiInfo apiInfo = adapter.adapt();

            // 获取对应的Model来处理请求
            String modelBeanName = request.getModel().toLowerCase() + "Model";
            Model model = applicationContext.getBean(modelBeanName, Model.class);

            // 直接传递完整的messages给Model处理
            String content = model.requestData(request.getMessages(), apiInfo.getApiKey(), apiInfo.getBaseUrl());

            // 构建OpenAI格式的响应
            ChatCompletionResponse chatResponse = new ChatCompletionResponse(
                "chatcmpl-" + UUID.randomUUID().toString().substring(0, 8),
                request.getModel(),
                content
            );

            return ResponseEntity.ok(chatResponse);

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
     * 获取支持的模型列表 (可选的额外接口)
     */
    @Operation(
        summary = "List available models",
        description = "Lists the currently available models, and provides basic information about each one such as the owner and availability."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successful response",
            content = @Content(mediaType = "application/json")
        )
    })
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
