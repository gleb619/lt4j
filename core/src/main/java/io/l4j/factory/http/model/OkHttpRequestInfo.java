package io.l4j.factory.http.model;

import io.l4j.core.RequestInfo;
import lombok.*;
import okhttp3.Request;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class OkHttpRequestInfo implements RequestInfo {

    private Request request;
    private int code;
    private Map<String, List<String>> headers;
    private byte[] bytes;

    public OkHttpRequestInfo withResponse(int code, Map<String, List<String>> headers, byte[] bytes) {
        this.code = code;
        this.headers = headers;
        this.bytes = bytes;

        return this;
    }

}
