package io.l4j.factory.http;

import io.l4j.core.RequestInfo;
import io.l4j.factory.http.model.OkHttpRequestInfo;
import io.l4j.test.WebServer;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class OkHttpClientTest {

    @InjectMocks
    OkHttpClient okHttpClient;

    @Spy
    okhttp3.OkHttpClient httpClient;

    WebServer webServer;

    private RequestInfo resultRequestInfo;
    private RequestInfo awaitedRequestInfo;

    @Before
    public void setUp() {
        webServer = WebServer.create("{\"test1\":\"test2\"}");

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        String requestBody = "{ \"test\" : \"text\" }";
        RequestBody body = RequestBody.create(mediaType, requestBody);
        resultRequestInfo = OkHttpRequestInfo.builder()
                .request(new Request.Builder()
                        .url(webServer.getAddress())
                        .post(body)
                        .build())
                .build();

        awaitedRequestInfo = new OkHttpRequestInfo();
    }

    @Test
    public void execute() {
        RequestInfo requestInfo = okHttpClient.execute(resultRequestInfo);
        assertThat(requestInfo)
                .isNotNull()
                .isEqualTo(awaitedRequestInfo);
    }

    @After
    public void tierDown() {
        webServer.stop();
    }

}