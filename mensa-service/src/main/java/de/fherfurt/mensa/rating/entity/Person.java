package de.fherfurt.mensa.rating.entity;

import de.fherfurt.mensa.core.persistence.BaseBusinessEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data object that holds the information from the database.
 *
 * @author Michael Rhoese <michael.rhoese@fh-erfurt.de>
 */
@Getter
@Setter
@NoArgsConstructor
public class Person extends BaseBusinessEntity {
    private int id;
    private String alias;
    private String imageUrl;

    @Builder(setterPrefix = "with")
    public Person(int id, String alias, String imageUrl) {
        super(id);
        this.alias = alias;
        this.imageUrl = imageUrl;
    }
}
