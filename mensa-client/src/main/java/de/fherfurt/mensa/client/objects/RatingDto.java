package de.fherfurt.mensa.client.objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Container that holds all information of a rating for the transport from the <i>MENSA-Service</i> to another one.<br/>
 * <b>Important: </b> Do not modify that object! If other information are required, ask for a new release of the
 * <i>MENSA-Service</i>!
 *
 * @author Michael Rhoese <michael.rhoese@fh-erfurt.de>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class RatingDto {
    /**
     * Unique identifier of an already persisted entity.
     */
    private int id;

    /**
     * The person that creates the rating.
     */
    private PersonDto evaluator;

    /**
     * The ID of meal for which that rating is.
     */
    private int mealId;

    /**
     * The numerical rating. Can be a value between 1 and 10.
     */
    private int rating;

    /**
     * The textual rating of the meal.
     */
    private String description;

    /**
     * List of (optional) images that also show the reason for the rating.
     */
    private List<ImageDto> images;
}
