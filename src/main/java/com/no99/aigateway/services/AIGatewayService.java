package com.no99.aigateway.services;

import com.no99.aigateway.adapters.ApiInfo;
import com.no99.aigateway.aigateway.AIGatewayImpl;
import com.no99.aigateway.dto.ChatCompletionRequest;
import com.no99.aigateway.models.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AIGatewayService {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    AIGatewayImpl aigateway;

    public String getModelResponse(String modelName, String prompt) {
        // 模拟不同模型的响应
        ApiInfo apiInfo = aigateway.getAccess(modelName);
        String baseUrl = apiInfo.getBaseUrl();
        String apiKey = apiInfo.getApiKey();

        String modelBeanName = modelName.toLowerCase() + "Adapter";

        // 从Spring容器中获取对应的adapter组件实例
        Model model = applicationContext.getBean(modelBeanName, Model.class);

        // 将字符串prompt转换为消息列表
        List<ChatCompletionRequest.Message> messages = new ArrayList<>();
        messages.add(new ChatCompletionRequest.Message("user", prompt));

        return model.requestData(messages, apiKey, baseUrl);
    }
}
