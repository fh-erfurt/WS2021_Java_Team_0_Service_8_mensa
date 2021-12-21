package de.fherfurt.mensa.rating.boundary;

import de.fherfurt.mensa.client.RatingResourceClient;
import de.fherfurt.mensa.client.objects.ImageDto;
import de.fherfurt.mensa.client.objects.RatingDto;
import de.fherfurt.mensa.core.errors.FunctionWithException;
import de.fherfurt.mensa.core.mappers.BeanMapper;
import de.fherfurt.mensa.rating.business.RatingBF;
import de.fherfurt.mensa.rating.entity.Rating;
import de.fherfurt.persons.client.DevPersonsService;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static de.fherfurt.mensa.core.errors.ConsumerWithException.wrap;

/**
 * This class represents the production implementation for the functionality of the rating submodule. It receives and loads
 * all data that are required to store new ratings and provides search and load capability for external services.
 *
 * @author Michael Rhoese <michael.rhoese@fh-erfurt.de>
 */
@NoArgsConstructor(staticName = "of")
public class RatingResource implements RatingResourceClient {

    /**
     * TODO: Remove {@link de.fherfurt.persons.client.DevPersonsService} before release!
     */
    private final RatingBF ratingBF = RatingBF.of(new DevPersonsService());

    /**
     * {@inheritDoc}
     */
    @Override
    public int save(final RatingDto ratingDto, final String userAlias) {
        final Rating rating = BeanMapper.mapFromDto(ratingDto);

        ratingBF.save(rating, userAlias);

        ratingDto.getImages()
                .forEach(wrap(
                        image -> ratingBF.saveImage(rating.getImages().get(ratingDto.getImages().indexOf(image)), image.getContent())
                ));

        return rating.getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<RatingDto> findBy(final int id) {
        return ratingBF.findBy(id)
                .map(rating -> (RatingDto) BeanMapper.mapToDto(rating))
                .or(Optional::empty);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ImageDto> loadImagesBy(final int ratingId) {
        return ratingBF.findBy(ratingId)
                .map(rating -> rating.getImages().stream()
                        .map(FunctionWithException.wrap(
                                        image -> ImageDto.builder()
                                                .withId(image.getId())
                                                .withName(image.getName())
                                                .withSuffix(image.getSuffix())
                                                .withContent(ratingBF.loadImage(image.getId()).orElse(null))
                                                .build()
                                )
                        )
                        .collect(Collectors.toList())
                ).orElse(Collections.emptyList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteBy(final int id) {
        ratingBF.delete(id);
    }
}
