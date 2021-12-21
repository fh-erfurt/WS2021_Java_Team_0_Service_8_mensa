package de.fherfurt.mensa.rating.boundary;

import de.fherfurt.mensa.client.objects.PersonDto;
import de.fherfurt.mensa.core.mappers.BeanMapper;
import de.fherfurt.mensa.rating.entity.Person;
import de.fherfurt.persons.client.transfer.objects.MensaPerson;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.control.DeepClone;
import org.mapstruct.factory.Mappers;

/**
 * Mapper that allows the conversion of
 * <ul>
 *     <li>{@link Person} to/from {@link PersonDto} and</li>
 *     <li>{@link Person} from {@link MensaPerson}.</li>
 * </ul>
 *
 * @author Michael Rhoese <michael.rhoese@fh-erfurt.de>
 */
@Mapper(
        builder = @Builder(disableBuilder = true),
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        mappingControl = DeepClone.class
)
public interface PersonMapper extends BeanMapper<Person, PersonDto> {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    /**
     * Maps a {@link MensaPerson} to an internal {@link Person}.
     *
     * @param mensaPerson Mensa person to map
     * @return Resulting person
     */
    Person fromMensaPerson(MensaPerson mensaPerson);
}
