package de.fherfurt.mensa.rating.boundary;

import de.fherfurt.mensa.client.objects.ImageDto;
import de.fherfurt.mensa.core.mappers.BeanMapper;
import de.fherfurt.mensa.rating.entity.Image;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import static de.fherfurt.mensa.TestTags.BOUNDARY;
import static de.fherfurt.mensa.TestTags.DOMAIN;
import static de.fherfurt.mensa.TestTags.UNIT;

@Tags({
        @Tag(UNIT),
        @Tag(DOMAIN),
        @Tag(BOUNDARY)
})
class ImageMapperTest {

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
    void shouldMapCompleteImageEntityToDto() {
        // GIVEN
        final Image entity = Image.builder()
                .withId(1)
                .withName("image1")
                .withSuffix("jpg")
                .build();

        // WHEN
        final ImageDto result = BeanMapper.mapToDto(entity);

        // THEN
        softly.assertThat(result)
                .returns(entity.getId(), ImageDto::getId)
                .returns(entity.getName(), ImageDto::getName)
                .returns(entity.getSuffix(), ImageDto::getSuffix);
    }

    @Test
    void shouldMapCompleteRatingDtoToEntity() {
        // GIVEN
        final ImageDto entity = ImageDto.builder()
                .withId(1)
                .withName("image1")
                .withSuffix("jpg")
                .build();

        // WHEN
        final Image result = BeanMapper.mapFromDto(entity);

        // THEN
        softly.assertThat(result)
                .returns(entity.getId(), Image::getId)
                .returns(entity.getName(), Image::getName)
                .returns(entity.getSuffix(), Image::getSuffix);
    }
}