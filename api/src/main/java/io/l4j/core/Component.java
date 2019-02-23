package io.l4j.core;

public interface Component<D extends ComponentType, T extends Type> {

    default void onCreate(Context context) {
    }

    default void onConfig(Context context) {
    }

    default void onStart(Context context) {
    }

    default void onStop(Context context) {
    }

    default void onDestroy(Context context) {
    }

    D getComponentType();

    T getType();

    default int priority() {
        return 1000;
    }

}
