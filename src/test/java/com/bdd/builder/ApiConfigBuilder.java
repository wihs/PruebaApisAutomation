package com.bdd.builder;

import io.restassured.http.Headers;
import lombok.Data;

import java.util.Map;

@Data
public class ApiConfigBuilder {
    private String apiType;
    private String apiUrl;
    private String method;
    private Headers headers;
    private String body;
    private Map<String, Object> pathVariables;
    private Map<String, Object> params;
    private Map<String, Object> formUrlEncoded;
    private Map<String, Object> formData;

    public ApiConfigBuilder(){
    }

    public ApiConfigBuilder withApiType(String apiType){
        this.apiType = apiType;
        return this;
    }
    public ApiConfigBuilder withApiUrl(String apiUrl){
        this.apiUrl = apiUrl;
        return this;
    }
    public ApiConfigBuilder withApiMethod(String apiMethod){
        this.method = apiMethod;
        return this;
    }
    public ApiConfigBuilder withHeaders(Headers headers){
        this.headers = headers;
        return this;
    }
    public ApiConfigBuilder withBody(String body){
        this.body = body;
        return this;
    }

    public ApiConfig build(){
        ApiConfig apiConfig = new ApiConfig();

        apiConfig.setApiType(this.apiType);
        apiConfig.setApiUrl(this.apiUrl);
        apiConfig.setMethod(this.method);
        apiConfig.setHeaders(this.headers);
        apiConfig.setPathVariables(this.pathVariables);
        apiConfig.setParams(this.params);
        apiConfig.setFormUrlEncoded(this.formUrlEncoded);
        apiConfig.setFormData(this.formData);
        apiConfig.setBody(this.body);

        return apiConfig;
    }
}
