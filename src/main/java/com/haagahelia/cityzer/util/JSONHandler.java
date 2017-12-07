package com.haagahelia.cityzer.util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Date;
import java.lang.Math;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.haagahelia.cityzer.domain.LocationObject;
//import com.haagahelia.cityzer.domain.TimeObject;

public class JSONHandler {

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

        String hoursSincevar;
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
            hoursSincevar = hours_since;

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date h = formatter.parse(hours_since);

            long seconds = TimeUnit.MILLISECONDS.toSeconds(date.getTime()-h.getTime());

            long fullHours = seconds / 3600;

            long minutes = seconds / 60;

            if (seconds - (fullHours * 3600) > 1800 ) {
                fullHours ++;
            }

            timevar = (int) fullHours; // TODO: fix rounding from long casted to int?

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

                messagevar = "Message from JSONHandler.java!";  // TODO: move messages to application.properties
            }


            weatherJsonObject = (JSONObject) jsonObject.get(strLocation);

            String hoursSinceKey = "hours since";
            writeJsonObject(hoursSinceKey, hoursSincevar, latestWeatherJsonObject);

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


            setHourlyWeatherParameters(timevar, weatherJsonObject, latestWeatherJsonObject);


            computeWindchill(latestWeatherJsonObject);


            setOverage(timevar, latestWeatherJsonObject);
            

            return latestWeatherJsonObject;

        } catch (FileNotFoundException fe) {

            jsonObject = new JSONObject();
            successvar = false;
            // messagevar = properties.getProperty("fileNotFound.message");
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
            // messagevar = properties.getProperty("generalError.message");
            messagevar = "Something went wrong.";
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

    private static void setHourlyWeatherParameters(int timevar, JSONObject weatherJsonObject, JSONObject latestWeatherJsonObject) {

        String[] hourlyWeatherParameters = new String[]{"air_temperature_4", "eastward_wind_23", "precipitation_amount_353", "northward_wind_24"};

        /*Properties properties = new Properties();

        try {
            properties.load(new FileInputStream("application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] hourlyWeatherParameters = properties.getProperty("hourlyWeatherParameters.array").split(",");;
*/
        for (String s: hourlyWeatherParameters) {

            if (timevar == 0) {
                String jsonKey = s;
                Object var = weatherJsonObject.get(s);
                writeJsonObject(jsonKey, var, latestWeatherJsonObject);
            } else {
                int hour = 0;
                hour = timevar - 6; // UGLY HACK: -6 because data is assumed to be at least 6 hours old
                if (hour > 9) hour = 9;
                String jsonKey = s;
                Object var = weatherJsonObject.get(s + "_" + hour + "h");
                writeJsonObject(jsonKey, var, latestWeatherJsonObject);
            }

            for (int i = 1; i < 4; i ++) {
                int hour = 0;
                hour = timevar + i - 6; // UGLY HACK: -6 because data is assumed to be at least 6 hours old
                if (hour > 9) hour = 9;
                String jsonKey = s + "_" + i + "h";
                Object var = weatherJsonObject.get(s + "_" + hour + "h");
                writeJsonObject(jsonKey, var, latestWeatherJsonObject);
            }

        }

    }

    private static void computeWindchill(JSONObject latestWeatherJsonObject) {

        double windspeed;
        double windchill_air_temp;

        for (int i = 0; i < 4; i++) {

            String ending="";
            switch (i) {
                case 0:  ending = "";
                    break;
                case 1:  ending = "_1h";
                    break;
                case 2:  ending = "_2h";
                    break;
                case 3:  ending = "_3h";
                    break;
            }

            double eastward_wind = (double) latestWeatherJsonObject.get("eastward_wind_23" + ending);
            double northward_wind = (double) latestWeatherJsonObject.get("northward_wind_24" + ending);
            boolean windchill = false;

            windspeed = Math.hypot(Math.abs(eastward_wind), Math.abs(northward_wind));
            // source: https://www.tutorialspoint.com/java/lang/math_hypot.htm

            double temperature = (double) latestWeatherJsonObject.get("air_temperature_4" + ending) - 273.15;

            if (windspeed >= 2 && windspeed <= 32 && temperature >= -50 && temperature <= 10) {
                windchill = true;
                windchill_air_temp = 13.12 + (0.6215 * temperature) -
                        (13.9563 * Math.pow(windspeed, 0.16)) +
                        (0.4867 * temperature * Math.pow(windspeed, 0.16)) + 273.15;
                latestWeatherJsonObject.put("windchill" + ending, windchill);
                latestWeatherJsonObject.put("windchill_air_temp" + ending, windchill_air_temp);
            } else {
                latestWeatherJsonObject.put("windchill" + ending, windchill);
                latestWeatherJsonObject.put("windchill_air_temp" + ending, "null");
            }

        }
        // source: https://www.calcunation.com/calculator/wind-chill-celsius.php
        // source: https://www.jkauppi.fi/pakkasen-purevuus/
        // source: https://github.com/jsquared21/Intro-to-Java-Programming/blob/master/Exercise_02/Exercise_02_17/Exercise_02_17.java

    }

    private static void setOverage(int timevar, JSONObject latestWeatherJsonObject) {

        boolean overage = false;

        // Writes boolean "overage" as "true" to json when data is too old - 10th hour data is the last valid.

        for (int i = 0; i < 4; i++) {
            int hourcount = timevar + i - 6; // UGLY HACK: -6 because data is assumed to be at least 6 hours old
            if (i == 0) {
                if (hourcount > 9) overage = true;
                latestWeatherJsonObject.put("overage", overage);
            } else {
                if (hourcount > 9) overage = true;
                latestWeatherJsonObject.put("overage_" + i + "h", overage);
            }
        }
    }

}
