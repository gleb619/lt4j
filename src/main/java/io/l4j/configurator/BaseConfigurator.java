package io.l4j.configurator;

import com.vanillasource.jaywire.standalone.StandaloneModule;
import io.l4j.core.Configurator;
import io.l4j.factory.converter.XmlConverter;
import io.l4j.factory.converter.YamlConverter;
import io.l4j.factory.http.ApacheHttpClient;
import io.l4j.factory.http.OkHttpClient;

public class BaseConfigurator implements Configurator {

    public static class Module extends StandaloneModule {

        public XmlConverter xmlConverter(){
            return singleton(() -> new XmlConverter());
        }

        public YamlConverter yamlConverter(){
            return singleton(() -> new YamlConverter());
        }

        public OkHttpClient okHttpClient(){
            return singleton(() -> new OkHttpClient());
        }

        public ApacheHttpClient apacheHttpClient(){
            return singleton(() -> new ApacheHttpClient());
        }

    }

}
