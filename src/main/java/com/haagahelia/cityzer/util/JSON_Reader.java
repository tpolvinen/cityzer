package com.haagahelia.cityzer.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
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

        // TODO: get these values from wherever

        int timevar = 0;
        int time_hvar = 0;
        boolean successvar = false;
        boolean inrangevar = false;
        String messagevar = "Message from JSON_Reader.java!";

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

            weatherJsonObject = (JSONObject) jsonObject.get(strLocation);

            // TODO: refactor these to a single method:

            String timeKey = "time";
            Integer timeValue = timevar;
            //String timeValue = String.valueOf(timevar);
            weatherJsonObject.put(timeKey, timeValue);

            String time_hKey = "time_h";
            Integer time_hValue = time_hvar;
            //String time_hValue = String.valueOf(time_hvar);
            weatherJsonObject.put(time_hKey, time_hValue);

            String successvarKey = "success";
            Boolean successvarValue = successvar;
            weatherJsonObject.put(successvarKey, successvarValue);

            String inrangevarKey = "inrange";
            Boolean inrangevarValue = inrangevar;
            weatherJsonObject.put(inrangevarKey, inrangevarValue);

            String messagevarKey = "message";
            String messagevarValue = messagevar;
            weatherJsonObject.put(messagevarKey, messagevarValue);

            return weatherJsonObject;

        } catch (FileNotFoundException fe) {

            //TODO: make this return weatherJsonObject with no values...
            // TODO: ...but success=false message="Something Went Wrong" or something(?)
            fe.printStackTrace();
            return weatherJsonObject;
        } catch (Exception e) {

            //TODO: same as above here.
            e.printStackTrace();
            return weatherJsonObject;
        }

        // return weatherJsonObject;

    }

}
