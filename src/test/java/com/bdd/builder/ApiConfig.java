package com.bdd.builder;

import io.restassured.http.Headers;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ApiConfig {

    private String apiType;
    private String apiUrl;
    private String method;
    private Headers headers;
    private String body;
    private Map<String, Object> pathVariables;
    private Map<String, Object> params;
    private Map<String, Object> formUrlEncoded;
    private Map<String, Object> formData;

    public ApiConfig(){
    }
}
