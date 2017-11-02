package com.haagahelia.cityzer.util;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSON_Reader {

    // source: http://www.javainterviewpoint.com/read-json-java-jsonobject-jsonarray/

    public static JSONObject reader(double lat, double lon, String filepath)  {

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        JSONObject weatherJsonObject = null;

        String strLat = Double.toString(lat);
        String strLon = Double.toString(lon);
        String strLocation = strLat + " " + strLon;

        String filepath = filepath;

        try {
            Object object = parser
                    .parse(new FileReader(filepath));

            jsonObject = (JSONObject) object;
            weatherJsonObject = (JSONObject) jsonObject.get(strLocation);

        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return weatherJsonObject;
    }

}
