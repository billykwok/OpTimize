package com.optimize.optimize.tasks;

import android.os.AsyncTask;
import android.text.format.DateUtils;
import android.util.Log;

import com.optimize.optimize.activities.OTEventManager;
import com.optimize.optimize.calendar.CalendarManager;
import com.optimize.optimize.calendar.TimeSlot;
import com.parse.ParseUser;

import java.util.Date;
import java.util.List;

/**
 * Created by samwalker on 18/1/15.
 */
public class GetOptimumTimeSlotsTask extends AsyncTask<Void, Void , List<TimeSlot>> {

    final String TAG = "AsyncTask";
    @Override
    protected List<TimeSlot> doInBackground(Void... params) {
        List<ParseUser> parseUsers = OTEventManager.getInstance().getParseUserList();
        CalendarManager cm = CalendarManager.getInstance();
        return CalendarManager.getOptimumTimeSlots(parseUsers, null, 3 * DateUtils.HOUR_IN_MILLIS, cm.getStartHour(), cm.getEndHour(), new Date().getTime(), (new Date().getTime() + 30 * DateUtils.DAY_IN_MILLIS), 3);
    }

    @Override
    protected void onPostExecute(List<TimeSlot> timeSlots) {
        super.onPostExecute(timeSlots);
        for (TimeSlot timeSlot: timeSlots) {
            Log.d(TAG, timeSlot.toString());
        }
    }
}
