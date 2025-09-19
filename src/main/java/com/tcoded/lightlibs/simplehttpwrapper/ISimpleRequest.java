package com.tcoded.lightlibs.simplehttpwrapper;

import java.util.List;

public interface ISimpleRequest {
    String getUrl();

    ProgrammaticHttpMethod getMethod();

    List<HttpHeader> getHeaders();

    long getConnectTimeoutMillis();

    long getReadTimeoutMillis();

    String getBody();
}
