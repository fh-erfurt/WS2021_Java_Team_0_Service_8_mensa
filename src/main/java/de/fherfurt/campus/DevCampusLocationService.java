package de.fherfurt.campus;

import de.fherfurt.campus.transfer.objects.Address;
import de.fherfurt.campus.transfer.objects.MensaLocation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DevCampusLocationService implements CampusLocationService {

    private final Map<String, MensaLocation> locations = new HashMap<>() {{
        put("Mensa FH Erfurt", new MensaLocation(1.1, 1.2));
        put("Mensa Uni Erfurt", new MensaLocation(2.1, 2.2));
        put("Weimarische Str.", new MensaLocation(-1, -1));
    }};

    @Override
    public MensaLocation findBy(Address address) {

        return switch (address.getStreet()) {
            case "Altonaer Str." -> locations.get("Mensa FH Erfurt");
            case "Weimarische Str." -> locations.get("Weimarische Str.");
            default -> locations.get("Mensa Uni Erfurt");
        };
    }

    @Override
    public Set<MensaLocation> findBy(String name) {

        Set<MensaLocation> res = new HashSet<>();

        for (Map.Entry<String, MensaLocation> entry : locations.entrySet()) {
            if (entry.getKey().toLowerCase().contains(name.toLowerCase())) {
                res.add(entry.getValue());
            }
        }

        return res;
    }
}
