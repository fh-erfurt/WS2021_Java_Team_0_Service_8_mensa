package de.fherfurt.mensa.rating.boundary;

import de.fherfurt.mensa.core.mappers.BeanMapper;
import de.fherfurt.mensa.rating.boundary.transfer.objects.ImageDto;
import de.fherfurt.mensa.rating.entity.Image;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true),
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ImageMapper extends BeanMapper<Image, ImageDto> {
    ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);
}
