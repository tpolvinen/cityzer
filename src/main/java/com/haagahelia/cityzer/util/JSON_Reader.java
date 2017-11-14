package com.haagahelia.cityzer.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.haagahelia.cityzer.domain.LocationObject;

public class JSON_Reader {

    // source: http://www.javainterviewpoint.com/read-json-java-jsonobject-jsonarray/

    public static JSONObject weatherReader(double userLat, double userLon, String filepath, Date date) {

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


        long test = 0;
        try {
            Object object = parser.parse(new FileReader(path));

            jsonObject = (JSONObject) object;
            String hours = String.valueOf(jsonObject.get("hours since"));
            System.out.println(hours);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date h = formatter.parse(hours);

            test = TimeUnit.MILLISECONDS.toMinutes(date.getTime() - h.getTime());
            System.out.println(test);

            successvar = true;

            // TODO: refactor these to a single method:
            // source https://stackoverflow.com/questions/41016764/parsing-nested-json-array-in-java

            JSONArray jsonLats = (JSONArray) jsonObject.get("lats");

            for (Object latsObject : jsonLats.toArray()) {
                JSONObject latsJsonStorage = (JSONObject) latsObject;
                latsArraylist = (JSONArray) latsJsonStorage.get("storage");
                System.out.println(latsArraylist);
            }

            JSONArray jsonLons = (JSONArray) jsonObject.get("lons");

            for (Object lonsObject : jsonLons.toArray()) {
                JSONObject lonsJsonStorage = (JSONObject) lonsObject;
                lonsArraylist = (JSONArray) lonsJsonStorage.get("storage");
                System.out.println(lonsArraylist);
            }

            ArrayList<Double> latsList = new ArrayList<Double>();
            if (latsArraylist != null) {
                int len = latsArraylist.size();
                for (int i = 0; i < len; i++) {
                    latsList.add(Double.parseDouble(latsArraylist.get(i).toString()));
                }
            }

            ArrayList<Double> lonsList = new ArrayList<Double>();
            if (lonsArraylist != null) {
                int len = lonsArraylist.size();
                for (int i = 0; i < len; i++) {
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
            writeJsonObject(timeKey, timevar, weatherJsonObject, test);

            String time_hKey = "time_h";
            writeJsonObject(time_hKey, time_hvar, weatherJsonObject, test);

            String successKey = "success";
            writeJsonObject(successKey, successvar, weatherJsonObject, test);

            String inrangeKey = "inrange";
            writeJsonObject(inrangeKey, inrangevar, weatherJsonObject, test);

            String messageKey = "message";
            writeJsonObject(messageKey, messagevar, weatherJsonObject, test);

            String closestLatKey = "closestLat";
            writeJsonObject(closestLatKey, closestLatvar, weatherJsonObject, test);

            String closestLonKey = "closestLon";
            writeJsonObject(closestLonKey, closestLonvar, weatherJsonObject, test);


            if (test <= 89) {
                System.out.println(weatherJsonObject);
            } else if (test >89 ||test <= 149){
                weatherJsonObject.replace("air_temperature_4h", weatherJsonObject.get("air_temperature_4_1h"));
                weatherJsonObject.replace("air_temperature_4_1h", weatherJsonObject.get("air_temperature_4_2h"));
                weatherJsonObject.replace("air_temperature_4_2h", weatherJsonObject.get("air_temperature_4_3h"));
                weatherJsonObject.replace("air_temperature_4_3h", weatherJsonObject.get("air_temperature_4_4h"));
            }else if (test > 149 ||test <= 209){
                weatherJsonObject.replace("air_temperature_4", weatherJsonObject.get("air_temperature_4_2h"));
                weatherJsonObject.replace("air_temperature_4_1h", weatherJsonObject.get("air_temperature_4_3h"));
                weatherJsonObject.replace("air_temperature_4_2h", weatherJsonObject.get("air_temperature_4_4h"));
                weatherJsonObject.replace("air_temperature_4_3h", weatherJsonObject.get("air_temperature_4_5h"));
            }else if (test > 209 || test<= 269){
                weatherJsonObject.replace("air_temperature_4", weatherJsonObject.get("air_temperature_4_3h"));
                weatherJsonObject.replace("air_temperature_4_1h", weatherJsonObject.get("air_temperature_4_4h"));
                weatherJsonObject.replace("air_temperature_4_2h", weatherJsonObject.get("air_temperature_4_5h"));
                weatherJsonObject.replace("air_temperature_4_3h", weatherJsonObject.get("air_temperature_4_6h"));
            }else if (test > 269 || test <= 329){
                weatherJsonObject.replace("air_temperature_4", weatherJsonObject.get("air_temperature_4_4h"));
                weatherJsonObject.replace("air_temperature_4_1h", weatherJsonObject.get("air_temperature_4_5h"));
                weatherJsonObject.replace("air_temperature_4_2h", weatherJsonObject.get("air_temperature_4_6h"));
                weatherJsonObject.replace("air_temperature_4_3h", weatherJsonObject.get("air_temperature_4_7h"));
            }else if (test > 329 || test <= 389){
                weatherJsonObject.replace("air_temperature_4", weatherJsonObject.get("air_temperature_4_5h"));
                weatherJsonObject.replace("air_temperature_4_1h", weatherJsonObject.get("air_temperature_4_6h"));
                weatherJsonObject.replace("air_temperature_4_2h", weatherJsonObject.get("air_temperature_4_7h"));
                weatherJsonObject.replace("air_temperature_4_3h", weatherJsonObject.get("air_temperature_4_8h"));
            }else {
                weatherJsonObject.replace("air_temperature_4", weatherJsonObject.get("air_temperature_4_6h"));
                weatherJsonObject.replace("air_temperature_4_1h", weatherJsonObject.get("air_temperature_4_7h"));
                weatherJsonObject.replace("air_temperature_4_2h", weatherJsonObject.get("air_temperature_4_8h"));
                weatherJsonObject.replace("air_temperature_4_3h", weatherJsonObject.get("air_temperature_4_9h"));
            }




            return weatherJsonObject;

        } catch (FileNotFoundException fe) {

            jsonObject = new JSONObject();
            successvar = false;
            messagevar = "Weather data file was not found on server."; // TODO: move messages to application.properties

            String successvarKey = "success";
            writeJsonObject(successvarKey, successvar, jsonObject, test);

            String messagevarKey = "message";
            writeJsonObject(messagevarKey, messagevar, jsonObject, test);

            fe.printStackTrace();
            return jsonObject;

        } catch (Exception e) {

            jsonObject = new JSONObject();
            successvar = false;
            messagevar = "Something went wrong."; // TODO: move messages to application.properties

            String successvarKey = "success";
            writeJsonObject(successvarKey, successvar, jsonObject, test);

            String messagevarKey = "message";
            writeJsonObject(messagevarKey, messagevar, jsonObject, test);

            e.printStackTrace();
            return jsonObject;
        }

    }


    private static void writeJsonObject(String jsonKey, Object var, JSONObject weatherJsonObject, long test) {


        String key = jsonKey;
        Object value = var;

        weatherJsonObject.put(key, value);


    }

}
