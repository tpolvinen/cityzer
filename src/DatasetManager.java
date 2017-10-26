import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import ucar.ma2.Array;
import ucar.ma2.InvalidRangeException;
import ucar.nc2.NetcdfFile;


public class DatasetManager {

        final String locationMain = "/var/www/html/dataCDF.nc";
        NetcdfFile ncfile = null;
        public DatasetManager() throws IOException, InvalidRangeException {

        }


        public void databaseHandler() throws IOException, InvalidRangeException, JSONException {
            ncfile = NetcdfFile.open(locationMain);
            NetCdfHandler handlerMain = new NetCdfHandler();
            handlerMain.getHoursStart(ncfile);
            ArrayList<WeatherCollection> weatherlist = new ArrayList<>();
            Array latArr = handlerMain.getLat(ncfile);
            Array lonArr = handlerMain.getLon(ncfile);
            for (int i=0; i < latArr.getSize(); i++){
                for (int k=0; k<lonArr.getSize();k++){
                    WeatherCollection wc = new WeatherCollection();
                    wc.setLat(latArr.getDouble(i));
                    wc.setLon(lonArr.getDouble(k));
                    for(int h = 0 ; h<9 ; h++){
                            Weather w = makeDataSet(new Weather(),h, latArr.getDouble(i), lonArr.getDouble(k));
                            if(h==0){
                                wc.setAir_temperature_4(w.getAir_temperature_4());
                                wc.setPrecipitation_amount_353(w.getPrecipitation_amount_353());
                                wc.setCloud_area_fraction_79(w.getCloud_area_fraction_79());
                                wc.setEastward_wind_23(w.getEastward_wind_23());
                                wc.setNorthward_wind_24(w.getNorthward_wind_24());
                            }else if(h==1){
                                wc.setAir_temperature_4_1h(w.getAir_temperature_4());
                                wc.setPrecipitation_amount_353_1h(w.getPrecipitation_amount_353());
                                wc.setCloud_area_fraction_79_1h(w.getCloud_area_fraction_79());
                                wc.setEastward_wind_23_1h(w.getEastward_wind_23());
                                wc.setNorthward_wind_24_1h(w.getNorthward_wind_24());
                            }else if(h==2){
                                wc.setAir_temperature_4_2h(w.getAir_temperature_4());
                                wc.setPrecipitation_amount_353_2h(w.getPrecipitation_amount_353());
                                wc.setCloud_area_fraction_79_2h(w.getCloud_area_fraction_79());
                                wc.setEastward_wind_23_2h(w.getEastward_wind_23());
                                wc.setNorthward_wind_24_2h(w.getNorthward_wind_24());
                            }else if(h==3){
                                wc.setAir_temperature_4_3h(w.getAir_temperature_4());
                                wc.setPrecipitation_amount_353_3h(w.getPrecipitation_amount_353());
                                wc.setCloud_area_fraction_79_3h(w.getCloud_area_fraction_79());
                                wc.setEastward_wind_23_3h(w.getEastward_wind_23());
                                wc.setNorthward_wind_24_3h(w.getNorthward_wind_24());
                            }else if(h==4){
                                wc.setAir_temperature_4_4h(w.getAir_temperature_4());
                                wc.setPrecipitation_amount_353_4h(w.getPrecipitation_amount_353());
                                wc.setCloud_area_fraction_79_4h(w.getCloud_area_fraction_79());
                                wc.setEastward_wind_23_4h(w.getEastward_wind_23());
                                wc.setNorthward_wind_24_4h(w.getNorthward_wind_24());
                            }else if(h==5){
                                wc.setAir_temperature_4_5h(w.getAir_temperature_4());
                                wc.setPrecipitation_amount_353_5h(w.getPrecipitation_amount_353());
                                wc.setCloud_area_fraction_79_5h(w.getCloud_area_fraction_79());
                                wc.setEastward_wind_23_5h(w.getEastward_wind_23());
                                wc.setNorthward_wind_24_5h(w.getNorthward_wind_24());
                            }else if(h==6){
                                wc.setAir_temperature_4_6h(w.getAir_temperature_4());
                                wc.setPrecipitation_amount_353_6h(w.getPrecipitation_amount_353());
                                wc.setCloud_area_fraction_79_6h(w.getCloud_area_fraction_79());
                                wc.setEastward_wind_23_6h(w.getEastward_wind_23());
                                wc.setNorthward_wind_24_6h(w.getNorthward_wind_24());
                            }else if(h==7){
                                wc.setAir_temperature_4_7h(w.getAir_temperature_4());
                                wc.setPrecipitation_amount_353_7h(w.getPrecipitation_amount_353());
                                wc.setCloud_area_fraction_79_7h(w.getCloud_area_fraction_79());
                                wc.setEastward_wind_23_7h(w.getEastward_wind_23());
                                wc.setNorthward_wind_24_7h(w.getNorthward_wind_24());
                            }else if(h==8){
                                wc.setAir_temperature_4_8h(w.getAir_temperature_4());
                                wc.setPrecipitation_amount_353_8h(w.getPrecipitation_amount_353());
                                wc.setCloud_area_fraction_79_8h(w.getCloud_area_fraction_79());
                                wc.setEastward_wind_23_8h(w.getEastward_wind_23());
                                wc.setNorthward_wind_24_8h(w.getNorthward_wind_24());
                            }else if(h==9){
                                wc.setAir_temperature_4_9h(w.getAir_temperature_4());
                                wc.setPrecipitation_amount_353_9h(w.getPrecipitation_amount_353());
                                wc.setCloud_area_fraction_79_9h(w.getCloud_area_fraction_79());
                                wc.setEastward_wind_23_9h(w.getEastward_wind_23());
                                wc.setNorthward_wind_24_9h(w.getNorthward_wind_24());
                            }
                    }

                    weatherlist.add(wc);
                }
            }
            PrintWriter print = null;
            File file = new File("/var/www/html/api/outputJSON.json");
            PrintWriter pw = new PrintWriter(file);
            pw.close();
            print = new PrintWriter(new FileWriter(file, true));


            JSONObject outer = new JSONObject();
            outer.put("hours since", handlerMain.getHoursStart(ncfile));
            for (WeatherCollection x:weatherlist){
                JSONObject inner = new JSONObject();

                try {
                    inner.put("time", 0);
                    inner.put("time_h", 0);

                    inner.put("lat", x.getLat());
                    inner.put("lon", x.getLon());

                    inner.put("precipitation_amount_353", x.getPrecipitation_amount_353());
                    inner.put("precipitation_amount_353_1h",x.getPrecipitation_amount_353_1h());
                    inner.put("precipitation_amount_353_2h",x.getPrecipitation_amount_353_2h());
                    inner.put("precipitation_amount_353_3h",x.getPrecipitation_amount_353_3h());
                    inner.put("precipitation_amount_353_4h",x.getPrecipitation_amount_353_4h());
                    inner.put("precipitation_amount_353_5h",x.getPrecipitation_amount_353_5h());
                    inner.put("precipitation_amount_353_6h",x.getPrecipitation_amount_353_6h());
                    inner.put("precipitation_amount_353_7h",x.getPrecipitation_amount_353_7h());
                    inner.put("precipitation_amount_353_8h",x.getPrecipitation_amount_353_8h());
                    inner.put("precipitation_amount_353_9h",x.getPrecipitation_amount_353_9h());

                    inner.put("air_temperature_4",x.getAir_temperature_4());
                    inner.put("air_temperature_4_1h",x.getAir_temperature_4_1h());
                    inner.put("air_temperature_4_2h",x.getAir_temperature_4_2h());
                    inner.put("air_temperature_4_3h",x.getAir_temperature_4_3h());
                    inner.put("air_temperature_4_4h",x.getAir_temperature_4_4h());
                    inner.put("air_temperature_4_5h",x.getAir_temperature_4_5h());
                    inner.put("air_temperature_4_6h",x.getAir_temperature_4_6h());
                    inner.put("air_temperature_4_7h",x.getAir_temperature_4_7h());
                    inner.put("air_temperature_4_8h",x.getAir_temperature_4_8h());
                    inner.put("air_temperature_4_9h",x.getAir_temperature_4_9h());

                    inner.put("eastward_wind_23",x.getEastward_wind_23());
                    inner.put("eastward_wind_23_1h",x.getEastward_wind_23_1h());
                    inner.put("eastward_wind_23_2h",x.getEastward_wind_23_2h());
                    inner.put("eastward_wind_23_3h",x.getEastward_wind_23_3h());
                    inner.put("eastward_wind_23_4h",x.getEastward_wind_23_4h());
                    inner.put("eastward_wind_23_5h",x.getEastward_wind_23_5h());
                    inner.put("eastward_wind_23_6h",x.getEastward_wind_23_6h());
                    inner.put("eastward_wind_23_7h",x.getEastward_wind_23_7h());
                    inner.put("eastward_wind_23_8h",x.getEastward_wind_23_8h());
                    inner.put("eastward_wind_23_9h",x.getEastward_wind_23_9h());

                    inner.put("northward_wind_24",x.getNorthward_wind_24());
                    inner.put("northward_wind_24_1h",x.getNorthward_wind_24_1h());
                    inner.put("northward_wind_24_2h",x.getNorthward_wind_24_2h());
                    inner.put("northward_wind_24_3h",x.getNorthward_wind_24_3h());
                    inner.put("northward_wind_24_4h",x.getNorthward_wind_24_4h());
                    inner.put("northward_wind_24_5h",x.getNorthward_wind_24_5h());
                    inner.put("northward_wind_24_6h",x.getNorthward_wind_24_6h());
                    inner.put("northward_wind_24_7h",x.getNorthward_wind_24_7h());
                    inner.put("northward_wind_24_8h",x.getNorthward_wind_24_8h());
                    inner.put("northward_wind_24_9h",x.getNorthward_wind_24_9h());

                    inner.put("inrange", true);
                    inner.put("success", true);
                    inner.put("message", "Placeholder");
                    outer.put(x.getLat()+" "+x.getLon(), inner);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            print.println(outer);
            print.close();

        }


        public Weather makeDataSet(Weather weather, int time, double lat, double lon) throws IOException, InvalidRangeException {

            // TODO: find out if it is possible to get all four hours's data in to a single Weather

            NetCdfHandler handler = new NetCdfHandler();

            // TODO: move location path to application.properties file
            String location = locationMain;

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


            return weather;
        }

        private  double roundValue(double val) {

            NumberFormat nf = NumberFormat.getInstance();
            nf.setMinimumFractionDigits(12);

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

}
