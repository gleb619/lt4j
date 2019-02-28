package io.l4j.factory.http;

import io.l4j.core.HttpClient;
import io.l4j.core.RequestInfo;
import io.l4j.core.specification.HttpClientType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

@RequiredArgsConstructor
public class ApacheHttpClient implements HttpClient {

    private final CloseableHttpClient closeableHttpClient;

    public ApacheHttpClient() {
        this.closeableHttpClient = null;
    }

    @SneakyThrows
    public void post() {
        HttpPost httpPost = new HttpPost("http://www.example.com");

        String json = "{\"id\":1,\"name\":\"John\"}";
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        try (CloseableHttpResponse response = closeableHttpClient.execute(httpPost)) {
            HttpEntity httpEntity = response.getEntity();
            byte[] bytes = EntityUtils.toByteArray(httpEntity);
        }

        httpPost.releaseConnection();

//        Request.Post("http://somehost/do-stuff")
//                .useExpectContinue()
//                .version(HttpVersion.HTTP_1_1)
//                .bodyString("Important stuff", ContentType.DEFAULT_TEXT)
//                .execute().returnContent().asBytes();
    }

    @Override
    public HttpClientType getType() {
        return HttpClientType.APACHE;
    }

    @Override
    public RequestInfo execute(RequestInfo requestInfo) {
        return null;
    }

}
