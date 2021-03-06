package de.fherfurt.persons.client;

import de.fherfurt.persons.client.transfer.objects.MensaPerson;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DevPersonsService implements PersonsService {

    private final List<MensaPerson> persons = Arrays.asList(
            new MensaPerson(1, "user1", null),
            new MensaPerson(2, "user2", null),
            new MensaPerson(3, "user3", null),
            new MensaPerson(4, "user4", null)
    );

    @Override
    public Optional<MensaPerson> findBy(int id) {
        return Optional.ofNullable(persons.get(id));
    }

    @Override
    public Optional<MensaPerson> findBy(final String alias) {
        return persons.stream().filter(user -> Objects.equals(user.getAlias(), alias)).findFirst();
    }
}