package com.optimize.optimize.calendar;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.text.format.DateUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import android.content.ContentValues;
import java.util.List;
import java.util.TimeZone;
import android.content.Context;

import com.optimize.optimize.models.OTEvent;

public class CalendarService{

    public static CalendarEvent createEvent(String title, int startYear, int startMonth, int startDay, int startHour, int startMin,
                                              int endYear, int endMonth, int endDay, int endHour, int endMin){
        long startMillis = 0;
        long endMillis = 0;
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(startYear, startMonth, startDay, startHour, startMin);
        startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(endYear, endMonth, endDay, endHour, endMin);
        endMillis = endTime.getTimeInMillis();

        CalendarEvent newEvent = new CalendarEvent(title, startMillis, endMillis);
        return newEvent;
    }

    public static void exportEvent(Context context, CalendarEvent event, String description, String location, String id){
        Log.i("Enter Export", "Success with id: "+id);
        ContentResolver cr = context.getContentResolver();
        ContentValues values = new ContentValues();
        TimeZone timeZone = TimeZone.getDefault();
        values.put(CalendarContract.Events.DTSTART, event.getBegin());
        values.put(CalendarContract.Events.DTEND, event.getEnd());
        values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
        values.put(CalendarContract.Events.TITLE, event.getTitle());
        values.put(CalendarContract.Events.EVENT_LOCATION, location);
        values.put(CalendarContract.Events.DESCRIPTION, description);
        values.put(CalendarContract.Events.CALENDAR_ID, id);
        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values); //in case need to retrieve any info
    }

    public static List<String> getCalendarIdList(Context context) {
        String[] projection = new String[]{CalendarContract.Calendars._ID};
        Cursor cursor = null;
        ContentResolver cr = context.getContentResolver();
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        cursor = cr.query(uri, projection, null, null, null);
        List<String> ids = new ArrayList<String>();
        while (cursor.moveToNext()) {
            ids.add(cursor.getString(0));
        }
        cursor.close();
        return ids;
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

    public static List<CalendarEvent> getEventList(Context context, List<String> ids) {

        String[] eventProjection = new String[]{
                CalendarContract.Events.TITLE,
                CalendarContract.Instances.BEGIN,
                CalendarContract.Instances.END,
        };

        Cursor eventCursor = null;
        ContentResolver cr = context.getContentResolver();
        Uri uri = CalendarContract.Instances.CONTENT_URI;

        Uri.Builder eventUriBuilder = uri.buildUpon();
        long now = new Date().getTime();
        long beforeNow = now;
        long afterNow = now + (DateUtils.DAY_IN_MILLIS * 60) + (DateUtils.HOUR_IN_MILLIS * 0); //read 60 days' events

        // create the time span based on the inputs
        ContentUris.appendId(eventUriBuilder, beforeNow);
        ContentUris.appendId(eventUriBuilder,afterNow);

        List<CalendarEvent> eventList = new ArrayList<CalendarEvent>();

        for(int i = 0; i < ids.size(); ++i) {
            eventCursor = cr.query(eventUriBuilder.build(), eventProjection, "(Events.CALENDAR_ID=" + ids.get(i) + ")", null, "startDay ASC, startMinute ASC");

            while (eventCursor.moveToNext()) {

                CalendarEvent ce = loadEvent(eventCursor);
                eventList.add(ce);
            }
        }

        if(eventList.size() == 0)
            Log.i("Event List","Empty Event List");
        else
            Log.i("Event List","Not Empty Event List");

        return eventList;
    }


    private static CalendarEvent loadEvent(Cursor csr) {
//        Log.i("Return", csr.getString(0) + " " + csr.getLong(1) + " " + csr.getLong(2));
        return new CalendarEvent(csr.getString(0), csr.getLong(1), csr.getLong(2));
    }

    public static void printArrayList(List<CalendarEvent> eventList){
        for(CalendarEvent ce: eventList){
            Log.i("EventList", ce.toString());
        }
    }
}
