package com.haagahelia.cityzer.domain;

public class Weather {
    private double tempature;
    private double rainAmounth;
    private double cloud_area_fraction;
    private double eastward_wind;
    private double northward_wind;

    public double getTempature() {
        return tempature;
    }
    public void setTempature(double tempature) {
        this.tempature = tempature;
    }
    public double getRainAmounth() {
        return rainAmounth;
    }
    public void setRainAmounth(double rainAmounth) {
        this.rainAmounth = rainAmounth;
    }
    public double getCloud_area_fraction() {
        return cloud_area_fraction;
    }
    public void setCloud_area_fraction(double cloud_area_fraction) {
        this.cloud_area_fraction = cloud_area_fraction;
    }
    public double getEastward_wind() {
        return eastward_wind;
    }
    public void setEastward_wind(double eastward_wind) {
        this.eastward_wind = eastward_wind;
    }
    public double getNorthward_wind() {
        return northward_wind;
    }
    public void setNorthward_wind(double northward_wind) {
        this.northward_wind = northward_wind;
    }
    @Override
    public String toString() {
        return "Weather [tempature=" + tempature + ", rainAmounth=" + rainAmounth
                + ", cloud_area_fraction=" + cloud_area_fraction
                + ", eastward_wind=" + eastward_wind + ", northward_wind="
                + northward_wind + "]";
    }
}
