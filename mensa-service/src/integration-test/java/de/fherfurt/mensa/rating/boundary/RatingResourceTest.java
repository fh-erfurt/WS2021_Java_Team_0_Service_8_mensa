package de.fherfurt.mensa.rating.boundary;

import de.fherfurt.mensa.rating.boundary.transfer.objects.ImageDto;
import de.fherfurt.mensa.rating.boundary.transfer.objects.RatingDto;
import de.fherfurt.mensa.rating.entity.FileRepository;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static de.fherfurt.mensa.TestTags.BOUNDARY;
import static de.fherfurt.mensa.TestTags.DOMAIN;
import static de.fherfurt.mensa.TestTags.INTEGRATION;

@Tags({
        @Tag(INTEGRATION),
        @Tag(DOMAIN),
        @Tag(BOUNDARY),
})
class RatingResourceTest {

    private static final String imageName = "new-image-";

    private static final byte[] content = loadImage("demo-1.jpg");

    @BeforeAll
    static void beforeAll() throws IOException {
        Path homeDir = Paths.get(FileRepository.BASE_DIR, "mensa");
        if (Files.notExists(homeDir)) {
            return;
        }

        FileUtils.deleteDirectory(homeDir.toFile());
    }

    @Test
    void shouldSaveNewRatingWithNewImages() {
        // GIVEN
        final RatingDto rating = RatingDto.builder()
                .withRating(2)
                .withMealId(1)
                .withImages(List.of(
                        ImageDto.builder()
                                .withName(imageName + 1)
                                .withSuffix("jpg")
                                .withContent(content)
                                .build()
                ))
                .build();

        final RatingResource resource = RatingResource.of();

        // WHEN
        final int result = resource.save(rating);

        // THEN
        Assertions.assertThat(result).isGreaterThanOrEqualTo(1);
    }

    @Test
    void shouldUpdateAlreadyExistingRating() {
        // GIVEN
        final RatingDto rating = RatingDto.builder()
                .withRating(2)
                .withMealId(1)
                .withImages(List.of(
                        ImageDto.builder()
                                .withName(imageName + 2)
                                .withSuffix("jpg")
                                .withContent(content)
                                .build()
                ))
                .build();

        final RatingResource resource = RatingResource.of();
        final int id = resource.save(rating);
        final RatingDto refreshed = resource.findBy(id)
                .orElseThrow(() -> new NullPointerException("Expected refreshed rating not found"));
        refreshed.setRating(3);

        refreshed.setImages(resource.loadImagesBy(id));
        refreshed.getImages().add(ImageDto.builder()
                .withName(imageName + 3)
                .withSuffix("jpg")
                .withContent(content)
                .build());

        // WHEN
        final int result = resource.save(refreshed);

        // THEN
        Assertions.assertThat(result).isGreaterThanOrEqualTo(1);
        final Optional<RatingDto> reloaded = resource.findBy(result);
        Assertions.assertThat(reloaded).isPresent()
                .get()
                .returns(3, RatingDto::getRating)
                .returns(2, ratingDto -> ratingDto.getImages().size());
    }

    @Test
    void shouldFindByIdIfExists() {
        // GIVEN
        final RatingDto rating = RatingDto.builder()
                .withRating(2)
                .withMealId(1)
                .withImages(List.of(
                        ImageDto.builder()
                                .withName(imageName + 4)
                                .withSuffix("jpg")
                                .withContent(content)
                                .build()
                ))
                .build();

        final RatingResource resource = RatingResource.of();
        final int id = resource.save(rating);

        // WHEN
        final Optional<RatingDto> refreshed = resource.findBy(id);

        // THEN
        Assertions.assertThat(refreshed).isPresent()
                .get()
                .returns(id, RatingDto::getId)
                .returns(2, RatingDto::getRating)
                .returns(1, ratingDto -> ratingDto.getImages().size());
    }

    @Test
    void shouldFindNothingByIdIfNotExists() {
        // GIVEN
        final RatingDto rating = RatingDto.builder()
                .withRating(2)
                .withMealId(1)
                .withImages(List.of(
                        ImageDto.builder()
                                .withName(imageName + 5)
                                .withSuffix("jpg")
                                .withContent(content)
                                .build()
                ))
                .build();

        final RatingResource resource = RatingResource.of();
        resource.save(rating);

        // WHEN
        final Optional<RatingDto> result = resource.findBy(9999);

        // THEN
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    void shouldLoadImagesByRatingIdIfExists() {
        // GIVEN
        final RatingDto rating = RatingDto.builder()
                .withRating(2)
                .withMealId(1)
                .withImages(List.of(
                        ImageDto.builder()
                                .withName(imageName + 6)
                                .withSuffix("jpg")
                                .withContent(content)
                                .build()
                ))
                .build();

        final RatingResource resource = RatingResource.of();
        final int id = resource.save(rating);

        // WHEN
        final List<ImageDto> result = resource.loadImagesBy(id);

        // THEN
        Assertions.assertThat(result).isNotEmpty();
        Assertions.assertThat(result.get(0)).returns(imageName + 6, ImageDto::getName);
    }

    private static byte[] loadImage(final String fileName) {
        try {
            return IOUtils.toByteArray(Paths.get("src", "integration-test", "resources", "images", fileName).toUri());
        } catch (IOException e) {
            throw new RuntimeException("Could not load file '" + fileName + "' from 'resources/images", e);
        }
    }
}