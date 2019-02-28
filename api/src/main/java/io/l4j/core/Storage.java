package io.l4j.core;

import io.l4j.core.specification.StorageType;

public interface Storage extends Component<ComponentType, StorageType> {

    @Override
    default ComponentType getComponentType() {
        return ComponentType.STORAGE;
    }

    @Override
    default StorageType getType() {
        return StorageType.FS;
    }

}
