package com.tcoded.lightlibs.simplehttpwrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class SimpleRequestBuilder implements ISimpleRequest {

    // Base
    private String url;
    private ProgrammaticHttpMethod method;
    private List<HttpHeader> headers;

    // Meta
    private long connectTimeoutMillis;
    private long readTimeoutMillis;

    // Payload data
    private String body;

    // Builder
    private final Function<SimpleRequestBuilder, SimpleRequest> builder;



    public SimpleRequestBuilder(Function<SimpleRequestBuilder, SimpleRequest> builder) {
        this.builder = builder;
        this.headers = new ArrayList<>();
    }

    public SimpleRequest build() {
        return builder.apply(this);
    }

    public SimpleRequestBuilder copy() {
        return new SimpleRequestBuilder(builder)
                .setUrl(this.url)
                .setMethod(this.method)
                .setHeaders(new ArrayList<>(this.headers))
                .setConnectTimeoutMillis(this.connectTimeoutMillis)
                .setReadTimeoutMillis(this.readTimeoutMillis)
                .setBody(this.body);
    }



    @Override
    public String getUrl() {
        return url;
    }

    public SimpleRequestBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    @Override
    public ProgrammaticHttpMethod getMethod() {
        return method;
    }

    public SimpleRequestBuilder setMethod(ProgrammaticHttpMethod method) {
        this.method = method;
        return this;
    }

    @Override
    public List<HttpHeader> getHeaders() {
        return headers;
    }

    public SimpleRequestBuilder setHeaders(List<HttpHeader> headers) {
        this.headers = headers;
        return this;
    }

    public SimpleRequestBuilder setHeaders(HttpHeader... headers) {
        this.headers = new ArrayList<>(headers.length);
        Collections.addAll(this.headers, headers);
        return this;
    }

    public SimpleRequestBuilder addHeader(String key, String value) {
        ensureHeadersNotNull();
        HttpHeader newHeader = new HttpHeader(key, value);
        this.headers.add(newHeader);
        return this;
    }

    public SimpleRequestBuilder addHeaders(HttpHeader... headers) {
        ensureHeadersNotNull();
        Collections.addAll(this.headers, headers);
        return this;
    }

    public SimpleRequestBuilder replaceHeader(HttpHeader newHeader) {
        ensureHeadersNotNull();

        String targetKey = newHeader.getKey();

        int index = -1;
        for (int i = 0; i < this.headers.size(); ++i) {
            HttpHeader header = this.headers.get(i);
            if (!header.getKey().equals(targetKey)) continue;
            index = i;
            break;
        }

        if (index == -1) this.headers.add(newHeader);
        else this.headers.set(index, newHeader);

        return this;
    }

    public SimpleRequestBuilder replaceHeaders(HttpHeader... headers) {
        for (HttpHeader header : headers) {
            replaceHeader(header);
        }
        return this;
    }

    @Override
    public long getConnectTimeoutMillis() {
        return connectTimeoutMillis;
    }

    public SimpleRequestBuilder setConnectTimeoutMillis(long connectTimeoutMillis) {
        this.connectTimeoutMillis = connectTimeoutMillis;
        return this;
    }

    @Override
    public long getReadTimeoutMillis() {
        return readTimeoutMillis;
    }

    public SimpleRequestBuilder setReadTimeoutMillis(long readTimeoutMillis) {
        this.readTimeoutMillis = readTimeoutMillis;
        return this;
    }

    @Override
    public String getBody() {
        return body;
    }

    public SimpleRequestBuilder setBody(String body) {
        this.body = body;
        return this;
    }


    // UTILS

    private void ensureHeadersNotNull() {
        if (this.headers == null) {
            this.headers = new ArrayList<>();
        }
    }

}
