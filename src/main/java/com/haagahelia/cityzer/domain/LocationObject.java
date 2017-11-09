package com.haagahelia.cityzer.domain;

//import org.springframework.stereotype.Component;

//@Component
public class LocationObject {

    private String strLocation;
    private double closestLat;
    private double closestLon;

    public LocationObject(String strLocation, double closestLat, double closestLon) {
        this.strLocation = strLocation;
        this.closestLat = closestLat;
        this.closestLon = closestLon;
    }

    public String getStrLocation() {
        return strLocation;
    }

    public void setStrLocation(String strLocation) {
        this.strLocation = strLocation;
    }

    public double getClosestLat() {
        return closestLat;
    }

    public void setClosestLat(double closestLat) {
        this.closestLat = closestLat;
    }

    public double getClosestLon() {
        return closestLon;
    }

    public void setClosestLon(double closestLon) {
        this.closestLon = closestLon;
    }

    @Override
    public String toString() {
        return "LocationObject{" +
                "strLocation='" + strLocation + '\'' +
                ", closestLat=" + closestLat +
                ", closestLon=" + closestLon +
                '}';
    }
}
