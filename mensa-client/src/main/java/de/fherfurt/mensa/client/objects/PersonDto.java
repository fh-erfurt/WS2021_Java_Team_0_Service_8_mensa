package de.fherfurt.mensa.client.objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Container that holds all information of a person for the transport from the <i>MENSA-Service</i> to another one.<br/>
 * <b>Important: </b> Do not modify that object! If other information are required, ask for a new release of the
 * <i>MENSA-Service</i>!
 *
 * @author Michael Rhoese <michael.rhoese@fh-erfurt.de>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class PersonDto {
    /**
     * Unique identifier of an already persisted entity.
     */
    private int id;

    /**
     * Alias of the user which creates the rating.
     */
    private String alias;

    /**
     * URL to the user's avatar.
     */
    private String imageUrl;
}
