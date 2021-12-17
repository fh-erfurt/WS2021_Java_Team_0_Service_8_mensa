package de.fherfurt.mensa.rating.boundary.transfer.objects;

import de.fherfurt.persons.client.transfer.objects.MensaPerson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class RatingDto {
    private int id;
    private MensaPerson evaluator;
    private int mealId;
    private int rating;
    private String description;
    private List<ImageDto> images;
}
