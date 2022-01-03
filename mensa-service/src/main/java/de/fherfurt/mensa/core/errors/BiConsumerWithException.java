package de.fherfurt.mensa.core.errors;

import java.util.function.BiConsumer;

/**
 * This functional interface is used to wrap {@link BiConsumer} calls that throws checked exceptions.
 *
 * @param <PARAM_1>   Generic type of the parameter 1
 * @param <PARAM_2>   Generic type of the parameter 2
 * @param <EXCEPTION> Generic exception type
 * @author Michael Rhoese <michael.rhoese@fh-erfurt.de>
 */
@FunctionalInterface
public interface BiConsumerWithException<PARAM_1, PARAM_2, EXCEPTION extends Exception> {
    void accept(PARAM_1 one, PARAM_2 other) throws EXCEPTION;

    static <PARAM_1, PARAM_2, EXCEPTION extends Exception> BiConsumer<PARAM_1, PARAM_2> wrap(BiConsumerWithException<PARAM_1, PARAM_2, EXCEPTION> consumer) {
        return (arg1, arg2) -> {
            try {
                consumer.accept(arg1, arg2);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
