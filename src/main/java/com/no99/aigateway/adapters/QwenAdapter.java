package com.no99.aigateway.adapters;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QwenAdapter implements Adapter {

    @Value("${model.qwen.key}")
    private String apiKey;

    @Override
    public ApiInfo adapt() {
        return new ApiInfo("https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions", apiKey);
    }
}
