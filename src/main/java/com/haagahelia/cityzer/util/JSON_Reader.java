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

            // Will this work? REPONSE_JSON_OBJECT.getJSONObject("lats").getJSONArray("storage");
            // source: https://stackoverflow.com/questions/14898768/how-to-access-nested-elements-of-json-object-using-getjsonarray-method


            //JSONArray latsArray = (JSONArray)jsonObject.get("lats").getJSONArray("storage");
            //JSONArray lonsArray = (JSONArray)jsonObject.get("lons").getJSONArray("storage");

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

            //JSONArray latsStorageArray = (JSONArray)latsObject.get("storage");

            //JSONObject lonsObject = (JSONObject) jsonObject.get("lons");
            //JSONArray lonsStorageArray = (JSONArray)lonsObject.get("storage");

            //JSONObject lonsObject = (JSONObject)jsonObject.get("lons");
            // JSONArray lonsArray = (JSONArray)lonsObject.get("storage");

            // Nope.
            // So much nope.
            // source: https://stackoverflow.com/questions/2255220/how-to-parse-a-json-and-turn-its-values-into-an-array
            // JSONObject myjson = new JSONObject(jsonObject);
            // JSONArray the_json_array = myjson.getJSONArray("profiles");

            // source: https://stackoverflow.com/questions/1568762/accessing-members-of-items-in-a-jsonarray-with-java
//            for (int i = 0; i < recs.length(); ++i) {
//                JSONObject rec = recs.getJSONObject(i);
//                int id = rec.getInt("id");
//                String loc = rec.getString("loc");
//                // ...
//            }

                    // TODO: How to convert JSONArray to ArrayList<Double>?

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

            // TODO: get the weatherJsonObject with strLocation

            weatherJsonObject = (JSONObject) jsonObject.get(strLocation);

        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return weatherJsonObject;
    }

}
