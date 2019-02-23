package io.l4j.core.specification;

import io.l4j.core.Type;
import io.vavr.control.Try;

import java.net.URL;
import java.nio.file.Paths;

public enum ProcessorType implements Type {

    FS,
    HTTP,
    IO,
    UNKNOWN;

    public static ProcessorType parseType(String path) {
        URL url = Try.of(() -> new URL(path))
                .getOrElseTry(() -> Paths.get(path).normalize().toUri().toURL());

        switch (url.getProtocol()) {
            case "http":
            case "https":
                return HTTP;
            case "file":
                return FS;
        }

        return UNKNOWN;
    }

}
