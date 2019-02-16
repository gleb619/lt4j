package io.l4j.factory.http;

import io.l4j.core.HttpClient;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.http.HttpVersion;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.client.fluent.Request;

@Data
public class ApacheHttpClient implements HttpClient {

    private CloseableHttpClient closeableHttpClient;

    @Override
    @SneakyThrows
    public void post() {
        Request.Post("http://somehost/do-stuff")
                .useExpectContinue()
                .version(HttpVersion.HTTP_1_1)
                .bodyString("Important stuff", ContentType.DEFAULT_TEXT)
                .execute().returnContent().asBytes();
    }

}
