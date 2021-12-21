package de.fherfurt.mensa.core.persistence.errors;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PersistenceException extends RuntimeException {

    public PersistenceException(String message) {
        super(message);
    }

    public PersistenceException(Throwable cause) {
        super(cause);
    }

    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
