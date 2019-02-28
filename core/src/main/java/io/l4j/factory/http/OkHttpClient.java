package io.l4j.factory.http;

import io.l4j.core.HttpClient;
import io.l4j.core.RequestInfo;
import io.l4j.core.specification.HttpClientType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.Response;

@RequiredArgsConstructor
public class OkHttpClient implements HttpClient {

    private final okhttp3.OkHttpClient okHttpClient;

    public OkHttpClient() {
        this.okHttpClient = null;
    }

    @Override
    @SneakyThrows
    public RequestInfo execute(RequestInfo requestInfo) {
        try (Response response = okHttpClient.newCall(requestInfo.getRequest()).execute()) {
            return requestInfo.withResponse(
                    response.code(),
                    response.headers().toMultimap(),
                    response.body().bytes());
        }
    }

    @Override
    public HttpClientType getType() {
        return HttpClientType.OKHTTP;
    }

}
