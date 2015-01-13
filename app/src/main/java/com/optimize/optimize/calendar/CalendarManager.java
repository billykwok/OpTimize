package com.optimize.optimize.calendar;

import android.util.Log;

import com.optimize.optimize.WithInType;
import com.optimize.optimize.models.OTUserService;

import java.util.List;

/**
 * Created by james on 7/1/15.
 */
public class CalendarManager {

    private int startHour;
    private int endHour;
    private WithInType withIn;
    private int duration;
    private List<OTUserService> users;

    final String TAG = "CalenderManager";

    private static CalendarManager ourInstance = new CalendarManager();

    public static CalendarManager getInstance() {
        return ourInstance;
    }

    private CalendarManager() {
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setUsers(List<OTUserService> users) {
        this.users = users;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
        Log.d(TAG, String.valueOf(startHour));
    }


    public void setEndHour(int endHour) {
        this.endHour = endHour;
        Log.d(TAG, String.valueOf(endHour));
    }

    public void setWithIn(WithInType withIn) {
        this.withIn = withIn;
        Log.d(TAG, withIn.toString());
    }

}
