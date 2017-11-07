package com.haagahelia.cityzer.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.haagahelia.cityzer.util.CoordinatesHandler;

public class JSON_Reader {

    // source: http://www.javainterviewpoint.com/read-json-java-jsonobject-jsonarray/

    public static JSONObject weatherReader(double userLat, double userLon, String filepath)  {

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        JSONObject weatherJsonObject = null;
        JSONArray latsArraylist = null;
        JSONArray lonsArraylist = null;

        String path = filepath;

        try {
            Object object = parser.parse(new FileReader(path));

            jsonObject = (JSONObject) object;

            // TODO: refactor these to a single method:
            // source https://stackoverflow.com/questions/41016764/parsing-nested-json-array-in-java

            JSONArray jsonLats = (JSONArray) jsonObject.get("lats");

            for(Object latsObject: jsonLats.toArray()){
                JSONObject latsJsonStorage = (JSONObject) latsObject;
                latsArraylist = (JSONArray) latsJsonStorage.get("storage");
                System.out.println(latsArraylist);
            }

            JSONArray jsonLons = (JSONArray) jsonObject.get("lons");

            for(Object lonsObject: jsonLons.toArray()){
                JSONObject lonsJsonStorage = (JSONObject) lonsObject;
                lonsArraylist = (JSONArray) lonsJsonStorage.get("storage");
                System.out.println(lonsArraylist);
            }

            ArrayList<Double> latsList = new ArrayList<Double>();
            if (latsArraylist != null) {
                int len = latsArraylist.size();
                for (int i=0;i<len;i++){
                    latsList.add(Double.parseDouble(latsArraylist.get(i).toString()));
                }
            }

            ArrayList<Double> lonsList = new ArrayList<Double>();
            if (lonsArraylist != null) {
                int len = lonsArraylist.size();
                for (int i=0;i<len;i++){
                    lonsList.add(Double.parseDouble(lonsArraylist.get(i).toString()));
                }
            }

            String strLocation = CoordinatesHandler.finder(userLat, userLon, latsList, lonsList);
            System.out.println(strLocation);

            //strLocation = userLat + " " + userLon;

            // TODO: find out why this SOMETIMES returns null weatherJsonObject
            // TODO: ...like http://localhost:8080/api/getWeather?time=0&userLat=59.1&userLon=24.96599006652832 IS FINE!
            // TODO: This returns null WeatherObject, but strLocation is ok: 60.37255 25.64626  http://localhost:8080/api/getWeather?time=0&userLat=60.39621860539766&userLon=25.662238597869873
            weatherJsonObject = (JSONObject) jsonObject.get(strLocation);
            System.out.println(weatherJsonObject);

        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return weatherJsonObject;
    }

}
