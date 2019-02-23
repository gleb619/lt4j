package io.l4j.core;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Configurator extends Component<ComponentType, Type> {

    @Override
    default ComponentType getComponentType() {
        return ComponentType.CONFIGURATOR;
    }

    @Override
    default Type getType() {
        return Type.none();
    }

    @Override
    default int priority() {
        return 1;
    }

    Component createComponent(ComponentModule componentModule,
                              Map<ComponentType, Map<Type, ComponentInfo<Component>>> components,
                              ComponentType componentType,
                              Type type);

    ComponentInfo createComponentInfo(ComponentModule module,
                                      Map<ComponentType, Map<Type, ComponentInfo<Component>>> componentsConfiguration,
                                      Component component);

    Optional<Component> findComponent(List<Component> components, ComponentType componentType, Type type);

}
