package com.haagahelia.cityzer.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CoordinatesHandler {

    public static String finder(double userLat, double userLon, ArrayList<Double> latsArray, ArrayList<Double> lonsArray) {

        double closestLat = closest(userLat,latsArray);
        double closestLon = closest(userLon,lonsArray);

        String strLat = Double.toString(closestLat);
        String strLon = Double.toString(closestLon);

        String strLocation = strLat + " " + strLon;

        return strLocation;
    }

    // source: https://stackoverflow.com/questions/1187352/find-closest-value-in-an-ordererd-list
    // refactoring from int to double
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