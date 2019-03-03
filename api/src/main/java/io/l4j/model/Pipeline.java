package io.l4j.model;

import io.l4j.core.Model;
import lombok.Data;

import java.util.List;

@Data
public class Pipeline implements Model {

    private String name;
    private List<Job> jobs;
    private Order order;
    private Scope scope;
    private String fileLocation;
    private Strategy strategy;

}
