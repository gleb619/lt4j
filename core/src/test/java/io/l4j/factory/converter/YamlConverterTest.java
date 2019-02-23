package io.l4j.factory.converter;

import com.google.common.collect.ImmutableMap;
import io.l4j.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class YamlConverterTest {

    @InjectMocks
    YamlConverter yamlConverter;

    private Project resultProject;

    @Before
    public void setUp() {
        resultProject = new Project();
        resultProject.setName("test");
        resultProject.setAddress("test1");
        resultProject.setProxy("test2");
        resultProject.setScope(new Scope());
        resultProject.setPipelines(new ArrayList<>());
        resultProject.getPipelines().add(new Pipeline());
        resultProject.setJobs(new ArrayList<>());
        Job job = new Job();
        job.setPath("test3");
        job.setHeaders(ImmutableMap.of("test4", "test5"));
        job.setBody("test6");
        job.setTimeout(10000L);
        job.setAssertions(new ArrayList<>());
        job.getAssertions().add(new Assertion());
        resultProject.getJobs().add(job);
    }

    @Test
    public void from() {
//        yamlConverter.from()
    }

    @Test
    public void to() {
        String result = yamlConverter.to(resultProject);
        assertThat(result)
                .isNotBlank();
    }

}