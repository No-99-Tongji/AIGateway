package com.no99.aigateway;

import com.no99.aigateway.dto.ChatCompletionRequest;
import com.no99.aigateway.models.DeepseekModel;
import com.no99.aigateway.models.DoubaoModel;
import com.no99.aigateway.models.QwenModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class AIGatewayApplicationTests {

    @Autowired
    private DeepseekModel deepseekModel;
    @Autowired
    private DoubaoModel doubaoModel;
    @Autowired
    private QwenModel qwenModel;

    @Value("${model.deepseek.key}")
    private String deepseekKey;

    @Value("${model.deepseek.baseurl}")
    private String deepseekBaseUrl;

    @Value("${model.doubao.key}")
    private String doubaoKey;

    @Value("${model.doubao.baseurl}")
    private String doubaoBaseUrl;

    @Value("${model.qwen.key}")
    private String qwenKey;

    @Value("${model.qwen.baseurl}")
    private String qwenBaseUrl;

    @Test
    void contextLoads() {
        // 创建消息列表
        List<ChatCompletionRequest.Message> messages = List.of(
            new ChatCompletionRequest.Message("user", "你好")
        );

        System.out.println("Testing Deepseek:");
        System.out.println(deepseekModel.requestData(messages, deepseekKey, deepseekBaseUrl));

        System.out.println("\nTesting Doubao:");
        System.out.println(doubaoModel.requestData(messages, doubaoKey, doubaoBaseUrl));

        System.out.println("\nTesting Qwen:");
        System.out.println(qwenModel.requestData(messages, qwenKey, qwenBaseUrl));
    }

}
