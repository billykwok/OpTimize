package com.optimize.optimize;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import android.content.ContentValues;
import java.util.List;
import java.util.TimeZone;

import android.content.Context;

public class CalendarService extends Activity implements View.OnClickListener {
    String id;
    static List<CalendarEvent> eventList = new ArrayList<CalendarEvent>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button list = (Button)findViewById(R.id.listCalendarButton);
        list.setOnClickListener(this);

        Button add = (Button)findViewById(R.id.addEventButton);
        add.setOnClickListener(this);
    }

    public void onClick(View v) {
        Log.i("afterClick", "Success");
        switch (v.getId()) {
            case R.id.listCalendarButton:
                readCalendar(this.getApplicationContext());
                listEvents(this.getApplicationContext(), 5, 0, id);
                printArrayList();
                break;
            case R.id.addEventButton:
                Log.i("hi", "Success");
                addEvent(this.getApplicationContext(),createEvent("TestX", 2014, 12, 25, 9, 0, 2014, 12, 25, 11, 0, false), id);
                break;
        }
    }

    public List<CalendarEvent> getEventList(Context context, int days, int hours){
        readCalendar(context);
        listEvents(context, days, hours, id);
        return eventList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onClickListCalendars(MenuItem item){
        readCalendar(this.getApplicationContext());
    }

    private static CalendarEvent createEvent(String title, int startYear, int startMonth, int startDay, int startHour, int startMin,
                                              int endYear, int endMonth, int endDay, int endHour, int endMin, boolean recur){
        Log.i("enterCreate", "Success");
        long startMillis = 0;
        long endMillis = 0;
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(startYear, startMonth, startDay, startHour, startMin);
        startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(endYear, endMonth, endDay, endHour, endMin);
        endMillis = endTime.getTimeInMillis();

        CalendarEvent newEvent = new CalendarEvent(title, startMillis, endMillis, recur);
//        Log.i("leaveCreate", "Success");
        return newEvent;
    }

    public static void addEvent(Context context, CalendarEvent event, String id){
        Log.i("enterAdd", "Success");
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
        Log.i("leaveAdd", "Success");
    }

    public void readCalendar(Context context) {
        Log.i("enterListCalendars", "Success");
        String[] projection = new String[]{
                CalendarContract.Calendars._ID,                     //0
                CalendarContract.Calendars.ACCOUNT_NAME,            //1
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,   //2
                CalendarContract.Calendars.ACCOUNT_TYPE             //3
        };
        Cursor cursor = null;
        ContentResolver cr = context.getContentResolver();
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        cursor = cr.query(uri, projection, null, null, null);

        while (cursor.moveToNext()) {
            long calID = 0;
            String displayName = null;
            String accountName = null;
            String accountType = null;

            id = cursor.getString(0);
            calID = cursor.getLong(0);
            displayName = cursor.getString(1);
            accountName = cursor.getString(2);
            accountType = cursor.getString(3);
            Log.i("Return", calID + " " + displayName + " " + accountName + " " + accountType);
        }
        cursor.close();
    }

    public void listEvents(Context context, int days, int hours, String id) {
        Log.i("enterListEvents", "Success");
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
//        Log.i("END","END");
    }

    private static CalendarEvent loadEvent(Cursor csr) {
        Log.i("Return", csr.getString(0) + " " + csr.getLong(1) + " " + csr.getLong(2));
        return new CalendarEvent(csr.getString(0), csr.getLong(1), csr.getLong(2), !csr.getString(3).equals("0"));
    }

    private static void printArrayList(){
        for(CalendarEvent ce: eventList){
            Log.i("EventList", ce.toString());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
