package io.l4j.configurator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.collect.ImmutableMap;
import com.vanillasource.jaywire.standalone.StandaloneModule;
import io.l4j.core.ComponentModule;
import io.l4j.factory.configurator.BaseProcessor;
import io.l4j.factory.configurator.FileSystemProcessor;
import io.l4j.factory.converter.YamlConverter;
import io.l4j.factory.http.ApacheHttpClient;
import io.l4j.factory.http.OkHttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class ConfiguratorModule extends StandaloneModule implements ComponentModule {

    /* =============== COMPONENTS =============== */

    public YamlConverter resolveYamlConverter() {
        return singleton(() -> new YamlConverter(provideYamlMapper()));
    }

    public OkHttpClient resolveOkHttpClient() {
        return singleton(() -> new OkHttpClient(provideOkHttpClient()));
    }

    public ApacheHttpClient resolveApacheHttpClient() {
        return singleton(() -> new ApacheHttpClient(provideCloseableHttpClient()));
    }

    public BaseProcessor resolveBaseProcessor() {
        return singleton(() -> new BaseProcessor(ImmutableMap.of(
                resolveFileSystemProcessor().getType(), resolveFileSystemProcessor()
        )));
    }

    public FileSystemProcessor resolveFileSystemProcessor() {
        return singleton(() -> new FileSystemProcessor());
    }

    /* =============== OUTSIDE DEPENDENCIES =============== */

    public ObjectMapper provideYamlMapper() {
        return singleton(() -> new ObjectMapper(new YAMLFactory()));
    }

    public okhttp3.OkHttpClient provideOkHttpClient() {
        return singleton(() -> new okhttp3.OkHttpClient());
    }

    public CloseableHttpClient provideCloseableHttpClient() {
        return singleton(() -> HttpClients.createDefault());
    }

}
