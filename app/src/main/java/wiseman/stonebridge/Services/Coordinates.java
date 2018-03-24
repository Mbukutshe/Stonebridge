package wiseman.stonebridge.Services;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import wiseman.stonebridge.Objects.MapCoordinates;

/**
 * Created by Wiseman on 2018-02-06.
 */

public class Coordinates {
    static List<MapCoordinates> itemList;
    public static List<MapCoordinates> parseData(String content) {

        JSONArray items_arry = null;
        MapCoordinates items = null;
        try {

            items_arry = new JSONArray(content);
            itemList = new ArrayList<>();

            for (int i = 0; i < items_arry.length(); i++) {

                JSONObject obj = items_arry.getJSONObject(i);
                items = new MapCoordinates(obj.getString("device_lati"),obj.getString("device_longi"));
                itemList.add(items);
            }

        }
        catch (JSONException ex) {
            ex.printStackTrace();
        }
        return itemList;
    }
}
