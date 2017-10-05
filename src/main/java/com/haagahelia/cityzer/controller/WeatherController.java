package com.haagahelia.cityzer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.haagahelia.cityzer.util.FeatureDatasetFactoryManager;
import com.haagahelia.cityzer.domain.Weather;

import ucar.ma2.InvalidRangeException;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/getWeather")
public class WeatherController {

    @Autowired
    FeatureDatasetFactoryManager featureDatasetFactoryManager;

    @Autowired
    Weather weather;

    @Value("${weather.filepath}")
    private String weatherfilepath;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody Weather getWeather(@RequestParam int time, @RequestParam double lat, @RequestParam double lon) throws IOException, InvalidRangeException {

        // TODO: how to calculate the hours from the data to current time and then pass that int as "time" parameter?

        FeatureDatasetFactoryManager fdfm = new FeatureDatasetFactoryManager();
        fdfm.makeDataSet(weather, weatherfilepath, time, lat, lon);

        return weather;
    }

}
