package com.haagahelia.cityzer.util;

import java.io.IOException;
import java.text.NumberFormat;

import com.haagahelia.cityzer.domain.Weather;
import com.haagahelia.cityzer.util.NetCdfHandler;
import ucar.ma2.InvalidRangeException;
import ucar.nc2.dt.*;

public class FeatureDatasetFactoryManager {
    public Weather makeDataSet( int time, double lat, double lon) throws IOException, InvalidRangeException {

        NetCdfHandler handler = new NetCdfHandler();

        Weather weather = new Weather();

        String location = "/m:/cityzer/_mnt_meru_data_cityzerdb_Storage_grid_data_HIRLAM_HIRLAM_2017-09-11T00_00_00Z.nc";


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


        return weather;
    }



private  double roundValue(double val){


    NumberFormat nf = NumberFormat.getInstance();
    nf.setMinimumFractionDigits(12);

    String roundedValStr=null;
    double roundedVal=0;

    if(val<0){



      val=val*-1;
        roundedValStr=nf.format(val);

    roundedVal=Double.parseDouble(roundedValStr.replaceFirst(",", "."));

    roundedVal=0-roundedVal;

    }

    else{
        roundedValStr=nf.format(val);

        roundedVal=Double.parseDouble(roundedValStr.replaceFirst(",", "."));

    }



    return roundedVal;}




}