package de.fherfurt.mensa.core.errors;

import java.util.function.Function;

/**
 * This functional interface is used to wrap {@link java.util.function.Function} calls that throws checked exceptions.
 *
 * @param <PARAM>     Generic type of the parameter
 * @param <RESULT>    Generic type of the returned result
 * @param <EXCEPTION> Generic exception type
 * @author Michael Rhoese <michael.rhoese@fh-erfurt.de>
 */
@FunctionalInterface
public interface FunctionWithException<PARAM, RESULT, EXCEPTION extends Exception> {
    RESULT apply(PARAM t) throws EXCEPTION;

    static <PARAM, RESULT, EXCEPTION extends Exception> Function<PARAM, RESULT> wrap(FunctionWithException<PARAM, RESULT, EXCEPTION> consumer) {
        return arg -> {
            try {
                return consumer.apply(arg);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
