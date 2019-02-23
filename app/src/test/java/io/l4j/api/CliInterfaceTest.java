package io.l4j.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CliInterfaceTest {

    @InjectMocks
    CliInterface cliInterface;
    private String[] resultArgs;

    @Before
    public void setUp() {
        resultArgs = new String[]{"-c", "./project.yml"};
    }

    @Test
    public void main() {
        CliInterface.main(resultArgs);
    }

}