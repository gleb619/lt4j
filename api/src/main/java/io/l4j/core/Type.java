package io.l4j.core;

public interface Type {

    static Type none() {
        return new None();
    }

    class None implements Type {
    }

}
