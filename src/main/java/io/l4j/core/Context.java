package io.l4j.core;

import lombok.Data;

import java.util.List;
import java.util.concurrent.ExecutorService;

@Data
public class Context {

    private List<Component> components;
    private State state;
    private ExecutorService threadPool;

}
