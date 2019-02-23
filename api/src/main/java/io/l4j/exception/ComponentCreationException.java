package io.l4j.exception;

import io.l4j.core.ComponentType;
import io.l4j.core.Constants;
import io.l4j.core.Type;

public class ComponentCreationException extends RuntimeException {

    public ComponentCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public static RuntimeException create(Throwable t, ComponentType componentType, Type type) {
        return new ComponentCreationException(String.format(Constants.ERROR_COMPONENT_CREATE, componentType, type), t);
    }

}
