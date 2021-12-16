package de.fherfurt.campus.client;

import de.fherfurt.campus.client.transfer.objects.Address;
import de.fherfurt.campus.client.transfer.objects.MensaLocation;

import java.util.Set;

public interface CampusLocationService {

    MensaLocation findBy(Address address);

    Set<MensaLocation> findBy(String name);
}
