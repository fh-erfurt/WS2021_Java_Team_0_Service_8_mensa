package de.fherfurt.persons.client;

import de.fherfurt.persons.client.transfer.objects.MensaPerson;

import java.util.Optional;

public interface PersonsService {

    Optional<MensaPerson> findBy(int id);

    Optional<MensaPerson> findBy(String alias);
}
