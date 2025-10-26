package com.no99.aigateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Chat completion request following OpenAI API format")
public class ChatCompletionRequest {
    @Schema(description = "ID of the model to use", example = "deepseek", required = true,
            allowableValues = {"deepseek", "qwen", "doubao"})
    private String model;

    @Schema(description = "A list of messages comprising the conversation so far", required = true)
    private List<Message> messages;

    @Schema(description = "What sampling temperature to use, between 0 and 2",
            example = "0.7", minimum = "0", maximum = "2")
    private Double temperature;

    @JsonProperty("max_tokens")
    @Schema(description = "The maximum number of tokens to generate in the chat completion",
            example = "1000", minimum = "1")
    private Integer maxTokens;

    @Schema(description = "If set, partial message deltas will be sent", example = "false")
    private Boolean stream;

    // 构造函数
    public ChatCompletionRequest() {}

    public ChatCompletionRequest(String model, List<Message> messages) {
        this.model = model;
        this.messages = messages;
    }

    // Getter和Setter方法
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Integer getMaxTokens() {
        return maxTokens;
    }

    public void setMaxTokens(Integer maxTokens) {
        this.maxTokens = maxTokens;
    }

    public Boolean getStream() {
        return stream;
    }

    public void setStream(Boolean stream) {
        this.stream = stream;
    }

    // Message内部类
    @Schema(description = "A chat completion message")
    public static class Message {
        @Schema(description = "The role of the messages author",
                allowableValues = {"system", "user", "assistant"}, example = "user")
        private String role;

        @Schema(description = "The contents of the message", example = "Hello, how are you?")
        private String content;

        public Message() {}

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
