package de.fherfurt.mensa.rating.business;

import de.fherfurt.mensa.core.errors.BiConsumerWithException;
import de.fherfurt.mensa.rating.entity.FileRepository.FileTypes;
import de.fherfurt.mensa.rating.entity.Image;
import de.fherfurt.mensa.rating.entity.Rating;
import de.fherfurt.mensa.rating.entity.RatingRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

import static de.fherfurt.mensa.TestTags.BUSINESS;
import static de.fherfurt.mensa.TestTags.DOMAIN;
import static de.fherfurt.mensa.TestTags.UNIT;

@Tags({
        @Tag(UNIT),
        @Tag(DOMAIN),
        @Tag(BUSINESS)
})
@ExtendWith(MockitoExtension.class)
class RatingBFTest {

    @Mock
    RatingRepository ratingRepository;

    @Mock
    FilesBF filesBF;

    @Test
    void shouldCallSaveOnRatingRepository() {
        prepareTest((ratingRepoMock, filesBfMock) -> {
            // GIVEN
            RatingBF facade = RatingBF.of();
            final Rating entity = Rating.builder().build();

            // WHEN
            facade.save(entity);

            // THEN
            Mockito.verify(ratingRepository, Mockito.times(1)).save(entity);
        });
    }

    @Test
    void shouldCallSaveOnImageBf() {
        prepareTest(BiConsumerWithException.wrap((ratingRepoMock, filesBfMock) -> {
            // GIVEN
            RatingBF facade = RatingBF.of();
            final Image entity = Image.builder().withName("test").withSuffix("jpg").build();
            final byte[] content = "content".getBytes();
            final boolean newImage = true;

            // WHEN
            facade.saveImage(entity, content);

            // THEN
            Mockito.verify(filesBF, Mockito.times(1))
                    .save(FileTypes.IMAGE, entity.getName() + "." + entity.getSuffix(), content, newImage);
        }));
    }

    @Test
    void shouldLoadImageIfNoExceptionCauses() {
        prepareTest(BiConsumerWithException.wrap((ratingRepoMock, filesBfMock) -> {
            // GIVEN
            RatingBF facade = RatingBF.of();

            final int id = 1;
            final Image entity = Image.builder().withId(id).withName("test").withSuffix("jpg").build();
            final byte[] content = "content".getBytes();

            Mockito.when(ratingRepository.findImageBy(id)).thenReturn(entity);
            Mockito.when(filesBF.findBy(FileTypes.IMAGE, entity.getName() + "." + entity.getSuffix())).thenReturn(Optional.of(content));

            // WHEN
            Optional<byte[]> result = facade.loadImage(id);

            // THEN
            Mockito.verify(ratingRepository, Mockito.times(1)).findImageBy(id);
            Mockito.verify(filesBF, Mockito.times(1)).findBy(FileTypes.IMAGE, entity.getName() + "." + entity.getSuffix());
            Assertions.assertThat(result).isPresent().get().isEqualTo(content);
        }));
    }

    @Test
    void shouldLoadNoImageIfAnExceptionCauses() {
        prepareTest(BiConsumerWithException.wrap((ratingRepoMock, filesBfMock) -> {
            // GIVEN
            RatingBF facade = RatingBF.of();

            final int id = 1;
            final Image entity = Image.builder().withId(id).withName("test").withSuffix("jpg").build();

            Mockito.when(ratingRepository.findImageBy(id)).thenReturn(entity);
            Mockito.when(filesBF.findBy(FileTypes.IMAGE, entity.getName() + "." + entity.getSuffix()))
                    .thenThrow(IOException.class);

            // WHEN
            Optional<byte[]> result = facade.loadImage(id);

            // THEN
            Mockito.verify(ratingRepository, Mockito.times(1)).findImageBy(id);
            Mockito.verify(filesBF, Mockito.times(1)).findBy(FileTypes.IMAGE, entity.getName() + "." + entity.getSuffix());
            Assertions.assertThat(result).isEmpty();
        }));
    }

    @Test
    void shouldNotCallDeleteIfRatingDoesNotExist() {
        prepareTest((ratingRepoMock, filesBfMock) -> {
            // GIVEN
            RatingBF facade = RatingBF.of();

            final int id = 1;

            Mockito.when(ratingRepository.findBy(id)).thenReturn(Optional.empty());

            // WHEN
            facade.delete(id);

            // THEN
            Mockito.verify(ratingRepository, Mockito.never()).delete(Mockito.any());
        });
    }

    @Test
    void shouldCallDeleteIfRatingDoesExist() {
        prepareTest(BiConsumerWithException.wrap((ratingRepoMock, filesBfMock) -> {
            // GIVEN
            RatingBF facade = RatingBF.of();

            final int id = 1;
            final Rating entity = Rating.builder()
                    .withImages(List.of(
                            Image.builder().withId(1).build(),
                            Image.builder().withId(2).build(),
                            Image.builder().withId(3).build()
                    ))
                    .build();

            Mockito.when(ratingRepository.findBy(id)).thenReturn(Optional.of(entity));

            // WHEN
            facade.delete(id);

            // THEN
            Mockito.verify(ratingRepository, Mockito.times(1)).delete(entity);
            Mockito.verify(filesBF, Mockito.times(entity.getImages().size())).delete(Mockito.eq(FileTypes.IMAGE), Mockito.any(String.class));
        }));
    }

    private void prepareTest(BiConsumer<MockedStatic<RatingRepository>, MockedStatic<FilesBF>> testCase) {
        try (MockedStatic<RatingRepository> ratingRepo = Mockito.mockStatic(RatingRepository.class)) {
            // GIVEN
            ratingRepo.when(RatingRepository::of).thenReturn(ratingRepository);

            try (MockedStatic<FilesBF> filesFacade = Mockito.mockStatic(FilesBF.class)) {
                // GIVEN
                filesFacade.when(FilesBF::of).thenReturn(filesBF);

                testCase.accept(ratingRepo, filesFacade);
            }
        }
    }
}