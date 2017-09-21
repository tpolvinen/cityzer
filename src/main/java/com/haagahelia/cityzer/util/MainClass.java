package com.haagahelia.cityzer.util;

import java.io.IOException;
import com.haagahelia.cityzer.domain.Weather;
import com.haagahelia.cityzer.util.NetCdfHandler;
import ucar.ma2.InvalidRangeException;
import ucar.nc2.dt.*;

public class MainClass {
    public static void main(String args[]) throws IOException, InvalidRangeException {

        NetCdfHandler handler = new NetCdfHandler();

        Weather weather = new Weather();

        String location = "/private/tmp/data.bin";

        int time = 0;
        double lat = 60.201953770612505;
        double lon = 24.934014050581936;

        String datatype = "air_temperature_4";
        double tempature = handler.getaData(time, location, lat, lon, datatype) - 273.15;

        datatype = "cloud_area_fraction_79";
        double cloud_area_fraction = handler.getaData(time, location, lat, lon, datatype);

        datatype ="precipitation_amount_353";
        double rainAmounth=handler.getaData(time, location, lat, lon, datatype);

        datatype = "eastward_wind_23";
        double eastward_wind=handler.getaData(time, location, lat, lon, datatype);

        datatype = "northward_wind_24";
        double northward_wind=handler.getaData(time, location, lat, lon, datatype);

        weather.setTempature(tempature);
        weather.setCloud_area_fraction(cloud_area_fraction);
        weather.setRainAmounth(rainAmounth);
        weather.setEastward_wind(eastward_wind);
        weather.setNorthward_wind(northward_wind);
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(weather.toString());;
    }
}
