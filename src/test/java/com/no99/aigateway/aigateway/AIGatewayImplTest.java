package com.no99.aigateway.aigateway;

import com.no99.aigateway.adapters.ApiInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AIGatewayImplTest {

    @Autowired
    private AIGateway aiGateway;

    @Test
    public void testGetAccess() {
        // 测试获取不同模型的适配器信息
        ApiInfo qwenInfo = aiGateway.getAccess("qwen");
        System.out.println("Qwen - BaseURL: " + qwenInfo.getBaseUrl() + ", Key: " + qwenInfo.getApiKey());

        ApiInfo deepseekInfo = aiGateway.getAccess("deepseek");
        System.out.println("Deepseek - BaseURL: " + deepseekInfo.getBaseUrl() + ", Key: " + deepseekInfo.getApiKey());

        ApiInfo doubaoInfo = aiGateway.getAccess("doubao");
        System.out.println("Doubao - BaseURL: " + doubaoInfo.getBaseUrl() + ", Key: " + doubaoInfo.getApiKey());

        // 测试不存在的模型
        try {
            ApiInfo unknownInfo = aiGateway.getAccess("unknown");
            System.out.println("Unknown: " + unknownInfo);
        } catch (Exception e) {
            System.out.println("Unknown model error (expected): " + e.getMessage());
        }
    }
}
