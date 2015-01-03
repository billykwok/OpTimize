package com.optimize.optimize.calendar;

import java.util.Date;

/**
 * Created by james on 21/12/14.
 */
public class CalendarEvent {
    private String title;
    private long begin, end;
    private boolean allDay;

    public CalendarEvent() {
    }

    public CalendarEvent(String title, long begin, long end, boolean allDay) {
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
    }
    public long getBegin() {
        return begin;
    }
    public void setBegin(long begin) {
        this.begin = begin;
    }
    public long getEnd() {
        return end;
    }
    public void setEnd(long end) {
        this.end = end;
    }
    public boolean isAllDay() {
        return allDay;
    }
    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    @Override
    public String toString(){
        return getTitle() + " " + getBegin() + " " + getEnd() + " " + isAllDay();
    }

}
