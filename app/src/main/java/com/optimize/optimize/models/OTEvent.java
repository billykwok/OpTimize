package com.optimize.optimize.models;

import android.util.Log;

import com.google.gson.Gson;
import com.optimize.optimize.calendar.CalendarEvent;
import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samwalker on 10/1/15.
 */
@ParseClassName("OTEvent")
public class OTEvent extends ParseObject{
    private String title;
    private String venue;
    private String hostId;
    private long begin, end;
    private boolean allDay;
    private String description;
    private List<Participant> participants;

    public OTEvent(String title, long begin, long end, String venue, String description) {
        setTitle(title);
        setBegin(begin);
        setEnd(end);
        setDescription(description);
        setVenue(venue);
    }

    public String getTitle() {
        return getString("title");
    }

    public void setTitle(String title) {
        this.title = title;
        put("title", title);
    }

    public String getVenue() {
        return getString("venue");
    }

    public void setVenue(String venue) {
        this.venue = venue;
        put("venue", venue);
    }

    public String getHostId() {
        return getString("hostId");
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
        put("hostId", hostId);
    }

    public long getBegin() {
        return getLong("begin");
    }

    public void setBegin(long begin) {
        this.begin = begin;
        put("begin", begin);
    }

    public long getEnd() {
        return getLong("end");
    }

    public void setEnd(long end) {
        this.end = end;
        put("end", end);
    }

    public boolean isAllDay() {
        return getBoolean("allDay");
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
        put("allDay", allDay);
    }

    public String getDescription() {
        return getString("description");
    }

    public void setDescription(String description) {
        this.description = description;
        put("description", description);
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public List<Participant> getParticipantsFromJSON() {
        JSONArray participantsJson = (JSONArray) get("participants");
        List<Participant> participantList = new ArrayList<Participant>();
        if (participantsJson != null) {
            for (int i = 0; i < participantsJson.length(); i++) {
                JSONObject participantJsonObj = participantsJson.optJSONObject(i);
                Gson g = new Gson();
                Participant participant = g.fromJson(participantJsonObj.toString(), Participant.class);
                participantList.add(participant);
            }
        } else {
            Log.e("OTEvent", "participantsJson is null");
        }
        participants = participantList;
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
        JSONArray participantsJSON = new JSONArray();
        Gson g = new Gson();
        for (Participant participant: participants) {
            String j = g.toJson(participant);
            JSONObject eventJson = null;
            try {
                eventJson = new JSONObject(j);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            participantsJSON.put(eventJson);
        }
        put("participants", participantsJSON);
    }

}
