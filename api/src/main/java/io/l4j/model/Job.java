package io.l4j.model;

import io.l4j.core.Model;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Job implements Model {

    private String path;
    private Map<String, String> headers;
    private String body;
    private Long timeout;
    private List<Assertion> assertions;
    private String fileLocation;

}
