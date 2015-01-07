package com.optimize.optimize.models;

import com.optimize.optimize.calendar.CalendarEvent;
import com.parse.ParseUser;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by samwalker on 7/1/15.
 */
public class OTUser extends ParseUser{
    private List<CalendarEvent> eventList;
    private String calendarId;
    private String[] otEvents;

    public OTUser() {

    }

    public OTUser (JSONObject otUserJson) {

    }

    public OTUser(List<CalendarEvent> eventList, String id){
        this.eventList = eventList;
        this.calendarId = id;
    }

    public String getCalendarId(){return calendarId;}
    public List<CalendarEvent> getEventList(){return eventList;}

    @Override
    public String toString(){
        return calendarId + " " + eventList;
    }

    public void setEventList(List<CalendarEvent> eventList) {
        this.eventList = eventList;
    }

    public void setCalendarId(String calendarId) {
        this.calendarId = calendarId;
    }
}
