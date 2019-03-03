package io.l4j.factory.strategy;

import io.l4j.core.*;
import io.l4j.model.Job;
import io.l4j.model.Pipeline;
import io.l4j.model.Project;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class LinearStrategy implements Strategy {

    @Override
    public void onConfig(Context context) {
        HttpClient httpClient = context.getComponent(ComponentType.HTTP);
        Project project = context.getState().getProject();
        List<Pipeline> pipelines = project.getPipelines();

        Map<Integer, RequestInfo> requestInfos = new HashMap<>();
        for (Pipeline pipeline : pipelines) {
            for (int index = 0; index < pipeline.getStrategy().getIterations(); index++) {
                for (Job job : pipeline.getJobs()) {
                    RequestInfo requestInfo = httpClient.prepare(project, pipeline, job, index);
                    requestInfos.put(String.valueOf("" + pipeline + job + index).hashCode(), requestInfo);
                }
            }
        }

        context.getState().setRequestInfos(requestInfos);
    }

    @Override
    public void onStart(Context context) {
        HttpClient httpClient = context.getComponent(ComponentType.HTTP);
        Map<Integer, RequestInfo> requestInfos = context.getState().getRequestInfos();

        for (Map.Entry<Integer, RequestInfo> requestInfoEntry : requestInfos.entrySet()) {
            RequestInfo requestInfo = httpClient.execute(requestInfoEntry.getValue());
            log.info("Got response: {}", requestInfo);
        }
    }

}
