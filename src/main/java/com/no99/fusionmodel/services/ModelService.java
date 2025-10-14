package com.no99.fusionmodel.services;

import com.no99.fusionmodel.adapters.ApiInfo;
import com.no99.fusionmodel.aigateway.AIGatewayImpl;
import com.no99.fusionmodel.models.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class ModelService {
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
        return model.requestData(prompt, apiKey, baseUrl);
    }
}
