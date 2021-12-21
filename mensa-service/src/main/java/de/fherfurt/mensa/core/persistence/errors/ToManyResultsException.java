package de.fherfurt.mensa.core.persistence.errors;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ToManyResultsException extends PersistenceException {
    public ToManyResultsException(String message) {
        super(message);
    }
}
