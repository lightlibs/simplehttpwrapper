package com.tcoded.lightlibs.simplehttpwrapper;

import java.util.List;

public class SimpleRequest implements ISimpleRequest {

    private final ISimpleRequest delegate;

    public SimpleRequest(SimpleRequestBuilder parent) {
        this.delegate = parent.copy();
    }

    @Override
    public String getUrl() {
        return delegate.getUrl();
    }

    @Override
    public ProgrammaticHttpMethod getMethod() {
        return delegate.getMethod();
    }

    @Override
    public List<HttpHeader> getHeaders() {
        return delegate.getHeaders();
    }

    @Override
    public long getConnectTimeoutMillis() {
        return delegate.getConnectTimeoutMillis();
    }

    @Override
    public long getReadTimeoutMillis() {
        return delegate.getReadTimeoutMillis();
    }

    @Override
    public String getBody() {
        return delegate.getBody();
    }

    public HttpResponse execute() throws Exception {
        return SimpleHttp.execute(this);
    }

    public HttpResponse executeSilently() {
        return SimpleHttp.executeSilently(this);
    }

}
