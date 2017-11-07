package com.haagahelia.cityzer.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.haagahelia.cityzer.util.CoordinatesHandler;

public class JSON_Reader {

    // source: http://www.javainterviewpoint.com/read-json-java-jsonobject-jsonarray/

    public static JSONObject weatherReader(double userLat, double userLon, String filepath)  {

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        JSONObject weatherJsonObject = null;

        String path = filepath;

        String latsOrLons = null;
        double[] doubleLatsLons = null;

        try {
            Object object = parser.parse(new FileReader(path));

            jsonObject = (JSONObject) object;

            JSONArray latsArray = (JSONArray)jsonObject.get("lats");
            JSONArray lonsArray = (JSONArray)jsonObject.get("lons");

            // TODO: How to convert JSONArray arrays/objects to double[] arrays?

            double[] latitudes = latsArray; // THIS DOES NOT WORK!!!
            double[] longitudes = lonsArray;

            String strLocation = CoordinatesHandler.finder(userLat, userLon, latitudes, longitudes);

            //strLocation = userLat + " " + userLon;

            // TODO: get the weatherJsonObject with strLocation

        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return weatherJsonObject;
    }

}
