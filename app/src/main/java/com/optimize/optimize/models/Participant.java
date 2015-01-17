package com.optimize.optimize.models;

import android.util.Log;

import com.google.gson.Gson;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

    public static List<Participant> fromParseUsers(List<ParseUser> parseUsers) {
        List<Participant> participants = new ArrayList<>();
        for (ParseUser parseUser: parseUsers) {
            Participant participant = new Participant();
            participant.setUserId(parseUser.getObjectId());
            participant.setAccepted(false);
            participants.add(participant);
        }
        return participants;
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
