package io.l4j.core;

import io.l4j.core.specification.HttpClientType;
import io.l4j.core.specification.ProcessorType;
import lombok.Getter;

@Getter
public enum ComponentType {

    CONFIGURATOR,
    CONVERTER,
    STORAGE,
    PROCESSOR(ProcessorType.IO),
    SCHEDULER,
    HTTP(HttpClientType.OKHTTP),
    READER,
    STRATEGY;

    private final Type type;

    ComponentType(Type defaultType) {
        this.type = defaultType;
    }

    ComponentType() {
        type = Type.none();
    }

}
