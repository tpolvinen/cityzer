import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ucar.ma2.Array;
import ucar.ma2.InvalidRangeException;
import ucar.nc2.NetcdfFile;


public class DatasetManager {

        //final String locationMain = "C:\\Users\\a1500908\\Downloads\\_mnt_meru_data_cityzerdb_Storage_grid_data_HIRLAM_HIRLAM_2017-11-02T00_00_00Z.nc";
        final String locationMain = "/var/www/html/dataCDF.nc";
        NetcdfFile ncfile = null;
        public DatasetManager() throws IOException, InvalidRangeException {

        }


        public void databaseHandler() throws IOException, InvalidRangeException, JSONException {
            ncfile = NetcdfFile.open(locationMain);
            NetCdfHandler handlerMain = new NetCdfHandler();
            handlerMain.getHoursStart(ncfile);

            Array latArr = roundArray(handlerMain.getLat(ncfile));
            Array lonArr = roundArray(handlerMain.getLon(ncfile));


            JSONArray latarrJSON = new JSONArray(Arrays.asList(roundArray(latArr)));
            JSONArray lonarrJSON = new JSONArray(Arrays.asList(roundArray(lonArr)));

            PrintWriter print = null;
            //File file = new File("C:\\Users\\a1500908\\Downloads\\outputJSON5.json");
            File file = new File("/var/www/html/api/outputJSON.json");
            PrintWriter pw = new PrintWriter(file);
            pw.close();

            print = new PrintWriter(new FileWriter(file, true));
            print.println("{");
            print.println("\"hours since\": "+"\""+handlerMain.getHoursStart(ncfile)+"\",");
            print.println("\"lats\": "+latarrJSON+", ");
            print.println("\"lons\": "+lonarrJSON+", ");

            JSONObject inner = new JSONObject();
            for (int i=0; i < latArr.getSize(); i++){
                for (int k=0; k<lonArr.getSize();k++){
                    WeatherCollection wc = new WeatherCollection();
                    wc.setLat(roundValue(latArr.getDouble(i)));
                    wc.setLon(roundValue(lonArr.getDouble(k)));
                    for(int h = 0 ; h<=9 ; h++){
                            Weather w = makeDataSet(new Weather(),h, latArr.getDouble(i), lonArr.getDouble(k));
                            if(h==0){
                                inner.put("air_temperature_4",w.getAir_temperature_4());
                                inner.put("precipitation_amount_353", w.getPrecipitation_amount_353());
                                inner.put("eastward_wind_23",w.getEastward_wind_23());
                                inner.put("northward_wind_24",w.getNorthward_wind_24());
                            }else if(h==1){
                                inner.put("air_temperature_4_1h",w.getAir_temperature_4());
                                inner.put("precipitation_amount_353_1h", w.getPrecipitation_amount_353());
                                inner.put("eastward_wind_23_1h",w.getEastward_wind_23());
                                inner.put("northward_wind_24_1h",w.getNorthward_wind_24());
                            }else if(h==2){
                                inner.put("air_temperature_4_2h",w.getAir_temperature_4());
                                inner.put("precipitation_amount_353_2h", w.getPrecipitation_amount_353());
                                inner.put("eastward_wind_23_2h",w.getEastward_wind_23());
                                inner.put("northward_wind_24_2h",w.getNorthward_wind_24());
                            }else if(h==3){
                                inner.put("air_temperature_4_3h",w.getAir_temperature_4());
                                inner.put("precipitation_amount_353_3h", w.getPrecipitation_amount_353());
                                inner.put("eastward_wind_23_3h",w.getEastward_wind_23());
                                inner.put("northward_wind_24_3h",w.getNorthward_wind_24());
                            }else if(h==4){
                                inner.put("air_temperature_4_4h",w.getAir_temperature_4());
                                inner.put("precipitation_amount_353_4h", w.getPrecipitation_amount_353());
                                inner.put("eastward_wind_23_4h",w.getEastward_wind_23());
                                inner.put("northward_wind_24_4h",w.getNorthward_wind_24());
                            }else if(h==5){
                                inner.put("air_temperature_4_5h",w.getAir_temperature_4());
                                inner.put("precipitation_amount_353_5h", w.getPrecipitation_amount_353());
                                inner.put("eastward_wind_23_5h",w.getEastward_wind_23());
                                inner.put("northward_wind_24_5h",w.getNorthward_wind_24());
                            }else if(h==6){
                                inner.put("air_temperature_4_6h",w.getAir_temperature_4());
                                inner.put("precipitation_amount_353_6h", w.getPrecipitation_amount_353());
                                inner.put("eastward_wind_23_6h",w.getEastward_wind_23());
                                inner.put("northward_wind_24_6h",w.getNorthward_wind_24());
                            }else if(h==7){
                                inner.put("air_temperature_4_7h",w.getAir_temperature_4());
                                inner.put("precipitation_amount_353_7h", w.getPrecipitation_amount_353());
                                inner.put("eastward_wind_23_7h",w.getEastward_wind_23());
                                inner.put("northward_wind_24_7h",w.getNorthward_wind_24());
                            }else if(h==8){
                                inner.put("air_temperature_4_8h",w.getAir_temperature_4());
                                inner.put("precipitation_amount_353_8h", w.getPrecipitation_amount_353());
                                inner.put("eastward_wind_23_8h",w.getEastward_wind_23());
                                inner.put("northward_wind_24_8h",w.getNorthward_wind_24());
                            }else if(h==9){
                                inner.put("air_temperature_4_9h",w.getAir_temperature_4());
                                inner.put("precipitation_amount_353_9h", w.getPrecipitation_amount_353());
                                inner.put("eastward_wind_23_9h",w.getEastward_wind_23());
                                inner.put("northward_wind_24_9h",w.getNorthward_wind_24());
                            }
                    }
                        if(i==latArr.getSize()-1 && k==lonArr.getSize()-1){
                            print.println("\""+roundValue(latArr.getDouble(i))+" "+roundValue(lonArr.getDouble(k))+"\": "+inner);
                        }else if (i<latArr.getSize() && k<lonArr.getSize()){
                            print.println("\""+roundValue(latArr.getDouble(i))+" "+roundValue(lonArr.getDouble(k))+"\": "+inner+", ");
                        }




                }
            }
            print.println("}");
            print.close();

        }


        public Weather makeDataSet(Weather weather, int time, double lat, double lon) throws IOException, InvalidRangeException {

            // TODO: find out if it is possible
            // to get all four hours's data in to a single Weather

            NetCdfHandler handler = new NetCdfHandler();

            // TODO: move location path to application.properties file
            String location = locationMain;

            String datatype = "air_temperature_4";
            double val = handler.getaData(time, location, lat, lon, datatype) ;

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


            return weather;
        }

        private  double roundValue(double val) {

            NumberFormat nf = NumberFormat.getInstance();
            nf.setMinimumFractionDigits(0);
            nf.setMaximumFractionDigits(5);

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

        public Array roundArray(Array array){

            for(int p = 0; p < array.getSize(); p++){
                double value = roundValue(array.getDouble(p));
                array.setDouble(p, value);
            }

            return array;
        }

}
