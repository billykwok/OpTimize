package com.optimize.optimize;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by BillyKwok on 20/1/15.
 */
public class EventTime {

    int hour;
    int minute;

    public EventTime(int h, int m) {
        hour = h;
        minute = m;
    }

    public EventTime() {
        hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        minute = Calendar.getInstance().get(Calendar.MINUTE);
    }

    public EventTime setHour(int h) {
        hour = h;
        return this;
    }

    public EventTime setMinute(int m) {
        minute = m;
        return this;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String toString() {
        DateFormat df = DateFormat.getTimeInstance();
        return df.format(new GregorianCalendar(0, 0, 0, hour, minute).getTime());
    }

}
