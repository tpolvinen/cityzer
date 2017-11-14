package com.haagahelia.cityzer.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.haagahelia.cityzer.domain.LocationObject;
//import com.haagahelia.cityzer.domain.TimeObject;

public class JSON_Reader {

    // source: http://www.javainterviewpoint.com/read-json-java-jsonobject-jsonarray/

    public static JSONObject weatherReader(double userLat, double userLon, String filepath, Date date)  {

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        JSONObject weatherJsonObject = null;
        JSONObject latestWeatherJsonObject = new JSONObject();
        JSONArray latsArraylist = null;
        JSONArray lonsArraylist = null;

        String path = filepath;

        LocationObject locationObject;

        int timevar = 0;
        int time_hvar = 0;
        boolean successvar;
        boolean inrangevar;
        String messagevar;
        double closestLatvar;
        double closestLonvar;
        String hours_since;

        try {
            Object object = parser.parse(new FileReader(path));

            jsonObject = (JSONObject) object;

            successvar = true;

            hours_since = String.valueOf(jsonObject.get("hours since"));
            System.out.println("hours_since: " + hours_since);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date h = formatter.parse(hours_since);

            long seconds = TimeUnit.MILLISECONDS.toSeconds(date.getTime()-h.getTime());
            System.out.println("Seconds since hours_since: " + seconds);

            long fullHours = seconds / 3600;
            System.out.println("Full hours since hours_since: " + fullHours);

            long minutes = seconds / 60;
            System.out.println("Minutes since hours_since: " + minutes);

            if (seconds - (fullHours * 3600) > 1800 ) {
                fullHours ++;
                System.out.println("Added 1 hour");
            }

            System.out.println("Closest full hour is " + fullHours);

            if (fullHours > 9) fullHours = 9;  // outputJSON.json only has 10 hours of forecast data, beginning from 0.

            timevar = (int) fullHours;

            // TODO: Should we return only the latest part of weatherJsonObject? Like parts: fullhours -1, -2, -3, -4

            // "air_temperature_4_3h": X, "air_temperature_4_1h": Y, <-- How to get these by the hour number?


            // TODO: perhaps refactor these to a single method:
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
                messagevar = "Message from JSON_Reader.java!";  // TODO: move messages to application.properties
            }



            weatherJsonObject = (JSONObject) jsonObject.get(strLocation);




            String timeKey = "time";
            writeJsonObject(timeKey, timevar, latestWeatherJsonObject);

            String time_hKey = "time_h";
            writeJsonObject(time_hKey, time_hvar, latestWeatherJsonObject);

            String successKey = "success";
            writeJsonObject(successKey, successvar, latestWeatherJsonObject);

            String inrangeKey = "inrange";
            writeJsonObject(inrangeKey, inrangevar, latestWeatherJsonObject);

            String messageKey = "message";
            writeJsonObject(messageKey, messagevar, latestWeatherJsonObject);

            String closestLatKey = "closestLat";
            writeJsonObject(closestLatKey, closestLatvar, latestWeatherJsonObject);

            String closestLonKey = "closestLon";
            writeJsonObject(closestLonKey, closestLonvar, latestWeatherJsonObject);

            // fullHours = 9 max.

            for (int i = 4; i > 0; i --) {
                if (i == 1) {
                    String jsonKey = "air_temperature_4";
                    Object var = weatherJsonObject.get("air_temperature_4");
                    writeJsonObject(jsonKey, var, latestWeatherJsonObject);
                } else {
                    int hour = timevar + (i--);
                    if (hour > 9) hour = 9;
                    String jsonKey = "air_temperature_4_" + hour + "h";
                    Object var = weatherJsonObject.get("air_temperature_4_" + i + "h");
                    writeJsonObject(jsonKey, var, latestWeatherJsonObject);
                }
            }

            String jsonKey = "air_temperature_4";
            Object var = weatherJsonObject.get("air_temperature_4");
            writeJsonObject(jsonKey, var, latestWeatherJsonObject);





            // return weatherJsonObject;
            return latestWeatherJsonObject;

        } catch (FileNotFoundException fe) {

            jsonObject = new JSONObject();
            successvar = false;
            messagevar = "Weather data file was not found on server."; // TODO: move messages to application.properties

            String successvarKey = "success";
            writeJsonObject(successvarKey, successvar, jsonObject);

            String messagevarKey = "message";
            writeJsonObject(messagevarKey, messagevar, jsonObject);

            fe.printStackTrace();
            return jsonObject;

        } catch (Exception e) {

            jsonObject = new JSONObject();
            successvar = false;
            messagevar = "Something went wrong."; // TODO: move messages to application.properties

            String successvarKey = "success";
            writeJsonObject(successvarKey, successvar, jsonObject);

            String messagevarKey = "message";
            writeJsonObject(messagevarKey, messagevar, jsonObject);

            e.printStackTrace();
            return jsonObject;
        }

    }

    private static void writeJsonObject(String jsonKey, Object var, JSONObject latestWeatherJsonObject) {

        String key = jsonKey;
        Object value = var;

        latestWeatherJsonObject.put(key, value);

    }

}
