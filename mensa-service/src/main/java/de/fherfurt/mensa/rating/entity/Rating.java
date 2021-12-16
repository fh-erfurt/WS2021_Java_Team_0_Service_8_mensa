package de.fherfurt.mensa.rating.entity;

import de.fherfurt.mensa.core.persistence.BaseBusinessEntity;
import de.fherfurt.persons.client.transfer.objects.MensaPerson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Rating extends BaseBusinessEntity {

    private MensaPerson evaluator;
    private int mealId;
    private int rating;
    private String description;
    private List<Image> images;
}
