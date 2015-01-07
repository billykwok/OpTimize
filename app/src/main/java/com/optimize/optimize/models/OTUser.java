package com.optimize.optimize.models;

import com.optimize.optimize.calendar.CalendarEvent;
import com.parse.ParseUser;
import java.util.List;

/**
 * Created by samwalker on 7/1/15.
 */
public class OTUser extends ParseUser{
    private List<CalendarEvent> eventList;
    private String calendarId;

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
}
