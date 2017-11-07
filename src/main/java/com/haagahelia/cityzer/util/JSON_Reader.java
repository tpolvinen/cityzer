package com.haagahelia.cityzer.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

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
        double[] latitudes = null;
        double[] longitudes = null;

        try {
            Object object = parser.parse(new FileReader(path));

            jsonObject = (JSONObject) object;

            JSONArray latsArray = (JSONArray)jsonObject.get("lats");
            JSONArray lonsArray = (JSONArray)jsonObject.get("lons");

            // TODO: How to convert JSONArray arrays/objects to double[] arrays?

            // double[] latitudes = latsArray; // THIS DOES NOT WORK!!!
            // double[] latitudes = toDoubleArray(latsArray);
            // double[] longitudes = toDoubleArray(lonsArray);


            ArrayList<Double> latsList = new ArrayList<Double>();
            JSONArray latsJsonArray = (JSONArray)jsonObject.get("lats");
            if (latsJsonArray != null) {
                int len = latsJsonArray.size();
                for (int i=0;i<len;i++){
                    latsList.add(Double.parseDouble(latsJsonArray.get(i).toString()));
                }
            }

            ArrayList<Double> lonsList = new ArrayList<Double>();
            JSONArray lonsJsonArray = (JSONArray)jsonObject.get("lons");
            if (lonsJsonArray != null) {
                int len = lonsJsonArray.size();
                for (int i=0;i<len;i++){
                    lonsList.add(Double.parseDouble(lonsJsonArray.get(i).toString()));
                }
            }


            String strLocation = CoordinatesHandler.finder(userLat, userLon, latsList, lonsList);

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
