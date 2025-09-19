package com.tcoded.lightlibs.simplehttpwrapper;

public class HttpHeader {

    public static HttpHeader of(String key, String value) {
        return new HttpHeader(key, value);
    }

    public static HttpHeader of(String header) {
        String[] parts = header.split(":", 2);
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid header format. Expected 'Key: Value'");
        }
        return new HttpHeader(parts[0].trim(), parts[1].trim());
    }

    public static HttpHeader[] of(String... headers) {
        HttpHeader[] httpHeaders = new HttpHeader[headers.length];
        for (int i = 0; i < headers.length; i++) {
            httpHeaders[i] = of(headers[i]);
        }
        return httpHeaders;
    }

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
