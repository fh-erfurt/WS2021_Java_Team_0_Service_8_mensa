package de.fherfurt.mensa;

import de.fherfurt.campus.CampusLocationService;
import de.fherfurt.campus.MensaLocation;

import java.util.Set;

public class MensaService {

  private final CampusLocationService locationService;

  public MensaService(CampusLocationService locationService) {
    this.locationService = locationService;
  }

  public String getLocationsAsString(final String name) {

    Set<MensaLocation> locations = locationService.findBy(name);

    StringBuilder res = new StringBuilder();
    for (MensaLocation location : locations) {
      res.append(location);
    }

    return res.toString();
  }
}
