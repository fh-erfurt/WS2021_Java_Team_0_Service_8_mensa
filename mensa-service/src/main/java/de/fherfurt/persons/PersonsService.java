package de.fherfurt.persons;

import de.fherfurt.persons.transfer.objects.MensaPerson;

import java.util.Optional;

public interface PersonsService {

    Optional<MensaPerson> findBy(int id);
}
