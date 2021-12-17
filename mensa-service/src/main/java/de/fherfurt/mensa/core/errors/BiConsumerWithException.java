package de.fherfurt.mensa.core.errors;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface BiConsumerWithException<V1, V2, E extends Exception> {
    void accept(V1 one, V2 other) throws E;

    static <V1, V2, E extends Exception> BiConsumer<V1, V2> wrap(BiConsumerWithException<V1, V2, E> consumer) {
        return (arg1, arg2) -> {
            try {
                consumer.accept(arg1, arg2);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
