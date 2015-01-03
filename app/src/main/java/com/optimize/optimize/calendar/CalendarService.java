package com.optimize.optimize.calendar;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.text.format.DateUtils;
import android.util.Log;
import java.util.Calendar;
import java.util.Date;
import android.content.ContentValues;
import java.util.List;
import java.util.TimeZone;
import android.content.Context;

public class CalendarService{

    public static CalendarEvent createEvent(String title, int startYear, int startMonth, int startDay, int startHour, int startMin,
                                              int endYear, int endMonth, int endDay, int endHour, int endMin, boolean recur){
        long startMillis = 0;
        long endMillis = 0;
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(startYear, startMonth, startDay, startHour, startMin);
        startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(endYear, endMonth, endDay, endHour, endMin);
        endMillis = endTime.getTimeInMillis();

        CalendarEvent newEvent = new CalendarEvent(title, startMillis, endMillis, recur);
        return newEvent;
    }

    public static void addEvent(Context context, CalendarEvent event, String id){
        ContentResolver cr = context.getContentResolver();
        ContentValues values = new ContentValues();
        TimeZone timeZone = TimeZone.getDefault();
        values.put(CalendarContract.Events.DTSTART, event.getBegin());
        values.put(CalendarContract.Events.DTEND, event.getEnd());
        values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
        values.put(CalendarContract.Events.TITLE, event.getTitle());
        values.put(CalendarContract.Events.DESCRIPTION, "description here");
        values.put(CalendarContract.Events.CALENDAR_ID, id);
        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values); //in case need to retrieve any info
    }

    public static String getCalendarId(Context context){
        String[] projection = new String[]{CalendarContract.Calendars._ID};
        Cursor cursor = null;
        ContentResolver cr = context.getContentResolver();
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        cursor = cr.query(uri, projection, null, null, null);
        String id = null;
        while (cursor.moveToNext()) {
             id = cursor.getString(0);
        }
        cursor.close();
        return id;
    }

    public static void processEventList(Context context, int days, int hours, String id, List<CalendarEvent> eventList) {
        String[] eventProjection = new String[]{
                CalendarContract.Events.TITLE,
                CalendarContract.Instances.BEGIN,
                CalendarContract.Instances.END,
                CalendarContract.Events.ALL_DAY
        };

        Cursor eventCursor = null;
        ContentResolver cr = context.getContentResolver();
        Uri uri = CalendarContract.Instances.CONTENT_URI;

        Uri.Builder eventUriBuilder = uri.buildUpon();
        long now = new Date().getTime();
        long beforeNow = now - (DateUtils.DAY_IN_MILLIS * days) - (DateUtils.HOUR_IN_MILLIS * hours);
        long afterNow = now + (DateUtils.DAY_IN_MILLIS * days) + (DateUtils.HOUR_IN_MILLIS * hours);

        // create the time span based on the inputs
        ContentUris.appendId(eventUriBuilder, beforeNow);
        ContentUris.appendId(eventUriBuilder,afterNow);

        eventCursor = cr.query(eventUriBuilder.build(), eventProjection, "(Events.CALENDAR_ID=" + id + ")", null, "startDay ASC, startMinute ASC");

        while (eventCursor.moveToNext())
        {
            CalendarEvent ce = loadEvent(eventCursor);
            eventList.add(ce);
        }
    }


    private static CalendarEvent loadEvent(Cursor csr) {
//        Log.i("Return", csr.getString(0) + " " + csr.getLong(1) + " " + csr.getLong(2));
        return new CalendarEvent(csr.getString(0), csr.getLong(1), csr.getLong(2), !csr.getString(3).equals("0"));
    }

    public static void printArrayList(List<CalendarEvent> eventList){
        for(CalendarEvent ce: eventList){
            Log.i("EventList", ce.toString());
        }
    }
}
