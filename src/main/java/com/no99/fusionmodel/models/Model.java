package com.no99.fusionmodel.models;

public interface Model {
    String getName();
    String requestData(String query, String apiKey, String baseUrl);
}
