package com.optimize.optimize.models;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by samwalker on 10/1/15.
 */
public class Participant {
    private String userId;
    private boolean accepted;

    final String PARTICIPANT = "Participant";

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public JSONObject toJson() {
        Gson g = new Gson();
        String jsonString = g.toJson(this);
        Log.d(PARTICIPANT, jsonString);
        JSONObject json = null;
        try {
            json = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
