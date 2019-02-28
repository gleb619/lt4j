package io.l4j.core;

import io.l4j.core.specification.ProcessorType;
import lombok.Getter;

@Getter
public enum ComponentType {

    CONFIGURATOR,
    CONVERTER,
    STORAGE,
    PROCESSOR(ProcessorType.IO),
    SCHEDULER,
    HTTP,
    READER;

    private final Type type;

    ComponentType(Type defaultType) {
        this.type = defaultType;
    }

    ComponentType() {
        type = Type.none();
    }

}
