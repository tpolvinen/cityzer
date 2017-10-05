package com.haagahelia.cityzer.util;

import com.haagahelia.cityzer.domain.Weather;

import java.io.IOException;

import thredds.catalog.DataRootConfig;
import ucar.ma2.Array;
import ucar.ma2.ArrayInt;
import ucar.ma2.InvalidRangeException;
import ucar.nc2.dt.GridCoordSystem;
import ucar.nc2.dt.GridDataset;
import ucar.nc2.dt.GridDatatype;

public class NetCdfHandler {

    public  double getaData(int time, String location, double lat, double lon, String datatype) throws IOException, InvalidRangeException {

        // open the dataset, find the variable and its coordinate system
        GridDataset gds = ucar.nc2.dt.grid.GridDataset.open(location);
        GridDatatype grid = gds.findGridDatatype(datatype);
        GridCoordSystem gcs = grid.getCoordinateSystem();

        // find the x,y index for a specific lat/lon position
        int[] xy = gcs.findXYindexFromLatLon(lat, lon, null); // xy[0] = x, xy[1] = y

        // read the data at that lat, lon and the first time and z level (if any)
        Array data  = grid.readDataSlice(time, 0, xy[1], xy[0]); // note order is t, z, y, x;
        double val = data.getDouble(0); // we know its a scalar
        System.out.printf("Value at %f %f == %f%n", lat, lon, val);

        return val;
    }

}
