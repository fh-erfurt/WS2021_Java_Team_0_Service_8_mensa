package de.fherfurt;

import de.fherfurt.campus.CampusLocationService;
import de.fherfurt.campus.DevCampusLocationService;
import de.fherfurt.mensa.MensaService;

public class Main {
    public static void main(String[] args) {
        CampusLocationService locationService = new DevCampusLocationService();

        MensaService service = new MensaService(locationService);

        System.out.println(service.getLocationsAsString("MenSa"));
    }
}
