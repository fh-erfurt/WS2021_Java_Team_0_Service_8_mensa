package de.fherfurt.mensa.rating.boundary;

import de.fherfurt.mensa.core.mappers.BeanMapper;
import de.fherfurt.mensa.rating.boundary.transfer.objects.RatingDto;
import de.fherfurt.mensa.rating.entity.Rating;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RatingMapper extends BeanMapper<Rating, RatingDto> {

    RatingMapper INSTANCE = Mappers.getMapper(RatingMapper.class);

    @Override
    RatingDto toDto(Rating rating);

    @Override
    @InheritInverseConfiguration
    Rating fromDto(RatingDto ratingDO);
}
