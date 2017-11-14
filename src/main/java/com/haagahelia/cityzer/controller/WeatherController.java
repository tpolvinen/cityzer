package com.haagahelia.cityzer.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import ucar.ma2.InvalidRangeException;

import java.io.IOException;
import java.util.Date;

import com.haagahelia.cityzer.util.JSON_Reader;

import org.json.simple.JSONObject;

@RestController
public class WeatherController {

    @Value("${json.filepath}")
    private String filepath;

    @CrossOrigin
    @RequestMapping(value = "/api/getWeather", method = RequestMethod.GET, produces = "application/json")
    public JSONObject getWeather(@RequestParam double userLat, @RequestParam double userLon) throws IOException, InvalidRangeException {

        // TODO: get server time + calculate the hour int based on the server time and the "hours since" in JSON file?
        
        JSONObject jsonObject;
        Date date = new Date();

        return JSON_Reader.weatherReader(userLat, userLon, filepath, date);

    }

}
