package com.tcoded.lightlibs.simplehttpwrapper;

import java.util.List;

public class HttpResponse {

    private final int statusCode;
    private final List<HttpHeader> headers;
    private final String body;

    public HttpResponse(int statusCode, List<HttpHeader> headers, String body) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public List<HttpHeader> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

}