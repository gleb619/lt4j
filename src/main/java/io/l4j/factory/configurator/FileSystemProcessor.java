package io.l4j.factory.configurator;

import io.l4j.core.specification.IOProcessor;
import io.l4j.core.specification.ProcessorType;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSystemProcessor implements IOProcessor {

    @Override
    @SneakyThrows
    public byte[] read(String filePath) {
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }

    @Override
    @SneakyThrows
    public void write(String filePath, byte[] data) {
        Files.write(Paths.get(filePath), data);
    }

    @Override
    public ProcessorType getType() {
        return ProcessorType.FS;
    }

}
