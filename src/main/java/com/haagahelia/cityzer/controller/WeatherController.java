package com.haagahelia.cityzer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import ucar.ma2.InvalidRangeException;

import java.io.IOException;

import com.haagahelia.cityzer.domain.WeatherObject;

@RestController
@RequestMapping(value = "/api/getWeather")
public class WeatherController {

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public WeatherObject getWeather(@RequestParam double lat, @RequestParam double lon) throws IOException, InvalidRangeException {

        // TODO: LATER: get server time + calculate the hour int based on the server time and the "hours since" in JSON file?
        // TODO: import (?) the /var/www/html/api/outputJSON.json file
        // TODO: pick the right JSON object from outputJSON.json file
        // TODO: return the right JSON object
        WeatherObject weatherObject = new WeatherObject();

        return weatherObject;
    }

}
