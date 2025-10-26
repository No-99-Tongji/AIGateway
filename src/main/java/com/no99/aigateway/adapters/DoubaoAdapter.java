package com.no99.aigateway.adapters;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DoubaoAdapter implements Adapter {

    @Value("${model.doubao.key}")
    private String apiKey;

    @Override
    public ApiInfo adapt() {
        return new ApiInfo("https://ark.cn-beijing.volces.com/api/v3/chat/completions", apiKey);
    }
}
