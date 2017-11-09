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

        String strLat = Double.toString(closestLat);
        String strLon = Double.toString(closestLon);

        String strLocation = strLat + " " + strLon;

        LocationObject locationObject = new LocationObject(strLocation, closestLat, closestLon);

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