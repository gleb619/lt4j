package io.l4j.core;

import io.l4j.core.specification.ProcessorType;

public interface Processor<D extends ProcessorType> extends Component<ComponentType, ProcessorType> {

    default D getType() {
        return (D) ProcessorType.IO;
    }

    @Override
    default ComponentType getComponentType() {
        return ComponentType.PROCESSOR;
    }

}
