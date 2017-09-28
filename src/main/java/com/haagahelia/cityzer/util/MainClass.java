package com.haagahelia.cityzer.util;

import java.io.IOException;
import java.text.NumberFormat;

import com.haagahelia.cityzer.domain.Weather;
import com.haagahelia.cityzer.util.NetCdfHandler;
import ucar.ma2.InvalidRangeException;
import ucar.nc2.dt.*;

public class MainClass {
    public static void main(String args[]) throws IOException, InvalidRangeException {

        NetCdfHandler handler = new NetCdfHandler();

        Weather weather = new Weather();

        String location = "/m:/cityzer/_mnt_meru_data_cityzerdb_Storage_grid_data_HIRLAM_HIRLAM_2017-09-11T00_00_00Z.nc";

        int time = 0;
        double lat = 60.201953770612505;
        double lon = 24.934014050581936;

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
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(weather.toString());;
    }



private static double roundValue(double val){


    NumberFormat nf = NumberFormat.getInstance();
    nf.setMinimumFractionDigits(12);

    String roundedValStr=nf.format(val);




    double roundedVal=Double.parseDouble(roundedValStr.replaceFirst(",", "."));


    return roundedVal;}




}