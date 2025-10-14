package com.no99.fusionmodel.adapters;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DeepseekAdapter implements Adapter {

    @Value("${model.deepseek.key}")
    private String apiKey;

    @Override
    public ApiInfo adapt() {
        return new ApiInfo("https://api.deepseek.com/v1/chat/completions", apiKey);
    }
}
