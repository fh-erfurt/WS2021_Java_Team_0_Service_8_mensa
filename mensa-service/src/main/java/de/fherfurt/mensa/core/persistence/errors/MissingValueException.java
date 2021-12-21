package de.fherfurt.mensa.core.persistence.errors;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MissingValueException extends PersistenceException {
    public MissingValueException(String message) {
        super(message);
    }
}
