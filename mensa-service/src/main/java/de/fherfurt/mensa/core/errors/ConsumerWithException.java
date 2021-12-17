package de.fherfurt.mensa.core.errors;

import java.util.function.Consumer;

@FunctionalInterface
public interface ConsumerWithException<V, E extends Exception> {
    void accept(V value) throws E;

    static <V, E extends Exception> Consumer<V> wrap(ConsumerWithException<V, E> consumer) {
        return arg -> {
            try {
                consumer.accept(arg);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
