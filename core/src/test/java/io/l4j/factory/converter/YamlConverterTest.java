package io.l4j.factory.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Resources;
import io.l4j.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class YamlConverterTest {

    @InjectMocks
    YamlConverter yamlConverter;

    @Spy
    ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

    private Project resultProject;
    private String resultYaml;

    @Before
    public void setUp() throws Exception {
        Job job = new Job();
        job.setPath("test3");
        job.setHeaders(ImmutableMap.of("test4", "test5"));
        job.setBody("test6");
        job.setTimeout(10000L);
        job.setFileLocation("test14");
        job.setMethod("GET");
        job.setAssertions(Arrays.asList(new Assertion()));

        Job job1 = new Job();
        job1.setPath("test10");
        job1.setHeaders(ImmutableMap.of("test11", "test12"));
        job1.setBody("test13");
        job1.setTimeout(10001L);
        job1.setFileLocation("test15");
        job.setMethod("POST");
        job1.setAssertions(Arrays.asList(new Assertion()));

        Strategy strategy = new Strategy();
        strategy.setType("test9");
        strategy.setDelay(50);
        strategy.setIterations(1000L);

        Pipeline pipeline = new Pipeline();
        pipeline.setFileLocation("test7");
        pipeline.setName("test8");
        pipeline.setOrder(new Order());
        pipeline.setScope(new Scope());
        pipeline.setStrategy(strategy);
        pipeline.setJobs(Arrays.asList(job, job1));

        resultProject = new Project();
        resultProject.setName("test");
        resultProject.setAddress("test1");
        resultProject.setProxy("test2");
        resultProject.setScope(new Scope());
        resultProject.setPipelines(Arrays.asList(pipeline));

        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        resultYaml = Resources.toString(Resources.getResource("project.yml"), StandardCharsets.UTF_8);
    }

    @Test
    public void from() {
        Project result = yamlConverter.from(resultYaml, Project.class);
        assertThat(result)
                .isNotNull()
                .isEqualTo(resultProject);
    }

    @Test
    public void to() {
        String result = yamlConverter.to(resultProject);
        assertThat(result)
                .isNotBlank();
    }

}