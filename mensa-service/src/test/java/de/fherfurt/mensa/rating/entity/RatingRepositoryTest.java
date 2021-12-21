package de.fherfurt.mensa.rating.entity;

import de.fherfurt.mensa.core.persistence.Database;
import de.fherfurt.mensa.core.persistence.errors.NoResultException;
import de.fherfurt.mensa.core.persistence.errors.ToManyResultsException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import static de.fherfurt.mensa.TestTags.DOMAIN;
import static de.fherfurt.mensa.TestTags.PERSISTENCE;
import static de.fherfurt.mensa.TestTags.UNIT;

@Tags({
        @Tag(UNIT),
        @Tag(DOMAIN),
        @Tag(PERSISTENCE)
})
@ExtendWith(MockitoExtension.class)
class RatingRepositoryTest {

    @Mock
    Database database;

    @SuppressWarnings("unchecked")
    @Test
    void shouldThrowExceptionIfImageCouldNotBeFound() {
        try (MockedStatic<Database> db = Mockito.mockStatic(Database.class)) {
            // GIVEN
            db.when(Database::newInstance).thenReturn(database);

            final RatingRepository repository = RatingRepository.of();

            Mockito
                    .when(database.findBy(Mockito.eq(Image.class), Mockito.any(Predicate.class)))
                    .thenReturn(Collections.emptyList());

            // WHEN
            final Throwable result = Assertions.catchThrowable(() -> repository.findImageBy(1));

            // THE
            Assertions
                    .assertThat(result)
                    .isInstanceOf(NoResultException.class)
                    .hasMessage("Could not found image with id [1]");
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    void shouldThrowExceptionIfToManyImagesAreFound() {
        try (MockedStatic<Database> db = Mockito.mockStatic(Database.class)) {
            // GIVEN
            db.when(Database::newInstance).thenReturn(database);

            final RatingRepository repository = RatingRepository.of();

            Mockito
                    .when(database.findBy(Mockito.eq(Image.class), Mockito.any(Predicate.class)))
                    .thenReturn(List.of(Image.builder().withId(1).build(), Image.builder().withId(2).build()));

            // WHEN
            final Throwable result = Assertions.catchThrowable(() -> repository.findImageBy(1));

            // THE
            Assertions
                    .assertThat(result)
                    .isInstanceOf(ToManyResultsException.class)
                    .hasMessage("No unique result found for id [1]");
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    void shouldReturnFoundImageIfItIsTheOnlyFound() {
        try (MockedStatic<Database> db = Mockito.mockStatic(Database.class)) {
            // GIVEN
            db.when(Database::newInstance).thenReturn(database);

            final RatingRepository repository = RatingRepository.of();

            Mockito
                    .when(database.findBy(Mockito.eq(Image.class), Mockito.any(Predicate.class)))
                    .thenReturn(List.of(Image.builder().withId(1).build()));

            // WHEN
            final Image result = repository.findImageBy(1);

            // THE
            Assertions
                    .assertThat(result)
                    .isNotNull();
        }
    }

    @Test
    void shouldCallSaveEntityIfSaveRatingIsCalled() {
        try (MockedStatic<Database> db = Mockito.mockStatic(Database.class)) {
            // GIVEN
            db.when(Database::newInstance).thenReturn(database);

            final RatingRepository repository = RatingRepository.of();

            final Rating entity = Rating.builder().build();

            // WHEN
            repository.save(entity);

            // THE
            Mockito.verify(database, Mockito.times(1)).save(entity);
        }
    }

    @Test
    void shouldCallDeleteEntityIfDeleteRatingIsCalled() {
        try (MockedStatic<Database> db = Mockito.mockStatic(Database.class)) {
            // GIVEN
            db.when(Database::newInstance).thenReturn(database);

            final RatingRepository repository = RatingRepository.of();

            final Rating entity = Rating.builder().build();

            // WHEN
            repository.delete(entity);

            // THE
            Mockito.verify(database, Mockito.times(1)).delete(entity);
        }
    }
}