package io.l4j.core;

public interface Reader extends Component {

    @Override
    default ComponentType getComponentType() {
        return ComponentType.READER;
    }

    @Override
    default Type getType() {
        return Type.none();
    }

    @Override
    default int priority() {
        return 2;
    }

}
