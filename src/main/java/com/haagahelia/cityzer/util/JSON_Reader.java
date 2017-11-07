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

        String strLocation = null;
        String path = filepath;

        double[] latsArray = null;
        double[] lonsArray = null;

        String latsOrLons = null;
        double[] doubleLatsLons = null;

        try {
            Object object = parser.parse(new FileReader(path));

            jsonObject = (JSONObject) object;

            //strLocation = userLat + " " + userLon;

            latsOrLons = "lats";

            latsArray = ArrayReader(latsOrLons);

            latsOrLons = "lons";

            lonsArray = ArrayReader(latsOrLons);

            CoordinatesHandler.finder(userLat, userLon, latsArray, lonsArray);



        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return weatherJsonObject;
    }

    static double[] ArrayReader(String latsOrLons) {

        double[] doubleLatsLons = null;

        //Reading the array
        JSONArray jsonLatsLons = (JSONArray)jsonObject.get(latsOrLons);


        // TODO: cast (or something?) JSONArray jsonLatsLons to double[] doubleLatsLons
        
        return doubleLatsLons;
    }

}
