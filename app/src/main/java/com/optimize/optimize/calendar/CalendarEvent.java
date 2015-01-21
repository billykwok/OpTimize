package com.optimize.optimize.calendar;

import android.util.Log;

import com.google.gson.Gson;
import com.optimize.optimize.utilities.DateUtils;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by james on 21/12/14.
 */
public class CalendarEvent {
    private String title;
    private long begin, end;
    private boolean allDay;

    public static final String CALENDAR_EVENT = "CalendarEvent";

    public CalendarEvent() {
    }

    public CalendarEvent(String title, long begin, long end) {
        setTitle(title);
        setBegin(begin);
        setEnd(end);
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

    @Override
    public String toString(){
        return getTitle() + " " + getBegin() + " " + getEnd() + " " + DateUtils.convertDate(getBegin(), "dd/MM hh:mm AA" + " " + DateUtils.convertDate(getEnd(), "dd/MM hh:mm AA"));
    }

    public JSONObject toJson() {
        Gson g = new Gson();
        String jsonString = g.toJson(this);
        Log.d(CALENDAR_EVENT, jsonString);
        JSONObject json = null;
        try {
            json = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

}
