package com.optimize.optimize.models;

import android.util.Log;

import com.google.gson.Gson;
import com.optimize.optimize.calendar.CalendarEvent;
import com.parse.ParseClassName;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samwalker on 7/1/15.
 */
@ParseClassName("OTUser")
public class OTUser extends ParseUser{
    private List<CalendarEvent> eventList;
    private String calendarId;
    private List<String> otEventsId;

    final String TAG = "OTUser";

    public OTUser() {
        eventList = new ArrayList<CalendarEvent>();
        calendarId = "";
        otEventsId = new ArrayList<String>();
    }

    public OTUser (JSONObject otUserJson) {
    }

    public List<String> getOtEventsId() {
        return otEventsId;
    }

    public List<String> getOTEventsIdFromJSON() {
        return otEventsId;
    }


    public void setOtEventsId(List<String> otEventsId) {
        this.otEventsId = otEventsId;
    }

    public OTUser(List<CalendarEvent> eventList, String id){
        this.eventList = eventList;
        this.calendarId = id;
    }

    public String getCalendarId(){return calendarId;}
    public List<CalendarEvent> getEventList(){
        if (eventList == null) {
            getEventsFromJSON();
        }
        return eventList;
    }

    public List<CalendarEvent> getEventsFromJSON() {
        JSONArray calendarEventJsons = (JSONArray) get("calendarEvents");
        List<CalendarEvent> calendarEvents = new ArrayList<CalendarEvent>();
        if (calendarEventJsons != null) {
            for (int i = 0; i < calendarEventJsons.length(); i++) {
                JSONObject calendarEventJsonObj = calendarEventJsons.optJSONObject(i);
                Gson g = new Gson();
                CalendarEvent calendarEvent = g.fromJson(calendarEventJsonObj.toString(), CalendarEvent.class);
                calendarEvents.add(calendarEvent);
            }
        } else {
            Log.e(TAG, "calendarEventJsons is null");
        }
        eventList = calendarEvents;
        return calendarEvents;
    }

    @Override
    public String toString(){
        return calendarId + " " + eventList;
    }

    public void setEventList(List<CalendarEvent> eventList) {
        this.eventList = eventList;
        JSONArray eventsJSON = new JSONArray();
        Gson g = new Gson();
        for (CalendarEvent event: eventList) {
            String j = g.toJson(event);
            JSONObject eventJson = null;
            try {
                eventJson = new JSONObject(j);
            } catch (JSONException e) {
                e.printStackTrace();
            }
           eventsJSON.put(eventJson);
        }
        put("calendarEvents", eventsJSON);
    }

    public void setCalendarId(String calendarId) {
        this.calendarId = calendarId;
    }

}
