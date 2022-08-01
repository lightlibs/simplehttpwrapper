package com.github.lightlibs.simplehttpwrapper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SimpleHttpWrapper {

        public static SimpleHttpResponse get(@NotNull String urlStr, @Nullable String[] headers) throws IOException {
            return performRequest(SupportedHttpMethod.GET, urlStr, headers, null);
        }

        public static SimpleHttpResponse post(@NotNull String urlStr, @Nullable String[] headers, @Nullable String postData) throws IOException {
            return performRequest(SupportedHttpMethod.POST, urlStr, headers, postData);
        }

        public static SimpleHttpResponse performRequest(@NotNull SupportedHttpMethod method, String urlStr, @Nullable String[] headers, @Nullable String postData) throws IOException {
            return httpRequest(method.getRawMethodName(), urlStr, headers, postData);
        }

        @Deprecated
        public static SimpleHttpResponse performRequestUnsupported(@NotNull String method, String urlStr, @Nullable String[] headers, @Nullable String postData) throws IOException {
            return httpRequest(method, urlStr, headers, postData);
        }

        @NotNull
        private static SimpleHttpResponse httpRequest(@NotNull String method, String urlStr, @Nullable String[] headers, @Nullable String postData) throws IOException {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);

            if (headers != null) {
                for (String headerEntry : headers) {
                    if (headerEntry == null) continue;

                    String[] parts = headerEntry.split(":");
                    if (parts.length != 2) continue;

                    String key = parts[0];
                    String value = parts[1];

                    connection.setRequestProperty(key, value);
                }
            }

            if (method.equals("POST") && postData != null) {
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] postDataBytes = postData.getBytes(StandardCharsets.UTF_8);
                    os.write(postDataBytes, 0, postDataBytes.length);
                }
            }

            int responseCode = connection.getResponseCode();

            InputStream inputStream;
            if (responseCode >= 200 && responseCode < 300) inputStream = connection.getInputStream();
            else inputStream = connection.getErrorStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder strb = new StringBuilder();
            while (true) {
                String line = reader.readLine();
                if (line == null) break;
                strb.append(line);
            }
            reader.close();
            connection.disconnect();

            return new SimpleHttpResponse(responseCode, strb.toString());
        }

}
