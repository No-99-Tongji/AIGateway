package com.no99.fusionmodel.aigateway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AIGatewayImplTest {

    @Autowired
    private AIGateway aiGateway;

    @Test
    public void testGetAddress() {
        // 测试获取不同模型的适配器
        System.out.println("Qwen: " + aiGateway.getAddress("qwen"));
        System.out.println("Deepseek: " + aiGateway.getAddress("deepseek"));
        System.out.println("Doubao: " + aiGateway.getAddress("doubao"));

        // 测试不存在的模型
        System.out.println("Unknown: " + aiGateway.getAddress("unknown"));
    }
}
