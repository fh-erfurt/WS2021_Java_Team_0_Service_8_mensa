package de.fherfurt.mensa.rating.boundary;

import de.fherfurt.mensa.core.mappers.BeanMapper;
import de.fherfurt.mensa.rating.boundary.transfer.objects.ImageDto;
import de.fherfurt.mensa.rating.boundary.transfer.objects.RatingDto;
import de.fherfurt.mensa.rating.entity.Image;
import de.fherfurt.mensa.rating.entity.Rating;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import java.util.List;

import static de.fherfurt.mensa.TestTags.BOUNDARY;
import static de.fherfurt.mensa.TestTags.DOMAIN;
import static de.fherfurt.mensa.TestTags.UNIT;

@Tags({
        @Tag(UNIT),
        @Tag(DOMAIN),
        @Tag(BOUNDARY)
})
class RatingMapperTest {

    private SoftAssertions softly;

    @BeforeEach
    void beforeEach() {
        softly = new SoftAssertions();
    }

    @AfterEach
    void afterEach() {
        softly.assertAll();
    }

    @Test
    void shouldMapCompleteRatingEntityToDto() {
        // GIVEN
        final Rating entity = Rating.builder()
                .withId(1)
                .withRating(2)
                .withDescription("This is a test rating with 2")
                .withMealId(1)
                .withImages(List.of(Image.builder().withId(1).build()))
                .build();

        // WHEN
        final RatingDto result = BeanMapper.mapToDto(entity);

        // THEN
        softly.assertThat(result)
                .returns(entity.getId(), RatingDto::getId)
                .returns(entity.getRating(), RatingDto::getRating)
                .returns(entity.getDescription(), RatingDto::getDescription)
                .returns(entity.getMealId(), RatingDto::getMealId);
    }

    @Test
    void shouldMapCompleteRatingDtoToEntity() {
        // GIVEN
        final RatingDto dto = RatingDto.builder()
                .withId(1)
                .withRating(2)
                .withDescription("This is a test rating with 2")
                .withMealId(1)
                .withImages(List.of(ImageDto.builder().withId(1).build()))
                .build();

        // WHEN
        final Rating result = BeanMapper.mapFromDto(dto);

        // THEN
        softly.assertThat(result)
                .returns(dto.getId(), Rating::getId)
                .returns(dto.getRating(), Rating::getRating)
                .returns(dto.getDescription(), Rating::getDescription)
                .returns(dto.getMealId(), Rating::getMealId);
    }
}