package io.l4j.core;

import io.l4j.model.Project;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class State {

    private String configFileLocation;
    private Project project;

}
