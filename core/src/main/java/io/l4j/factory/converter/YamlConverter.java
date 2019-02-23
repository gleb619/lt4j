package io.l4j.factory.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.l4j.core.Converter;
import io.l4j.core.specification.DataFormat;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class YamlConverter implements Converter<DataFormat> {

    private final ObjectMapper objectMapper;

    public YamlConverter() {
        this.objectMapper = null;
    }

    @Override
    @SneakyThrows
    public <E> E from(String text, Class<E> clazz) {
        return objectMapper.readValue(text, clazz);
    }

    @Override
    @SneakyThrows
    public <T> T from(byte[] bytes, Class<T> projectClass) {
        return objectMapper.readValue(bytes, projectClass);
    }

    @Override
    @SneakyThrows
    public String to(Object object) {
        return objectMapper.writeValueAsString(object);
    }

    @Override
    public DataFormat getType() {
        return DataFormat.YAML;
    }

}
