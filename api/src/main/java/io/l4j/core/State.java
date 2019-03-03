package io.l4j.core;

import io.l4j.model.Project;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class State {

    private String configFileLocation;
    private Project project;
    private Map<Integer, RequestInfo> requestInfos;

}
