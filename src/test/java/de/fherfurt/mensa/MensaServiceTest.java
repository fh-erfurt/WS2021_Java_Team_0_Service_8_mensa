package de.fherfurt.mensa;

import de.fherfurt.campus.CampusLocationService;
import de.fherfurt.campus.transfer.objects.MensaLocation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

class MensaServiceTest {

  @Test
  void shouldReturnStringForNonEmptyList(){
    // GIVEN
    String name = "MenSa";
    CampusLocationService locationService = Mockito.mock(CampusLocationService.class);

    Set<MensaLocation> locations = new HashSet<>(){{
      add(new MensaLocation(1.1, 1.2));
      add(new MensaLocation(2.1, 2.2));
    }};

    Mockito.when(locationService.findBy(name)).thenReturn(locations);

    MensaService mensaService = new MensaService(locationService);

    // WHEN
    String result = mensaService.getLocationsAsString(name);

    // THEN
    Assertions.assertThat(result).contains("Lat: 1.2|Lon: 1.1", "Lat: 2.2|Lon: 2.1");
  }
}
