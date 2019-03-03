package io.l4j.api;

import com.google.common.io.Resources;
import io.l4j.test.WebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.net.URL;

@RunWith(MockitoJUnitRunner.class)
public class CliInterfaceTest {

    @InjectMocks
    CliInterface cliInterface;

    private String[] resultArgs;
    private WebServer webServer;

    @Before
    public void setUp() throws Exception {
        URL resource = Resources.getResource("project.yml");
        String path = new File(resource.toURI()).getAbsolutePath();

        resultArgs = new String[]{"-c", path};
        webServer = WebServer.create("{\"test\":\"data\", \"id\": \"123\"}", 8080);
    }

    @Test
    public void main() {
        CliInterface.main(resultArgs);
    }

    @After
    public void tearDown() {
        webServer.stop();
    }

}