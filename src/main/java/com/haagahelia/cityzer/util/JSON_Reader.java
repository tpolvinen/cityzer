package com.haagahelia.cityzer.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import com.haagahelia.cityzer.domain.WeatherObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

import com.haagahelia.cityzer.util.CoordinatesHandler;
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

            //inrangevar = true;

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

            // TODO: refactor these to a single method:

            //String timeKey = "time";
            //Integer timeValue = timevar;
            //weatherJsonObject.put(timeKey, timeValue);

            //String time_hKey = "time_h";
            //Integer time_hValue = time_hvar;
            //weatherJsonObject.put(time_hKey, time_hValue);

            //String successvarKey = "success";
            //Boolean successvarValue = successvar;
            //weatherJsonObject.put(successvarKey, successvarValue);

            //String inrangevarKey = "inrange";
            //Boolean inrangevarValue = inrangevar;
            //weatherJsonObject.put(inrangevarKey, inrangevarValue);

            //String messagevarKey = "message";
            //String messagevarValue = messagevar;
            //weatherJsonObject.put(messagevarKey, messagevarValue);

            //String closestLatKey = "closestLat";
            //Double closestLatValue = closestLatvar;
            //weatherJsonObject.put(closestLatKey, closestLatValue);

            //String closestLonKey = "closestLon";
            //Double closestLonValue = closestLonvar;
            //weatherJsonObject.put(closestLonKey, closestLonValue);


            String timeKey = "time";
            Function(timeKey, Integer.class, timevar, weatherJsonObject);

            String time_hKey = "time_h";
            Function(time_hKey, Integer.class, time_hvar, weatherJsonObject);

            String successvarKey = "success";
            Function(successvarKey, Boolean.class, successvar, weatherJsonObject);

            String inrangevarKey = "inrange";
            Function(inrangevarKey, Boolean.class, inrangevar, weatherJsonObject);

            String messagevarKey = "message";
            Function(messagevarKey, String.class, messagevar, weatherJsonObject);

            String closestLatKey = "closestLat";
            Function(closestLatKey, Double.class, closestLatvar, weatherJsonObject);

            String closestLonKey = "closestLon";
            Function(closestLonKey, Double.class, closestLonvar, weatherJsonObject);



            return weatherJsonObject;

        } catch (FileNotFoundException fe) {

            jsonObject = new JSONObject();
            successvar = false;
            messagevar = "Weather data file was not found on server.";

            String successvarKey = "success";
            Boolean successvarValue = successvar;
            jsonObject.put(successvarKey, successvarValue);

            String messagevarKey = "message";
            String messagevarValue = messagevar;
            jsonObject.put(messagevarKey, messagevarValue);

            fe.printStackTrace();
            return jsonObject;

        } catch (Exception e) {

            jsonObject = new JSONObject();
            successvar = false;
            messagevar = "Something went wrong.";

            String successvarKey = "success";
            Boolean successvarValue = successvar;
            jsonObject.put(successvarKey, successvarValue);

            String messagevarKey = "message";
            String messagevarValue = messagevar;
            jsonObject.put(messagevarKey, messagevarValue);

            e.printStackTrace();
            return jsonObject;
        }


    }

    private static void Function(String key, Class<?> cls, Object var, JSONObject weatherJsonObject) {

        if (cls == Integer.class) {
            Integer value = (Integer) var;
            weatherJsonObject.put(key, value);

        } else if (cls == Boolean.class) {
            Boolean value = (Boolean) var;
            weatherJsonObject.put(key, value);

        } else if (cls == String.class) {
            String value = (String) var;
            weatherJsonObject.put(key, value);

        } else if (cls == Double.class) {
            Double value = (Double) var;
            weatherJsonObject.put(key, value);

        }
    }

}
