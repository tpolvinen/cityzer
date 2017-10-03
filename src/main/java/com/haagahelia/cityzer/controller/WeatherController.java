package com.haagahelia.cityzer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody Weather getWeather(@RequestParam int time, @RequestParam double lat, @RequestParam double lon) throws IOException, InvalidRangeException {

        FeatureDatasetFactoryManager fdfm = new FeatureDatasetFactoryManager();
        //Weather weather = new Weather();
        fdfm.makeDataSet(weather, time, lat, lon);

        return weather;
    }

}
