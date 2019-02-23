package io.l4j.factory.http;

import io.l4j.core.HttpClient;
import io.l4j.core.specification.HttpClientType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@RequiredArgsConstructor
public class OkHttpClient implements HttpClient {

    private final okhttp3.OkHttpClient okHttpClient;

    public OkHttpClient() {
        this.okHttpClient = null;
    }

    @Override
    @SneakyThrows
    public void post() {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{ \"test\" : \"text\" }");
        Request request = new Request.Builder()
                .url("http://localhost:8080/api")
                .post(body)
                .build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            String test = response.body().string();
        }
    }

    @Override
    public HttpClientType getType() {
        return HttpClientType.OKHTTP;
    }

}
