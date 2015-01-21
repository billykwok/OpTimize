package com.optimize.optimize.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samwalker on 21/1/15.
 */
@ParseClassName("OTEventList")
public class OTEventList extends ParseObject{
    String userId;
    List<String> otEventId;

    public String getUserId() {
        return getString("userId");
    }

    public void setUserId(String userId) {
        this.userId = userId;
        put("userId", userId);
    }

    public List<String> getOtEventId() {
        if (otEventId == null) {
            otEventId = new ArrayList<>();
        }
        otEventId.clear();
        JSONArray array = getJSONArray("otEventsId");
        for (int i = 0; i < array.length(); i++) {
            try {
                String id = array.getString(i);
                otEventId.add(id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return otEventId;
    }

    public void setOtEventId(List<String> otEventId) {
        this.otEventId = otEventId;
        put("otEventsId", new JSONArray(otEventId));
    }
}
