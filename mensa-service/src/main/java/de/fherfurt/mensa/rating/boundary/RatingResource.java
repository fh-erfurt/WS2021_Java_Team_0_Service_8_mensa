package de.fherfurt.mensa.rating.boundary;

import de.fherfurt.mensa.core.errors.FunctionWithException;
import de.fherfurt.mensa.core.mappers.BeanMapper;
import de.fherfurt.mensa.rating.boundary.transfer.objects.ImageDto;
import de.fherfurt.mensa.rating.boundary.transfer.objects.RatingDto;
import de.fherfurt.mensa.rating.business.RatingBF;
import de.fherfurt.mensa.rating.entity.Rating;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static de.fherfurt.mensa.core.errors.ConsumerWithException.wrap;

public class RatingResource {

    private RatingBF ratingBF;

    public void save(final RatingDto ratingDto) {
        final Rating rating = BeanMapper.mapFromDto(ratingDto);

        ratingBF.save(rating);

        ratingDto.getImages()
                .forEach(wrap(
                        image -> ratingBF.saveImage(rating.getImages().get(ratingDto.getImages().indexOf(image)), image.getContent())
                ));
    }

    public Optional<RatingDto> findBy(int id) {
        return ratingBF.findBy(id)
                .map(rating -> (RatingDto) BeanMapper.mapToDto(rating))
                .or(Optional::empty);
    }

    public List<ImageDto> loadImagesBy(int ratingId) {
        return ratingBF.findBy(ratingId)
                .map(rating -> rating.getImages().stream()
                        .map(FunctionWithException.wrap(
                                        image -> ImageDto.builder()
                                                .withId(image.getId())
                                                .withTitle(image.getTitle())
                                                .withType(image.getType())
                                                .withContent(ratingBF.loadImage(image.getId()).orElse(null))
                                                .build()
                                )
                        )
                        .collect(Collectors.toList())
                ).orElse(Collections.emptyList());
    }
}
