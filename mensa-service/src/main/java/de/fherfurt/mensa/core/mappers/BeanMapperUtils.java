package de.fherfurt.mensa.core.mappers;

import de.fherfurt.mensa.core.containers.Tuple3;
import de.fherfurt.mensa.rating.boundary.RatingMapper;
import de.fherfurt.mensa.rating.boundary.transfer.objects.RatingDto;
import de.fherfurt.mensa.rating.entity.Rating;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Utils class with methods and data that are required to work with bean mapper. Instances of mappers have to registered
 * in the MAPPERS list of that class.
 */
public class BeanMapperUtils {
    /**
     * Searches a mapper instance by the given type and the Tuple3 supplier. The mappers must be pre-defined in the list of that utils class.
     * If no mapper is available for the type, an exception will be thrown.
     *
     * @param type         Type of the object
     * @param typeSupplier The supplier of the tuple, that is used to get the comparison value from the map entries.
     * @param <E>          Generic type of the entity
     * @param <D>          Generic type of the DTO
     * @return Instance of the mapper. Otherwise, an exception will be thrown.
     */
    @SuppressWarnings("unchecked")
    static <E, D> BeanMapper<E, D> getMapperBy(Class<?> type, final Function<Tuple3<Class<?>, Class<?>, ? extends BeanMapper<?, ?>>, Class<?>> typeSupplier) {
        return MAPPERS.stream()
                .filter(mapper -> Objects.equals(type, typeSupplier.apply(mapper)))
                .findFirst()
                .map(mapper -> (BeanMapper<E, D>) mapper.getV3())
                .orElseThrow(() -> new NullPointerException("Could not find mapper for DTO type '" + type.getSimpleName() + "'."));
    }

    /**
     * List that holds all available mappers.
     */
    private static final List<Tuple3<Class<?>, Class<?>, ? extends BeanMapper<?, ?>>> MAPPERS = List.of(
            Tuple3.<Class<?>, Class<?>, BeanMapper<?, ?>>builder()
                    .withV1(Rating.class)
                    .withV2(RatingDto.class)
                    .withV3(RatingMapper.INSTANCE)
                    .build()
    );
}
