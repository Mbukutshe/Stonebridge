package wiseman.stonebridge.Services;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import wiseman.stonebridge.Objects.Groups;

/**
 * Created by Wiseman on 2017-10-19.
 */

public class GroupsJSONParser {

    static List<Groups> itemList;

    public static List<Groups> parseData(String content) {

        JSONArray items_arry = null;
        Groups items = null;
        try {

            items_arry = new JSONArray(content);
            itemList = new ArrayList<>();

            for (int i = 0; i < items_arry.length(); i++) {

                JSONObject obj = items_arry.getJSONObject(i);
                items = new Groups(obj.getString("group_name").toString());
                itemList.add(items);
            }

        }
        catch (JSONException ex) {
            ex.printStackTrace();
        }
        return itemList;
    }

}
