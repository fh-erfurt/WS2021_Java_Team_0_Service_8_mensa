package de.fherfurt.mensa.core.persistence.errors;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MissingContentException extends PersistenceException {
    public MissingContentException(String message) {
        super(message);
    }
}
