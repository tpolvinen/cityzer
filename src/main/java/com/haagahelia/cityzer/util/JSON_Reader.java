package com.haagahelia.cityzer.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.haagahelia.cityzer.domain.LocationObject;

public class JSON_Reader {

    // source: http://www.javainterviewpoint.com/read-json-java-jsonobject-jsonarray/

    public static JSONObject weatherReader(double userLat, double userLon, String filepath)  {

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        JSONObject weatherJsonObject = null;
        JSONArray latsArraylist = null;
        JSONArray lonsArraylist = null;

        String path = filepath;

        LocationObject locationObject;

        // TODO: get these values from wherever

        int timevar = 0;
        int time_hvar = 0;
        boolean successvar;
        boolean inrangevar;
        String messagevar;
        double closestLatvar;
        double closestLonvar;


        try {
            Object object = parser.parse(new FileReader(path));

            jsonObject = (JSONObject) object;

            successvar = true;

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

            locationObject = CoordinatesHandler.finder(userLat, userLon, latsList, lonsList);

            String strLocation = locationObject.getStrLocation();
            closestLatvar = locationObject.getClosestLat();
            closestLonvar = locationObject.getClosestLon();
            inrangevar = locationObject.isInrange();
            if (locationObject.getMessage() != null && !locationObject.getMessage().isEmpty()) {
                messagevar = locationObject.getMessage();
            } else {
                messagevar = "Message from JSON_Reader.java!";
            }

            System.out.println(strLocation);
            System.out.println(messagevar);

            weatherJsonObject = (JSONObject) jsonObject.get(strLocation);


            String timeKey = "time";
            writeJsonObject(timeKey, timevar, weatherJsonObject);

            String time_hKey = "time_h";
            writeJsonObject(time_hKey, time_hvar, weatherJsonObject);

            String successvarKey = "success";
            writeJsonObject(successvarKey, successvar, weatherJsonObject);

            String inrangevarKey = "inrange";
            writeJsonObject(inrangevarKey, inrangevar, weatherJsonObject);

            String messagevarKey = "message";
            writeJsonObject(messagevarKey, messagevar, weatherJsonObject);

            String closestLatKey = "closestLat";
            writeJsonObject(closestLatKey, closestLatvar, weatherJsonObject);

            String closestLonKey = "closestLon";
            writeJsonObject(closestLonKey, closestLonvar, weatherJsonObject);



            return weatherJsonObject;

        } catch (FileNotFoundException fe) {

            jsonObject = new JSONObject();
            successvar = false;
            messagevar = "Weather data file was not found on server.";

            String successvarKey = "success";
            writeJsonObject(successvarKey, successvar, jsonObject);

            String messagevarKey = "message";
            writeJsonObject(messagevarKey, messagevar, jsonObject);

            fe.printStackTrace();
            return jsonObject;

        } catch (Exception e) {

            jsonObject = new JSONObject();
            successvar = false;
            messagevar = "Something went wrong.";

            String successvarKey = "success";
            writeJsonObject(successvarKey, successvar, jsonObject);

            String messagevarKey = "message";
            writeJsonObject(messagevarKey, messagevar, jsonObject);

            e.printStackTrace();
            return jsonObject;
        }

    }

    private static void writeJsonObject(String jsonKey, Object var, JSONObject weatherJsonObject) {

        String key = jsonKey;
        Object value = var;

        weatherJsonObject.put(key, value);

    }

}
