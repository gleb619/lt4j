package io.l4j.configurator;

import io.l4j.core.Context;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BaseConfiguratorTest {

    @InjectMocks
    BaseConfigurator baseConfigurator;

    private Context resultContext;

    @Before
    public void setUp() {
        resultContext = new Context();
        resultContext.setModule(new ConfiguratorModule());
    }

    @Test
    public void onCreate() {
        baseConfigurator.onCreate(resultContext);
    }


    @Test
    public void onConfig() {
        baseConfigurator.onConfig(resultContext);
    }

}