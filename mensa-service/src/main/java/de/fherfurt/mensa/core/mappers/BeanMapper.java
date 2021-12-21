package de.fherfurt.mensa.core.mappers;

import de.fherfurt.mensa.core.containers.Tuple3;
import de.fherfurt.mensa.core.persistence.BaseBusinessEntity;
import de.fherfurt.mensa.rating.boundary.RatingMapper;
import org.mapstruct.InheritInverseConfiguration;

/**
 * This interface represents an API definition for bean mappers and a mapper facade at the same time. An example can be found in {@link RatingMapper}.
 * The direct usage of the mapper framework isn't a good idea. Sometimes, the performance of frameworks change and the switch to another makes sense.
 *
 * @param <E> Generic type of entity
 * @param <D> Generic type of DTO
 * @author Michael Rhoese <michael.rhoese@fh-erfurt.de>
 */
public interface BeanMapper<E extends BaseBusinessEntity, D> {

    /**
     * Maps the given entity to its corresponding DTO.
     *
     * @param entity Entity to map
     * @return Resulting DTO of the mapping
     */
    D toDto(final E entity);

    /**
     * Maps the given DTO to its underlying entity.
     *
     * @param dto DTO to map
     * @return Resulting entity of the DTO
     */
    @InheritInverseConfiguration
    E fromDto(final D dto);

    /**
     * Deeply clones the entity.
     *
     * @param toClone Entity to clone
     * @return Cloned entity
     */
    E clone(E toClone);

    /**
     * Deeply clones the DTO.
     *
     * @param toClone DTO to clone
     * @return Cloned DTO
     */
    D clone(D toClone);

    @Ignore
    static <E extends BaseBusinessEntity, D> D mapToDto(final E entity) {
        final BeanMapper<E, D> beanMapper = BeanMapperUtils.getMapperBy(entity.getClass(), Tuple3::getV1);
        return beanMapper.toDto(entity);
    }

    @Ignore
    static <E extends BaseBusinessEntity, D> E mapFromDto(final D dto) {
        final BeanMapper<E, D> beanMapper = BeanMapperUtils.getMapperBy(dto.getClass(), Tuple3::getV2);
        return beanMapper.fromDto(dto);
    }
}
