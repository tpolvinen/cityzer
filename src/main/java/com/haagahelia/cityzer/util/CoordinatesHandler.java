package com.haagahelia.cityzer.util;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CoordinatesHandler {

    public static String finder(double userLat, double userLon, double[] latsArray, double[] lonsArray) {

       /* //latitudet
        double[] latitudes = new double[10];
        latitudes[0] = 100.234;
        latitudes[1] = -34200.4324;
        latitudes[2] = 3040.4321;
        latitudes[3] = 400433.9623;
        latitudes[4] = 500;
        latitudes[5] = -100.325;
        latitudes[6] = -200.236326;
        latitudes[7] = 550;
        latitudes[8] = 6584.23636223;
        latitudes[9] = -945.2363523;

        //longitudet
        double[] longitudes = new double[10];
        longitudes[0] = 321.234;
        longitudes[1] = 1623.4324;
        longitudes[2] = 4444.4321;
        longitudes[3] = 5555.9623;
        longitudes[4] = 6666;
        longitudes[5] = 7777.325;
        longitudes[6] = 8888.236326;
        longitudes[7] = 9999;
        longitudes[8] = 12000;
        longitudes[9] = 12000.2363523;

        //käyttäjän koordinaatit
        double userLat=525;
        double userLon=125;*/

        //hae lähimmät pisteet taulukoista käyttäjän koordinaatteihin katsoen
        double closestLat = FindClosest(userLat,latsArray);
        double closestLon = FindClosest(userLon,lonsArray);

/*        System.out.println("Käyttäjän antama lat on "+userLat+". Lähin lat taulukosta on "+closestLat+".");
        System.out.println("Käyttäjän antama lon on "+userLon+". Lähin lon taulukosta on "+closestLon+".");*/

        String strLat = Double.toString(closestLat);
        String strLon = Double.toString(closestLon);

        String strLocation = strLat + " " + strLon;

        return strLocation;
    }


    static double FindClosest(double arvo, double[] numbers)
    {
        double distance = Math.abs(numbers[0] - arvo);
        int idx = 0;
        for(int c = 1; c < numbers.length; c++){
            double cdistance = Math.abs(numbers[c] - arvo);
            if(cdistance < distance){
                idx = c;
                distance = cdistance;
            }
        }
        return numbers[idx];
    }
}