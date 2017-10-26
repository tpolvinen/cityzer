import org.json.JSONException;
import ucar.ma2.InvalidRangeException;

import java.io.IOException;

public class Main  {

    public static void main(String [ ] args){
        DatasetManager dm;
        try {
            dm = new DatasetManager();
            dm.databaseHandler();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidRangeException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
