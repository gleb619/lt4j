package io.l4j.core;

import java.util.Map;

public interface Configurator extends Component<ComponentType, Type> {

    Component findComponent(ComponentModule componentModule,
                            Map<ComponentType, Map<Type, ComponentInfo<Component>>> components,
                            ComponentType componentType,
                            Type type);

    @Override
    default ComponentType getComponentType() {
        return ComponentType.CONFIGURATOR;
    }

    @Override
    default Type getType() {
        return Type.none();
    }

}
