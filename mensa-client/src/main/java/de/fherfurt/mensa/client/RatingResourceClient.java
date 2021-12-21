package de.fherfurt.mensa.client;

import de.fherfurt.mensa.client.objects.ImageDto;
import de.fherfurt.mensa.client.objects.RatingDto;

import java.util.List;
import java.util.Optional;

/**
 * This interface defines the provided functionality for ratings in the <i>MENSA-Service</i>. It should be used by other
 * services and contains the connection related settings in the future.
 *
 * @author Michael Rhoese <michael.rhoese@fh-erfurt.de>
 */
public interface RatingResourceClient {

    /**
     * Takes a {@link RatingDto} and saves them for the given user. The related objects are persisted too. If a
     * {@link RatingDto} is already persisted, the version replaces the existing one.
     *
     * @param ratingDto Instance to save
     * @param userAlias Alias of user that creates the rating
     * @return The ID of the saved rating
     */
    int save(final RatingDto ratingDto, String userAlias);

    /**
     * Takes the id of a {@link RatingDto} and search for it. If the corresponding {@link RatingDto} is found, it will be
     * returned. If no {@link RatingDto} matches the id, an empty {@link Optional} is returned.
     *
     * @param id ID of the searched rating
     * @return The Rating or empty
     */
    Optional<RatingDto> findBy(int id);

    /**
     * Takes the ID of a {@link RatingDto} and loads all related {@link ImageDto}. These {@link ImageDto}s contain the
     * images as byte arrays.
     *
     * @param ratingId ID of the rating for which the images must be loaded
     * @return List of matching images
     */
    List<ImageDto> loadImagesBy(int ratingId);

    /**
     * Takes the ID of a {@link RatingDto} and deletes it and all its dependent objects like {@link ImageDto}s.
     *
     * @param id The ID of the rating that must be deleted
     */
    void deleteBy(int id);
}
