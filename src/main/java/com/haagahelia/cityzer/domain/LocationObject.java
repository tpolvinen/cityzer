package com.haagahelia.cityzer.domain;

//import org.springframework.stereotype.Component;

//@Component
public class LocationObject {

    private String strLocation;
    private double closestLat;
    private double closestLon;
    private boolean inrange;
    private String message;

    public LocationObject(String strLocation, double closestLat, double closestLon, boolean inrange, String message) {
        this.strLocation = strLocation;
        this.closestLat = closestLat;
        this.closestLon = closestLon;
        this.inrange = inrange;
        this.message = message;
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

    public boolean isInrange() {
        return inrange;
    }

    public void setInrange(boolean inrange) {
        this.inrange = inrange;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "LocationObject{" +
                "strLocation='" + strLocation + '\'' +
                ", closestLat=" + closestLat +
                ", closestLon=" + closestLon +
                ", inrange=" + inrange +
                ", message='" + message + '\'' +
                '}';
    }
}
