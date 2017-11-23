package com.haagahelia.cityzer.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Date;
import java.lang.Math;

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

        double windspeed;
        double windchill_air_temp;

        System.out.println("JSON_Reader method weatherReader starts");


        try {
            Object object = parser.parse(new FileReader(path));

            jsonObject = (JSONObject) object;

            successvar = true;

            hours_since = String.valueOf(jsonObject.get("hours since"));

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date h = formatter.parse(hours_since);

            long seconds = TimeUnit.MILLISECONDS.toSeconds(date.getTime()-h.getTime());

            long fullHours = seconds / 3600;

            long minutes = seconds / 60;

            if (seconds - (fullHours * 3600) > 1800 ) {
                fullHours ++;
            }

            //if (fullHours > 9) fullHours = 9;  // outputJSON.json only has 10 hours of forecast data, beginning from 0.

            // TODO: What to return when forecast data file gets too old? When data is over 9h, set boolean "overage" to "true" per hour

            timevar = (int) fullHours; // TODO: fix rounding from long casted to int?

            // TODO: perhaps refactor these to a single method:
            // source https://stackoverflow.com/questions/41016764/parsing-nested-json-array-in-java

            JSONArray jsonLats = (JSONArray) jsonObject.get("lats");

            for(Object latsObject: jsonLats.toArray()){
                JSONObject latsJsonStorage = (JSONObject) latsObject;
                latsArraylist = (JSONArray) latsJsonStorage.get("storage");
            }

            JSONArray jsonLons = (JSONArray) jsonObject.get("lons");

            for(Object lonsObject: jsonLons.toArray()){
                JSONObject lonsJsonStorage = (JSONObject) lonsObject;
                lonsArraylist = (JSONArray) lonsJsonStorage.get("storage");
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


            String[] weatherParameters = new String[]{"air_temperature_4", "eastward_wind_23", "precipitation_amount_353", "northward_wind_24"};


            for (String s: weatherParameters) {

                if (timevar == 0) {
                    String jsonKey = s;
                    Object var = weatherJsonObject.get(s);
                    writeJsonObject(jsonKey, var, latestWeatherJsonObject);
                } else {
                    int hour = 0;
                    hour = timevar;
                    if (hour > 9) hour = 9;
                    String jsonKey = s;
                    Object var = weatherJsonObject.get(s + "_" + hour + "h");
                    writeJsonObject(jsonKey, var, latestWeatherJsonObject);
                }

                for (int i = 1; i < 4; i ++) {
                    int hour = 0;
                    hour = timevar + i;
                    if (hour > 9) hour = 9;
                    String jsonKey = s + "_" + i + "h";
                    Object var = weatherJsonObject.get(s + "_" + hour + "h");
                    writeJsonObject(jsonKey, var, latestWeatherJsonObject);
                }
            }


            for (int i = 0; i < 4; i++) {

                if (i == 0) {
                    double eastward_wind = (double) latestWeatherJsonObject.get("eastward_wind_23");
                    double northward_wind = (double) latestWeatherJsonObject.get("northward_wind_24");
                    boolean windchill = false;

                    windspeed = Math.hypot(Math.abs(eastward_wind), Math.abs(northward_wind));
                    // source: https://www.tutorialspoint.com/java/lang/math_hypot.htm

                    double temperature = (double) latestWeatherJsonObject.get("air_temperature_4") - 273.15;

                    if (windspeed >= 2 && windspeed <= 32 && temperature >= -50 && temperature <= 10) {
                        windchill = true;
                        windchill_air_temp = 13.12 + (0.6215 * temperature) -
                                (13.9563 * Math.pow(windspeed, 0.16)) +
                                (0.4867 * temperature * Math.pow(windspeed, 0.16)) + 273.15;
                        // System.out.println("Valid windchill!");
                        // System.out.println("0 Windchill = " + windchill_air_temp);

                        latestWeatherJsonObject.put("windchill", windchill);
                        latestWeatherJsonObject.put("windchill_air_temp", windchill_air_temp);
                    } else {
                        latestWeatherJsonObject.put("windchill", windchill);
                        latestWeatherJsonObject.put("windchill_air_temp", "null");
                    }

                    // System.out.println("0 Temperature = " + temperature);
                    // System.out.println("0 Windspeed = " + windspeed);

                } else {
                    double eastward_wind = (double) latestWeatherJsonObject.get("eastward_wind_23_" + i + "h");
                    double northward_wind = (double) latestWeatherJsonObject.get("northward_wind_24_" + i + "h");
                    boolean windchill = false;

                    windspeed = Math.hypot(Math.abs(eastward_wind), Math.abs(northward_wind));

                    double temperature = (double) latestWeatherJsonObject.get("air_temperature_4_" + i + "h") - 273.15;

                    if (windspeed >= 2 && windspeed <= 32 && temperature >= -50 && temperature <= 10) {
                        windchill = true;
                        windchill_air_temp = 13.12 + (0.6215 * temperature) -
                                (13.9563 * Math.pow(windspeed, 0.16)) +
                                (0.4867 * temperature * Math.pow(windspeed, 0.16)) + 273.15;
                        // System.out.println("Valid windchill!");
                        // System.out.println(i + " Windchill = " + windchill_air_temp); // + "... should be 251.92");

                        latestWeatherJsonObject.put("windchill_" + i + "h", windchill);
                        latestWeatherJsonObject.put("windchill_air_temp_" + i + "h", windchill_air_temp);
                    } else {
                        latestWeatherJsonObject.put("windchill_" + i + "h", windchill);
                        latestWeatherJsonObject.put("windchill_air_temp_" + i + "h", "null");
                    }

                    // System.out.println(i + " Temperature = " + temperature);
                    // System.out.println(i + " Windspeed = " + windspeed);
                }

            }
            // source: https://www.calcunation.com/calculator/wind-chill-celsius.php
            // source: https://www.jkauppi.fi/pakkasen-purevuus/
            // source: https://github.com/jsquared21/Intro-to-Java-Programming/blob/master/Exercise_02/Exercise_02_17/Exercise_02_17.java

            boolean overage = false;

            // Writes boolean "overage" as "true" to json when data is too old - 10th hour data is the last valid.

            for (int i = 0; i < 4; i++) {
                int hourcount = timevar + i;
                if (i == 0) {
                    if (hourcount > 9) overage = true;
                    latestWeatherJsonObject.put("overage", overage);
                } else {
                    if (hourcount > 9) overage = true;
                    latestWeatherJsonObject.put("overage_" + i + "h", overage);
                }
            }

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
