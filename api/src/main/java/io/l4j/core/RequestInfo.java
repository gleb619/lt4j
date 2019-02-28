package io.l4j.core;

import java.util.List;
import java.util.Map;

public interface RequestInfo {

    <T> T getRequest();

    RequestInfo withResponse(int code, Map<String, List<String>> headers, byte[] bytes);

}
