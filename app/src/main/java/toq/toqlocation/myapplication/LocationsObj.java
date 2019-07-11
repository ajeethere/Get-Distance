package toq.toqlocation.myapplication;

public class LocationsObj {
    double longitude,latitude;
    String name;

    public LocationsObj(double longitude, double latitude, String name) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.name=name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
