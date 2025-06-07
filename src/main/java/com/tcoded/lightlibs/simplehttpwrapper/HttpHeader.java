package com.tcoded.lightlibs.simplehttpwrapper;

public class HttpHeader {

    private final String key;
    private final String value;

    public HttpHeader(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

}
