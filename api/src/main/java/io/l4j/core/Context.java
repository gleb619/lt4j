package io.l4j.core;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Data
@Slf4j
public class Context implements Closeable {

    private ComponentModule module;
    private Configurator configurator;
    private Map<ComponentType, Map<Type, ComponentInfo<Component>>> componentsConfiguration = new HashMap<>();
    private List<Component> components = new ArrayList<>();
    private State state;
    private ExecutorService threadPool = Executors.newWorkStealingPool();

    private Context() {
    }

    public static Context newOne() {
        return new Context();
    }

    public Context withState(State state) {
        this.state = state;
        return this;
    }

    public Context withModule(ComponentModule module) {
        this.module = module;
        return this;
    }

    public Context withConfigurator(Configurator configurator) {
        this.configurator = configurator;
        this.components.add(configurator);
        return this;
    }

    public <T extends Component> T getComponent(ComponentType componentType) {
        return getComponent(componentType, componentType.getType());
    }

    public <T extends Component> T getComponent(ComponentType componentType, Type type) {
        Component component = configurator.findComponent(components, componentType, type)
                .orElseGet(() -> {
                    Component newComponent = configurator.createComponent(module, componentsConfiguration, componentType, type);
                    components.add(newComponent);
                    return newComponent;
                });

        return (T) component;
    }

    public void registerComponent(Component component) {
        ComponentInfo<Component> componentInfo = configurator.createComponentInfo(module, componentsConfiguration, component);
        if (componentsConfiguration.containsKey(component.getComponentType())) {
            componentsConfiguration.get(component.getComponentType()).put(componentInfo.getType(), componentInfo);
        } else {
            componentsConfiguration.put(component.getComponentType(),
                    Maps.newHashMap(ImmutableMap.of(componentInfo.getType(), componentInfo)));
        }
    }

    public <T extends Component> void addComponents(T... components) {
        Objects.requireNonNull(components);
        this.components.addAll(Arrays.asList(components));
    }

    @Override
    public void close() {
        log.info("Context is closing");
        fire(Events.DESTROY);
        componentsConfiguration.clear();
        components.clear();
        module = null;
        state = null;
        configurator = null;
    }

    public void fire(Events create) {
        switch (create) {
            case CREATE:
                components.stream()
                        .sorted(Comparator.comparing(Component::priority))
                        .forEach(component -> component.onCreate(this));
                break;
            case CONFIG:
                components.stream()
                        .sorted(Comparator.comparing(Component::priority))
                        .forEach(component -> component.onConfig(this));
                break;
            case START:
                components.stream()
                        .sorted(Comparator.comparing(Component::priority))
                        .forEach(component -> component.onStart(this));
                break;
            case DESTROY:
                components.stream()
                        .sorted(Comparator.comparing(Component::priority))
                        .forEach(component -> component.onDestroy(this));
                break;
        }
    }

}
