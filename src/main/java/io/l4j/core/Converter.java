package io.l4j.core;

import io.l4j.core.specification.DataFormat;

public interface Converter<D extends DataFormat> extends Component<ComponentType, DataFormat> {

    <T> T from(String text, Class<T> clazz);

    String to(String text);

    default D getType() {
        return (D) DataFormat.ADAPTER;
    }

    @Override
    default ComponentType getComponentType() {
        return ComponentType.CONVERTER;
    }

}
