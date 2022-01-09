package de.fherfurt.mensa.core.errors;

import de.fherfurt.mensa.core.persistence.errors.PersistenceException;
import lombok.NoArgsConstructor;

/**
 * Thrown if a parameter of a function requires a non-null value, but it is null.
 *
 * @author Michael Rhoese <michael.rhoese@fh-erfurt.de>
 */
@NoArgsConstructor
public class MissingValueException extends PersistenceException {
    public MissingValueException(String message) {
        super(message);
    }
}
