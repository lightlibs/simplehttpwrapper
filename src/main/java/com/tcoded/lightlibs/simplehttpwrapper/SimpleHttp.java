package com.tcoded.lightlibs.simplehttpwrapper;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SimpleHttp {

    public static final String USER_AGENT = "SimpleHttpWrapper/1.0";

    public static SimpleRequestBuilder create() {
        return new SimpleRequestBuilder(SimpleRequest::new)
                .addHeader("User-Agent", USER_AGENT);
    }

    public static HttpResponse execute(ISimpleRequest request) throws IOException {
        HttpURLConnection connection = writeHttpRequest(request);
        return readHttpResponse(connection);
    }

    public static HttpResponse executeSilently(SimpleRequest request) {
        try {
            return execute(request);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static HttpURLConnection writeHttpRequest(ISimpleRequest request) throws IOException {
        URL url = new URL(request.getUrl());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        ProgrammaticHttpMethod method = request.getMethod();
        connection.setRequestMethod(method.getMethodId());

        for (HttpHeader header : request.getHeaders()) {
            if (header == null) continue;
            connection.setRequestProperty(header.getKey(), header.getValue());
        }

        String body = request.getBody();
        if (method.hasBody() && body != null) {
            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream()) {
                byte[] postDataBytes = body.getBytes(StandardCharsets.UTF_8);
                os.write(postDataBytes, 0, postDataBytes.length);
            }
        }

        return connection;
    }

    private static @NotNull HttpResponse readHttpResponse(HttpURLConnection connection) throws IOException {
        int responseCode = connection.getResponseCode();

        Map<String, List<String>> headerFields = connection.getHeaderFields();
        List<HttpHeader> headers = new ArrayList<>();
        for (Map.Entry<String, List<String>> headerEntry : headerFields.entrySet()) {
            for (String value : headerEntry.getValue()) {
                headers.add(new HttpHeader(headerEntry.getKey(), value));
            }
        }

        InputStream inputStream;
        if (responseCode >= 200 && responseCode < 300) inputStream = connection.getInputStream();
        else inputStream = connection.getErrorStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder bodyIn = new StringBuilder();
        while (true) {
            String line = reader.readLine();
            if (line == null) break;
            bodyIn.append(line);
        }
        reader.close();
        connection.disconnect();

        return new HttpResponse(responseCode, headers, bodyIn.toString());
    }
}
