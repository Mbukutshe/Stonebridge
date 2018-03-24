package wiseman.stonebridge.Services;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import wiseman.stonebridge.Globals.UserDevice;
import wiseman.stonebridge.Globals.UserDevices;
import wiseman.stonebridge.Objects.MapCoordinates;

/**
 * Created by Wiseman on 2018-02-11.
 */

public class DevicesJSONParser {
    static List<MapCoordinates> itemList;
    public static void parseData(String content) {

        JSONArray items_arry = null;
        UserDevice items = null;
        try {

            items_arry = new JSONArray(content);
            itemList = new ArrayList<>();
            for (int i = 0; i < items_arry.length(); i++) {

                JSONObject obj = items_arry.getJSONObject(i);
                items = new UserDevice(obj.getString("device_token"));
                UserDevices.device.add(items);
            }

        }
        catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

}
