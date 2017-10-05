package com.haagahelia.cityzer.util;

import java.io.IOException;
import java.text.NumberFormat;

import org.springframework.stereotype.Service;

import com.haagahelia.cityzer.domain.Weather;
import com.haagahelia.cityzer.util.NetCdfHandler;
import ucar.ma2.InvalidRangeException;
import ucar.nc2.dt.*;


@Service
public class FeatureDatasetFactoryManager {

    public Weather makeDataSet(Weather weather, String weatherfilepath, int time, double lat, double lon)
            throws IOException, InvalidRangeException {

        // TODO: find out if it is possible to get all four hours's data in to a single Weather

        NetCdfHandler handler = new NetCdfHandler();

        String location = weatherfilepath;

        String datatype = "air_temperature_4";
        double val = handler.getaData(time, location, lat, lon, datatype) ;
        val=val- 273.15;
        double air_temperature_4=roundValue(val);

        datatype = "cloud_area_fraction_79";
        val = handler.getaData(time, location, lat, lon, datatype);

        double cloud_area_fraction_79=roundValue(val);

        datatype ="precipitation_amount_353";
        val=handler.getaData(time, location, lat, lon, datatype);
        double precipitation_amount_353=roundValue(val);

        datatype = "eastward_wind_23";
        val=handler.getaData(time, location, lat, lon, datatype);

        double eastward_wind_23=roundValue(val);

        datatype = "northward_wind_24";
        val=handler.getaData(time, location, lat, lon, datatype);

        double northward_wind_24=roundValue(val);

        weather.setAir_temperature_4(air_temperature_4);
        weather.setCloud_area_fraction_79(cloud_area_fraction_79);
        weather.setPrecipitation_amount_353(precipitation_amount_353);
        weather.setEastward_wind_23(eastward_wind_23);
        weather.setNorthward_wind_24(northward_wind_24);

        System.out.println(weather.toString());

        return weather;
    }

    private  double roundValue(double val) {

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(12);

        String roundedValStr = null;
        double roundedVal = 0;

        if (val < 0) {
            val = val * -1;
            roundedValStr = nf.format(val);
            roundedVal = Double.parseDouble(roundedValStr.replaceFirst(",", "."));
            roundedVal = 0 - roundedVal;
        } else {
            roundedValStr = nf.format(val);
            roundedVal = Double.parseDouble(roundedValStr.replaceFirst(",", "."));
        }

        return roundedVal;
    }

}