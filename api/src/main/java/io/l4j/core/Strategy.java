package io.l4j.core;

import io.l4j.core.specification.StrategyType;

public interface Strategy extends Component<ComponentType, StrategyType> {

    @Override
    default ComponentType getComponentType() {
        return ComponentType.STRATEGY;
    }

    @Override
    default StrategyType getType() {
        return StrategyType.ADAPTER;
    }

}
