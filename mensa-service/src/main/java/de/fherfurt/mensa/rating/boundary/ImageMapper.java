package de.fherfurt.mensa.rating.boundary;

import de.fherfurt.mensa.client.objects.ImageDto;
import de.fherfurt.mensa.core.mappers.BeanMapper;
import de.fherfurt.mensa.rating.entity.Image;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.control.DeepClone;
import org.mapstruct.factory.Mappers;

/**
 * Mapper that allows the conversion of {@link Image} to/from {@link ImageDto}.
 *
 * @author Michael Rhoese <michael.rhoese@fh-erfurt.de>
 */
@Mapper(
        builder = @Builder(disableBuilder = true),
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        mappingControl = DeepClone.class
)
public interface ImageMapper extends BeanMapper<Image, ImageDto> {
    ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);
}
