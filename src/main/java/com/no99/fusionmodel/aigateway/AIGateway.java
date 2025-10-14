package com.no99.fusionmodel.aigateway;

import com.no99.fusionmodel.adapters.ApiInfo;

public interface AIGateway {
    ApiInfo getAccess(String modelName);
}
