package com.databull.api.utils.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.*;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;

public class HttpRestClient<T, U> {

    private final URL url;
    private final String subApiType;
    private OkHttpClient okHttpclient;
    private Headers headers;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public HttpRestClient(String headers, URL url, String subApiType) {
        this.url = url;
        this.subApiType = subApiType;
        this.okHttpclient = new OkHttpClient();
    }

    public U get() throws IOException {
        U finalResponse;
        this.headers = buildHeaders(headers);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .headers(this.headers)
                .build();
        try  {
            Response response = okHttpclient.newCall(request).execute();
            finalResponse = (U) response.body();
        } catch (Exception e) {
            throw new IOException("Error while executing the post request.");
        }
        return finalResponse;

    }

    public U post(T requestBody, Object headers, String bodyType) throws IOException {
        U finalResponse;
        RequestBody requestBody1 = RequestBody.create(JSON, requestBody.toString());
        this.headers = buildHeaders(headers);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody1)
                .headers(this.headers)
                .build();
        try  {
            Response response = okHttpclient.newCall(request).execute();
            finalResponse = (U) response.body();
        } catch (Exception e) {
            throw new IOException("Error while executing the post request.");
        }
        return finalResponse;
    }

    public U put(T requestBody, Object headers, String bodyType) throws IOException {
        U finalResponse;
        RequestBody requestBody1 = RequestBody.create(JSON, requestBody.toString());
        this.headers = buildHeaders(headers);
        Request request = new Request.Builder()
                .url(url)
                .put(requestBody1)
                .headers(this.headers)
                .build();
        try  {
            Response response = okHttpclient.newCall(request).execute();
            finalResponse = (U) response.body();
        } catch (Exception e) {
            throw new IOException("Error while executing the post request.");
        }
        return finalResponse;

    }

    public static <V> Headers buildHeaders(V headers) throws IOException {
        if(ObjectUtils.isEmpty(headers) || Objects.isNull(headers))
            return null;
        Class<?> cls = headers.getClass();
        if(cls.equals(String.class)) {
            ObjectMapper objectMapper = new ObjectMapper();
            Map headersMap = objectMapper.readValue((String) headers, Map.class);
            return Headers.of(headersMap);
        } else if(cls.equals(Map.class)) {
            return Headers.of((Map) headers);
        }
        return null;
    }


}
