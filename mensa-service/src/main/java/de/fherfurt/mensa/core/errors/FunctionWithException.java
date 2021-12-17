package de.fherfurt.mensa.core.errors;

import java.util.function.Function;

@FunctionalInterface
public interface FunctionWithException<T, R, E extends Exception> {
    R apply(T t) throws E;

    static <T, R, E extends Exception> Function<T, R> wrap(FunctionWithException<T, R, E> consumer) {
        return arg -> {
            try {
                return consumer.apply(arg);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
