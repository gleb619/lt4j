package io.l4j.model;

import io.l4j.core.Model;
import lombok.Data;

import java.util.List;

@Data
public class Project implements Model {

    private String name;
    private String address;
    private String proxy;
    private Scope scope;
    private List<Pipeline> pipelines;

}
