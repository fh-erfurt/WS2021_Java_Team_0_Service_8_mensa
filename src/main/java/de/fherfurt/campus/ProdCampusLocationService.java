package de.fherfurt.campus;

import java.util.HashSet;
import java.util.Set;

public class ProdCampusLocationService implements CampusLocationService{
  @Override
  public MensaLocation findBy(Address address) {
    return new MensaLocation(3.2, 4.2);
  }

  @Override
  public Set<MensaLocation> findBy(String name) {
    return new HashSet<>();
  }
}
