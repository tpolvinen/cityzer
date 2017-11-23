package com.haagahelia.cityzer.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;

import com.haagahelia.cityzer.domain.LocationObject;

public class CoordinatesHandler {

    public static LocationObject finder(double userLat, double userLon, ArrayList<Double> latsArray, ArrayList<Double> lonsArray) {


        double closestLat = closest(userLat,latsArray);
        double closestLon = closest(userLon,lonsArray);
        double latMax;
        double latMin;
        double lonMax;
        double lonMin;
        boolean inrange = true;
        String message = null;

        String strLat = Double.toString(closestLat);
        String strLon = Double.toString(closestLon);

        String strLocation = strLat + " " + strLon;

        latMax = Collections.max(latsArray);
        latMin = Collections.min(latsArray);
        lonMax = Collections.max(lonsArray);
        lonMin = Collections.min(lonsArray);

        double latMargin = ((latMax - latMin) / latsArray.size()) / 2;
        double lonMargin = ((lonMax - lonMin) / lonsArray.size()) / 2;

        System.out.println("CoordinatesHandler method finder starts");


        if (userLat + latMargin < latMin || latMax < userLat - latMargin || userLon + lonMargin < lonMin || lonMax < userLon - lonMargin) {
            message = "NOT IN RANGE! Too far ";
            inrange = false;
        }

        if (userLat + latMargin < latMin) {
            message = message + "south";
        }
        if (latMax < userLat - latMargin) {
            message = message + "north";
        }
        if (userLon + lonMargin < lonMin) {
            message = message + "west";
        }
        if (lonMax < userLon - lonMargin) {
            message = message + "east";
        }
        if (message != null && !message.isEmpty()) {
            message = message + ".";
        }

        LocationObject locationObject = new LocationObject(strLocation, closestLat, closestLon, inrange, message);

        return locationObject;
    }

    // source: https://stackoverflow.com/questions/1187352/find-closest-value-in-an-ordererd-list
    // refactored from int to double
    public static double closest(double of, List<Double> in) {
        double min = Double.MAX_VALUE;
        double closest = of;

        for (double v : in) {
            final double diff = Math.abs(v - of);

            if (diff < min) {
                min = diff;
                closest = v;
            }
        }

        return closest;
    }

}