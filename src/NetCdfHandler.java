import java.io.IOException;

import ucar.ma2.Array;
import ucar.ma2.InvalidRangeException;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;
import ucar.nc2.dt.GridCoordSystem;
import ucar.nc2.dt.GridDataset;
import ucar.nc2.dt.GridDatatype;

public class NetCdfHandler {
    String filename = null;

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
        return val;
    }

    public Array getLat(NetcdfFile ncfile) throws IOException, InvalidRangeException{
        Variable Vlat = ncfile.findVariable("lat");
        Array Alat = Vlat.read();

        return Alat;
    }

    public Array getLon(NetcdfFile ncfile) throws IOException, InvalidRangeException{
        Variable Vlon = ncfile.findVariable("lon");
        Array Alon = Vlon.read();

        return Alon;
    }

    public String getHoursStart(NetcdfFile ncfile) throws IOException, InvalidRangeException{
        String timepack = ncfile.getVariables().get(0).toString();
        int mark = timepack.indexOf("units =")+21;
        String datetime = timepack.substring(mark);
        String datetime2 = datetime.substring(0,19);

        return datetime2;
    }

}
