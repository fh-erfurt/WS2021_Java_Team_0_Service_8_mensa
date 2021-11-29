package de.fherfurt.campus;

import java.util.Set;

public interface CampusLocationService {

  MensaLocation findBy(Address address);

  Set<MensaLocation> findBy(String name);
}
