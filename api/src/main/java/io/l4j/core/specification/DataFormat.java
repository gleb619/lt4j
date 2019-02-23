package io.l4j.core.specification;

import io.l4j.core.Type;
import io.vavr.control.Try;

public enum DataFormat implements Type {

    YAML,
    XML,
    JSON,
    UNKNOWN,
    ADAPTER;

    public static DataFormat parseType(String extension) {
        return Try.of(() -> DataFormat.valueOf(extension))
                .getOrElse(() -> {
                    switch (extension) {
                        case "yml":
                            return DataFormat.YAML;
                    }

                    return UNKNOWN;
                });
    }

}
