package io.l4j.factory.configurator;

import io.l4j.core.specification.DataFormat;
import io.l4j.core.specification.IOProcessor;
import io.l4j.core.specification.ProcessorType;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class BaseProcessor implements IOProcessor {

    private final Map<ProcessorType, IOProcessor> processors;

    public BaseProcessor() {
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
        String extension = FilenameUtils.getExtension(path);
        DataFormat dataFormat = DataFormat.parseType(extension);
        return processors.get(dataFormat);
    }

}
