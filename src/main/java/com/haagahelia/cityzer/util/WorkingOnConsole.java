package com.haagahelia.cityzer.util;

import ucar.ma2.InvalidRangeException;

import java.io.IOException;

/**
 * Created by a1500863 on 28.9.2017.
 */
public class WorkingOnConsole {


    public static void main(String[] args) throws IOException, InvalidRangeException {


        int time=24;

        double lat= 64.12364789;

        double lon=24.12364598789;


        FeatureDatasetFactoryManager f=new FeatureDatasetFactoryManager();


        f.makeDataSet(time, lat, lon);


    }


}
