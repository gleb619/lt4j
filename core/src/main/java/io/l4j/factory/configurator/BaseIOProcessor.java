package io.l4j.factory.configurator;

import io.l4j.core.specification.IOProcessor;
import io.l4j.core.specification.ProcessorType;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class BaseIOProcessor implements IOProcessor {

    private final Map<ProcessorType, IOProcessor> processors;

    public BaseIOProcessor() {
        this.processors = new HashMap<>();
    }

    @Override
    public byte[] read(String path) {
        IOProcessor processor = findProcessor(path);
        return processor.read(path);
    }

    @Override
    public void write(String path, byte[] data) {
        IOProcessor processor = findProcessor(path);
        processor.write(path, data);
    }

    private IOProcessor findProcessor(String path) {
        URI uri = Paths.get(path).normalize().toUri();
        ProcessorType processorType = ProcessorType.parseType(path);
        return processors.get(processorType);
    }

}
