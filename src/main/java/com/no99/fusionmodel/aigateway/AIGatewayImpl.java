package com.no99.fusionmodel.aigateway;

import com.no99.fusionmodel.adapters.Adapter;
import com.no99.fusionmodel.adapters.ApiInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class AIGatewayImpl implements AIGateway {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public ApiInfo getAccess(String modelName) {
        try {
            // 根据模型名称构建adapter的bean名称
            String adapterBeanName = modelName.toLowerCase() + "Adapter";

            // 从Spring容器中获取对应的adapter组件实例
            Adapter adapter = applicationContext.getBean(adapterBeanName, Adapter.class);

            // 获取ApiInfo对象，包含baseUrl和apiKey
            ApiInfo apiInfo = adapter.adapt();

            // 返回包含base URL和API key的信息
            return apiInfo;

        } catch (Exception e) {
            // 如果找不到对应的adapter，返回错误信息
            return new ApiInfo(modelName + " - ", e.getMessage());
        }
    }
}
