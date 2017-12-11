package com.haagahelia.cityzer.controller;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import ucar.ma2.InvalidRangeException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import com.haagahelia.cityzer.util.JSONHandler;

import org.json.simple.JSONObject;

@RestController
public class WeatherController {

    @Value("${json.filepath}")
    private String filepath;

    @Value("${mockUpWeather.filepath}")
    private String mockUpWeatherFilepath;

    @CrossOrigin
    @RequestMapping(value = "/api/getWeather", method = RequestMethod.GET, produces = "application/json")
    public JSONObject getWeather(@RequestParam double userLat, @RequestParam double userLon) throws IOException, InvalidRangeException {

        JSONObject jsonObject;

        Date date = new Date();

        JSONParser parser = new JSONParser();

        // This returns a mock-up json file in case the request comes with coordinates at lat 60.2015 - 60.2025 and lon 24.9335 - 24.9345

        if (userLat >= 60.2015 && userLat <= 60.2025 && userLon >= 24.9335 && userLon <= 24.9345) {

            Object object = null;
            try {
                object = parser.parse(new FileReader(mockUpWeatherFilepath));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            jsonObject = (JSONObject) object;
            return jsonObject;
        }

        return JSONHandler.weatherReader(userLat, userLon, filepath, date);

    }

}
