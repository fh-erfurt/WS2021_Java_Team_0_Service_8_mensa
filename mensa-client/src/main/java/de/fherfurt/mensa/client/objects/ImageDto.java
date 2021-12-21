package de.fherfurt.mensa.client.objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Container that holds all information of an image for the transport from the <i>MENSA-Service</i> to another one.<br/>
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
public class ImageDto {
    /**
     * Unique identifier of an already persisted entity.
     */
    private int id;

    /**
     * File name of the image file.
     */
    private String name;

    /**
     * File suffix (type) of the image file.
     */
    private String suffix;

    /**
     * The image as byte array.
     */
    private byte[] content;
}
