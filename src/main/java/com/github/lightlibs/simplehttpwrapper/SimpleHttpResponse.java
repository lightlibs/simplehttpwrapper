package com.github.lightlibs.simplehttpwrapper;

public class SimpleHttpResponse {

    private final int statusCode;
    private final String data;

    public SimpleHttpResponse (int statusCode, String data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getData() {
        return data;
    }
}