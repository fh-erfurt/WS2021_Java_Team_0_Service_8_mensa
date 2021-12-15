package de.fherfurt.campus.transfer.objects;

public class MensaLocation {

    private double lon;
    private double lat;

    public MensaLocation(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "Lat: " + this.lat + "|Lon: " + this.lon + "\n";
    }
}
