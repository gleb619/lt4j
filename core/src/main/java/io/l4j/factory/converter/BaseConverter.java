package io.l4j.factory.converter;

import io.l4j.core.Converter;
import io.l4j.core.specification.DataFormat;

import java.util.HashMap;
import java.util.Map;

public class BaseConverter implements Converter<DataFormat> {

    private final Map<DataFormat, Converter> processors;

    public BaseConverter() {
        this.processors = new HashMap<>();
    }

    @Override
    public <T> T from(String text, Class<T> clazz) {
        Converter converter = findConverter(text);
        return (T) converter.from(text, clazz);
    }

    @Override
    public <T> T from(byte[] bytes, Class<T> projectClass) {
        throw new IllegalArgumentException("Unknown type for conversion");
    }

    @Override
    public String to(Object object) {
        throw new IllegalArgumentException("Unknown type for conversion");
    }

    public Converter findConverter(String text) {
        DataFormat dataFormat = detectDataFormat(text);
        return processors.get(dataFormat);
    }

    private DataFormat detectDataFormat(String text) {
        return DataFormat.YAML;
    }

}
