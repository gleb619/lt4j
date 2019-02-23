package io.l4j.core;

import io.l4j.core.specification.HttpClientType;

public interface HttpClient extends Component<ComponentType, HttpClientType> {

    void post();

    @Override
    default ComponentType getComponentType() {
        return ComponentType.HTTP;
    }

}
