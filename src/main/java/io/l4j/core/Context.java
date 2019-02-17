package io.l4j.core;

import lombok.Data;

import java.io.Closeable;
import java.util.Map;
import java.util.concurrent.ExecutorService;

@Data
public class Context implements Closeable {

    private ComponentModule module;
    private Configurator configurator;
    private Map<ComponentType, Map<Type, ComponentInfo<Component>>> components;
    private State state;
    private ExecutorService threadPool;

    public Component getComponent(ComponentType componentType, Type type) {
        return configurator.findComponent(module, components, componentType, type);
    }

    @Override
    public void close() {
        components.clear();
    }

}
