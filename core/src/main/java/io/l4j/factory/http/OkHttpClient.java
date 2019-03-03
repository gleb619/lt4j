package io.l4j.factory.http;

import io.l4j.core.HttpClient;
import io.l4j.core.RequestInfo;
import io.l4j.core.specification.HttpClientType;
import io.l4j.factory.http.model.OkHttpRequestInfo;
import io.l4j.model.Job;
import io.l4j.model.Pipeline;
import io.l4j.model.Project;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;

@RequiredArgsConstructor
public class OkHttpClient implements HttpClient {

    private final okhttp3.OkHttpClient okHttpClient;

    public OkHttpClient() {
        this.okHttpClient = null;
    }

    @Override
    @SneakyThrows
    public RequestInfo prepare(Project project, Pipeline pipeline, Job job, int index) {
        MediaType mediaType;
        if (StringUtils.isNotBlank(job.getMediaType())) {
            mediaType = MediaType.parse(job.getMediaType());
        } else {
            mediaType = MediaType.parse("application/json; charset=utf-8");
        }

        RequestBody requestBody = null;
        if (StringUtils.isNotBlank(job.getBody())) {
            requestBody = RequestBody.create(mediaType, job.getBody());
        }

        return OkHttpRequestInfo.builder()
                .request(new Request.Builder()
                        .url(project.getAddress() + job.getPath())
                        .method(job.getMethod(), requestBody)
                        .headers(Headers.of(job.getHeaders()))
                        .build())
                .build();
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
