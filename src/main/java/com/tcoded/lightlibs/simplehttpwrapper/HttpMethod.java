package com.tcoded.lightlibs.simplehttpwrapper;

import org.jetbrains.annotations.NotNull;

public enum HttpMethod {

    // See https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods
    // for details about each method.

    GET("GET", false),
    HEAD("HEAD", false),
    POST("POST", true),
    PUT("PUT", true),
    DELETE("DELETE", false),
    CONNECT("CONNECT", false),
    OPTIONS("OPTIONS", false),
    TRACE("TRACE", false),
    PATCH("PATCH", true);

    private final ProgrammaticHttpMethod method;

    HttpMethod(String methodId, boolean hasBody) {
        this.method = new ProgrammaticHttpMethod(methodId, hasBody);
    }

    public String getMethodId() {
        return method.getMethodId();
    }

    public boolean hasBody() {
        return method.hasBody();
    }

    public @NotNull ProgrammaticHttpMethod getMethod() {
        return method;
    }

}
