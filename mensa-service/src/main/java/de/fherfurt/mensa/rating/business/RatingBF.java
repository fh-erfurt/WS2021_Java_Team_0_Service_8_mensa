package de.fherfurt.mensa.rating.business;

import de.fherfurt.mensa.core.persistence.Repository;
import de.fherfurt.mensa.rating.entity.Rating;
import de.fherfurt.mensa.rating.entity.RatingRepository;

import java.util.Optional;

public class RatingBF {

    private final Repository<Rating> ratingRepository = RatingRepository.of();
    private final FilesBF filesBF = new FilesBF();

    public void save(final Rating rating) {
        ratingRepository.save(rating);
    }

    public Optional<Rating> findBy(final int id) {
        return ratingRepository.findBy(id);
    }
}
