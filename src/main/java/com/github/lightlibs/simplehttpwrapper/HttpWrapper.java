package com.github.lightlibs.simplehttpwrapper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class HttpWrapper {


    // GET
    public static HttpResponse get(@NotNull String urlStr, @Nullable String[] headers) throws IOException {
        return request(HttpMethod.GET, urlStr, headers, null);
    }

    public static HttpResponse getOrNull(@NotNull String urlStr, @Nullable String[] headers) {
        return requestOrNull(HttpMethod.GET, urlStr, headers, null);
    }

    // HEAD
    public static HttpResponse head(@NotNull String urlStr, @Nullable String[] headers) throws IOException {
        return request(HttpMethod.HEAD, urlStr, headers, null);
    }

    public static HttpResponse headOrNull(@NotNull String urlStr, @Nullable String[] headers) {
        return requestOrNull(HttpMethod.HEAD, urlStr, headers, null);
    }

    // POST
    public static HttpResponse post(@NotNull String urlStr, @Nullable String[] headers, @Nullable String body) throws IOException {
        return request(HttpMethod.POST, urlStr, headers, body);
    }

    public static HttpResponse postOrNull(@NotNull String urlStr, @Nullable String[] headers, @Nullable String body) {
        return requestOrNull(HttpMethod.POST, urlStr, headers, body);
    }

    // PUT
    public static HttpResponse put(@NotNull String urlStr, @Nullable String[] headers, @Nullable String body) throws IOException {
        return request(HttpMethod.PUT, urlStr, headers, body);
    }

    public static HttpResponse putOrNull(@NotNull String urlStr, @Nullable String[] headers, @Nullable String body) {
        return requestOrNull(HttpMethod.PUT, urlStr, headers, body);
    }

    // DELETE
    public static HttpResponse delete(@NotNull String urlStr, @Nullable String[] headers) throws IOException {
        return request(HttpMethod.DELETE, urlStr, headers, null);
    }

    public static HttpResponse deleteOrNull(@NotNull String urlStr, @Nullable String[] headers) {
        return requestOrNull(HttpMethod.DELETE, urlStr, headers, null);
    }

    // CONNECT
    public static HttpResponse connect(@NotNull String urlStr, @Nullable String[] headers) throws IOException {
        return request(HttpMethod.CONNECT, urlStr, headers, null);
    }

    public static HttpResponse connectOrNull(@NotNull String urlStr, @Nullable String[] headers) {
        return requestOrNull(HttpMethod.CONNECT, urlStr, headers, null);
    }

    // OPTIONS
    public static HttpResponse options(@NotNull String urlStr, @Nullable String[] headers) throws IOException {
        return request(HttpMethod.OPTIONS, urlStr, headers, null);
    }

    public static HttpResponse optionsOrNull(@NotNull String urlStr, @Nullable String[] headers) {
        return requestOrNull(HttpMethod.OPTIONS, urlStr, headers, null);
    }

    // TRACE
    public static HttpResponse trace(@NotNull String urlStr, @Nullable String[] headers) throws IOException {
        return request(HttpMethod.TRACE, urlStr, headers, null);
    }

    public static HttpResponse traceOrNull(@NotNull String urlStr, @Nullable String[] headers) {
        return requestOrNull(HttpMethod.TRACE, urlStr, headers, null);
    }

    // PATCH
    public static HttpResponse patch(@NotNull String urlStr, @Nullable String[] headers, @Nullable String body) throws IOException {
        return request(HttpMethod.PATCH, urlStr, headers, body);
    }

    public static HttpResponse patchOrNull(@NotNull String urlStr, @Nullable String[] headers, @Nullable String body) {
        return requestOrNull(HttpMethod.PATCH, urlStr, headers, body);
    }

    // -----
    // UTILS
    // -----

    public static HttpResponse request(@NotNull HttpMethod method, String urlStr, @Nullable String[] headers, @Nullable String postData) throws IOException {
        return convertHeadersAndExecute(method.getMethod(), urlStr, headers, postData);
    }

    public static HttpResponse request(@NotNull HttpMethod method, String urlStr, @Nullable List<HttpHeader> headers, @Nullable String postData) throws IOException {
        return execute(method.getMethod(), urlStr, headers, postData);
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public static HttpResponse requestOrNull(@NotNull HttpMethod method, String urlStr, @Nullable String[] headers, @Nullable String postData) {
        try {
            return convertHeadersAndExecute(method.getMethod(), urlStr, headers, postData);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public static HttpResponse requestOrNull(@NotNull HttpMethod method, String urlStr, @Nullable List<HttpHeader> headers, @Nullable String postData) {
        try {
            return execute(method.getMethod(), urlStr, headers, postData);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Use {@link #request(HttpMethod, String, String[], String)} instead.
     * Only use if you know what you are doing.
     */
    @Deprecated
    public static HttpResponse customRequest(@NotNull ProgrammaticHttpMethod method, String urlStr, @Nullable String[] headers, @Nullable String body) throws IOException {
        return convertHeadersAndExecute(method, urlStr, headers, body);
    }

    @NotNull
    private static HttpResponse convertHeadersAndExecute(@NotNull ProgrammaticHttpMethod method, String urlStr, @Nullable String[] headers, @Nullable String bodyOut) throws IOException {
        List<HttpHeader> convertedHeaders = convertHeaders(headers);
        return execute(method, urlStr, convertedHeaders, bodyOut);
    }

    @NotNull
    private static HttpResponse execute(@NotNull ProgrammaticHttpMethod method, String urlStr, @Nullable List<HttpHeader> headers, @Nullable String bodyOut) throws IOException {
        HttpURLConnection connection = writeHttpRequest(method, urlStr, headers, bodyOut);
        return readHttpResponse(connection);
    }

    private static @NotNull List<HttpHeader> convertHeaders(@Nullable String[] headers) {
        List<HttpHeader> headersList = new ArrayList<>();
        if (headers != null) {
            for (String header : headers) {
                if (header == null) continue;

                String[] parts = header.split(":", 2);
                if (parts.length != 2) continue;

                headersList.add(new HttpHeader(parts[0], parts[1]));
            }
        }
        return headersList;
    }

    private static @NotNull HttpURLConnection writeHttpRequest(@NotNull ProgrammaticHttpMethod method, String urlStr, @Nullable List<HttpHeader> headers, @Nullable String bodyOut) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method.getMethodId());

        if (headers != null) {
            for (HttpHeader header : headers) {
                if (header == null) continue;
                connection.setRequestProperty(header.getKey(), header.getValue());
            }
        }

        if (method.hasBody() && bodyOut != null) {
            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream()) {
                byte[] postDataBytes = bodyOut.getBytes(StandardCharsets.UTF_8);
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
