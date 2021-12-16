package de.fherfurt.campus;

import de.fherfurt.campus.transfer.objects.Address;
import de.fherfurt.campus.transfer.objects.MensaLocation;

import java.util.Set;

public interface CampusLocationService {

    MensaLocation findBy(Address address);

    Set<MensaLocation> findBy(String name);
}
