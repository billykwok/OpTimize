package com.optimize.optimize.models;

import android.util.Log;

import com.google.gson.Gson;
import com.optimize.optimize.calendar.CalendarEvent;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samwalker on 7/1/15.
 */
public class OTUserService {
    private List<CalendarEvent> eventList;
    private String calendarId;
    private List<String> otEventsId;

    final String TAG = "OTUserService";

    public OTUserService() {
        eventList = new ArrayList<CalendarEvent>();
        calendarId = "";
        otEventsId = new ArrayList<String>();
    }

    public List<String> getOtEventsId() {
        return otEventsId;
    }

    public List<String> getOTEventsIdFromJSON(ParseUser parseUser) {
        JSONArray otEventIdsJson = parseUser.getJSONArray("otEventsId");
        List<String> otEventIdsTemp = new ArrayList<String>();
        if (otEventIdsJson != null) {
            for (int i = 0; i < otEventIdsJson.length(); i++ ) {
                String otEventId = otEventIdsJson.optString(i);
                otEventIdsTemp.add(otEventId);
            }
        } else {
            Log.d(TAG, "otEventIdsJson is null");
        }
        otEventsId = otEventIdsTemp;
        return otEventsId;
    }


    public void setOtEventsId(List<String> otEventsId, ParseUser parseUser) {
        this.otEventsId = otEventsId;
        JSONArray eventsIdJSON = new JSONArray(otEventsId);
        parseUser.put("otEventsId", otEventsId);
    }


    public String getCalendarId(){return calendarId;}
    public List<CalendarEvent> getEventList(ParseUser parseUser){
        if (eventList == null) {
            getEventsFromJSON(parseUser);
        }
        return eventList;
    }

    public List<CalendarEvent> getEventsFromJSON(ParseUser parseUser) {
        JSONArray calendarEventJsons = (JSONArray) parseUser.get("calendarEvents");
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

    public void setEventList(List<CalendarEvent> eventList, ParseUser parseUser) {
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
        parseUser.put("calendarEvents", eventsJSON);
    }

    public void setCalendarId(String calendarId) {
        this.calendarId = calendarId;
    }


}
