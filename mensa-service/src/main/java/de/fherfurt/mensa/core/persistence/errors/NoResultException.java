package de.fherfurt.mensa.core.persistence.errors;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoResultException extends PersistenceException {
    public NoResultException(String message) {
        super(message);
    }
}
