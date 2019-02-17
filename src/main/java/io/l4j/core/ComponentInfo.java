package io.l4j.core;

import lombok.*;

import java.util.Optional;
import java.util.function.Function;

import static io.l4j.core.Constants.EMPTY_COMPONENT_INFO;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ComponentInfo<T extends Component> {

    public final static ComponentInfo EMPTY = new ComponentInfo();

    private Class<T> componentClass;
    private Function<ComponentModule, T> factoryMethod;
    private Type type;

    public T createComponent(ComponentModule componentModule) {
        return Optional.ofNullable(factoryMethod)
                .map(function -> function.apply(componentModule))
                .orElseThrow(() -> new IllegalStateException(EMPTY_COMPONENT_INFO));
    }

}
