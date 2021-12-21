package de.fherfurt.mensa.rating.boundary;

import de.fherfurt.mensa.core.mappers.BeanMapper;
import de.fherfurt.mensa.rating.boundary.transfer.objects.RatingDto;
import de.fherfurt.mensa.rating.entity.Rating;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.control.DeepClone;
import org.mapstruct.factory.Mappers;

@Mapper(
        builder = @Builder(disableBuilder = true),
        uses = ImageMapper.class,
        mappingControl = DeepClone.class
)
public interface RatingMapper extends BeanMapper<Rating, RatingDto> {
    RatingMapper INSTANCE = Mappers.getMapper(RatingMapper.class);

    RatingDto clone(RatingDto toClone);
}
