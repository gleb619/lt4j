package io.l4j.factory.http;

import fi.iki.elonen.NanoHTTPD;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.alexpanov.net.FreePortFinder;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class WebServer extends NanoHTTPD implements AutoCloseable {

    private final String status;
    private final String mimeType;
    private final String response;

    @SneakyThrows
    public WebServer(String status, String mimeType, String response) {
        super(FreePortFinder.findFreeLocalPort());
        this.status = status;
        this.mimeType = mimeType;
        this.response = response;
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        log.info("Started at http://127.0.0.1:{}", getListeningPort());
    }

    @SneakyThrows
    public static WebServer create(String response) {
        return new WebServer("OK", "application/json", response);
    }

    @Override
    @SneakyThrows
    public Response serve(IHTTPSession session) {
        String body = "";
        if (Method.POST.equals(session.getMethod())) {
            final Map<String, String> map = new HashMap<>();
            session.parseBody(map);
            body = map.get("postData");
        }

        log.info("Got request with: uri: {},  method: {}, headers: {}, body: {}",
                session.getUri(),
                session.getMethod(),
                session.getHeaders(),
                body);

        return newFixedLengthResponse(Response.Status.valueOf(status), mimeType, response);
    }

    @Override
    public void close() {
        stop();
    }

    public String getAddress() {
        return "http://127.0.0.1:" + getListeningPort();
    }

}
