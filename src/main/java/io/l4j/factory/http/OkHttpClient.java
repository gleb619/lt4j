package io.l4j.factory.http;

import io.l4j.core.HttpClient;
import lombok.Data;
import lombok.SneakyThrows;
import okhttp3.*;

@Data
public class OkHttpClient implements HttpClient {

    private okhttp3.OkHttpClient okHttpClient = new okhttp3.OkHttpClient();

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

}
