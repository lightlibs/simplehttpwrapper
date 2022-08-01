package com.github.lightlibs.simplehttpwrapper;

public enum SupportedHttpMethod {

    GET("GET"),
    POST("POST");

    private final String rawMethodName;

    SupportedHttpMethod(String rawMethodNameIn) {
        this.rawMethodName = rawMethodNameIn;
    }

    public String getRawMethodName() {
        return rawMethodName;
    }
}
