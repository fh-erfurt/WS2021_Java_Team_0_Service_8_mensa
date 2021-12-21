package de.fherfurt.mensa.rating.entity;

import de.fherfurt.mensa.core.persistence.BaseBusinessEntity;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Data object that holds the information from the database.
 *
 * @author Michael Rhoese <michael.rhoese@fh-erfurt.de>
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Rating extends BaseBusinessEntity {

    private Person evaluator;
    private int mealId;
    private int rating;
    private String description;
    private List<Image> images;

    @Builder(setterPrefix = "with")
    public Rating(int id, Person evaluator, int mealId, int rating, String description, List<Image> images) {
        super(id);
        this.evaluator = evaluator;
        this.mealId = mealId;
        this.rating = rating;
        this.description = description;
        this.images = images;
    }
}
