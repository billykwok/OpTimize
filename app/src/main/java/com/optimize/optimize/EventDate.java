package com.optimize.optimize;

import java.text.DateFormat;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by BillyKwok on 20/1/15.
 */
public class EventDate {

    int year;
    int month;
    int day;

    public EventDate(int y, int m, int d) {
        year = y;
        month = m;
        day = d;
    }

    public EventDate() {
        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH);
        day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    public EventDate setYear(int y) {
        year = y;
        return this;
    }

    public EventDate setMonth(int m) {
        month = m;
        return this;
    }

    public EventDate setDay(int d) {
        day = d;
        return this;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public String toString() {
        DateFormat df = DateFormat.getDateInstance();
        return df.format(new GregorianCalendar(year, month, day).getTime());
    }

}
