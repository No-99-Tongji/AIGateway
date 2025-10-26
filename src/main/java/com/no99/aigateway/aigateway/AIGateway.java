package com.no99.aigateway.aigateway;

import com.no99.aigateway.adapters.ApiInfo;

public interface AIGateway {
    ApiInfo getAccess(String modelName);
}
