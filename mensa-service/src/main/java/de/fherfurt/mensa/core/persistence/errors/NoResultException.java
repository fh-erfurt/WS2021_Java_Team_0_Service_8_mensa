package de.fherfurt.mensa.core.persistence.errors;

import lombok.NoArgsConstructor;

/**
 * Thrown if a result of a database request is expected but there wasn't any.
 *
 * @author Michael Rhoese <michael.rhoese@fh-erfurt.de>
 */
@NoArgsConstructor
public class NoResultException extends PersistenceException {
    public NoResultException(String message) {
        super(message);
    }
}
