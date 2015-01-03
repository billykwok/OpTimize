package com.optimize.optimize.calendar;

import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.util.Date;

/**
 * Created by james on 21/12/14.
 */
public class CalendarEvent {
    private String title;
    private long begin, end;
    private boolean allDay;

    public static final String CALENDAR_EVENT = "CalendarEvent";
    private ParseObject parseCalendarEvent;

    public CalendarEvent() {
    }

    public CalendarEvent(String title, long begin, long end, boolean allDay) {
        parseCalendarEvent = new ParseObject(CALENDAR_EVENT);
        setTitle(title);
        setBegin(begin);
        setEnd(end);
        setAllDay(allDay);
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
        parseCalendarEvent.put("title", title);
    }
    public long getBegin() {
        return begin;
    }
    public void setBegin(long begin) {
        this.begin = begin;
        parseCalendarEvent.put("begin", begin);
    }
    public long getEnd() {
        return end;
    }
    public void setEnd(long end) {
        this.end = end;
        parseCalendarEvent.put("end", end);
    }
    public boolean isAllDay() {
        return allDay;
    }
    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
        parseCalendarEvent.put("allDay", allDay);
    }

    public void save() {
        parseCalendarEvent.saveInBackground();
        parseCalendarEvent.pinInBackground();
    }

    public void save(SaveCallback callback) {
        parseCalendarEvent.saveEventually(callback);
    }



    @Override
    public String toString(){
        return getTitle() + " " + getBegin() + " " + getEnd() + " " + isAllDay();
    }

}
