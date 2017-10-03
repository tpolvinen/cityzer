package com.haagahelia.cityzer.domain;

import org.springframework.stereotype.Component;

@Component
public class Weather {
    private double air_temperature_4;
    private double precipitation_amount_353;
    private double cloud_area_fraction_79;
    private double eastward_wind_23;
    private double northward_wind_24;

    public double getAir_temperature_4() {
        return air_temperature_4;
    }

    public double getPrecipitation_amount_353() {
        return precipitation_amount_353;
    }

    public double getCloud_area_fraction_79() {
        return cloud_area_fraction_79;
    }

    public double getEastward_wind_23() {
        return eastward_wind_23;
    }

    public double getNorthward_wind_24() {
        return northward_wind_24;
    }


    public void setAir_temperature_4(double air_temperature_4) {
        this.air_temperature_4 = air_temperature_4;
    }

    public void setPrecipitation_amount_353(double precipitation_amount_353) {
        this.precipitation_amount_353 = precipitation_amount_353;
    }

    public void setCloud_area_fraction_79(double cloud_area_fraction_79) {
        this.cloud_area_fraction_79 = cloud_area_fraction_79;
    }

    public void setEastward_wind_23(double eastward_wind_23) {
        this.eastward_wind_23 = eastward_wind_23;
    }

    public void setNorthward_wind_24(double northward_wind_24) {
        this.northward_wind_24 = northward_wind_24;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "air_temperature_4=" + air_temperature_4 +
                ", precipitation_amount_353=" + precipitation_amount_353 +
                ", cloud_area_fraction_79=" + cloud_area_fraction_79 +
                ", eastward_wind_23=" + eastward_wind_23 +
                ", northward_wind_24=" + northward_wind_24 +
                '}';
    }
}

