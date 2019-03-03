package io.l4j.core;

import io.l4j.core.specification.HttpClientType;
import io.l4j.model.Job;
import io.l4j.model.Pipeline;
import io.l4j.model.Project;

public interface HttpClient extends Component<ComponentType, HttpClientType> {

    RequestInfo prepare(Project project, Pipeline pipeline, Job job, int index);

    RequestInfo execute(RequestInfo requestInfo);

    @Override
    default ComponentType getComponentType() {
        return ComponentType.HTTP;
    }

}
