package de.fherfurt.mensa.rating.business.errors;

import org.apache.commons.lang3.StringUtils;

/**
 * Thrown if no user was found. The users are requested on the <i>Persons Service</i>.
 *
 * @author Michael Rhoese <michael.rhoese@fh-erfurt.de>
 */
public class UserNotFoundException extends RuntimeException {
    private final String userAlias;
    private final int userId;

    public UserNotFoundException(final String userAlias) {
        this.userAlias = userAlias;
        this.userId = 0;
    }

    public UserNotFoundException(final int userId) {
        this.userAlias = StringUtils.EMPTY;
        this.userId = userId;
    }

    @Override
    public String getLocalizedMessage() {
        boolean identifierTypeAlias = StringUtils.isNotBlank(userAlias);
        return "No user found for " + (identifierTypeAlias ? "alias" : "id") + " '" + (identifierTypeAlias ? userAlias : userId) + "'";
    }
}
