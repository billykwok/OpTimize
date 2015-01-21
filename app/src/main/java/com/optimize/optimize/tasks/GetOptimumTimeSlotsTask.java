package com.optimize.optimize.tasks;

import android.os.AsyncTask;
import android.text.format.DateUtils;
import android.util.Log;

import com.optimize.optimize.R;
import com.optimize.optimize.activities.CreateEventActivity;
import com.optimize.optimize.activities.OTActionBarActivity;
import com.optimize.optimize.managers.OTEventManager;
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
    CreateEventActivity createEventActivity;

    public GetOptimumTimeSlotsTask(OTActionBarActivity otActionBarActivity) {
        this.createEventActivity = (CreateEventActivity)otActionBarActivity;
    }

    @Override
    protected List<TimeSlot> doInBackground(Void... params) {
        List<ParseUser> parseUsers = OTEventManager.getInstance().getParseUserList();
        CalendarManager cm = CalendarManager.getInstance();

        createEventActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                createEventActivity.blockForApi("Comparing...");
            }
        });
        return CalendarManager.getOptimumTimeSlots(parseUsers, null, 3 * DateUtils.HOUR_IN_MILLIS, 9, 23, new Date().getTime(), (new Date().getTime() + 30 * DateUtils.DAY_IN_MILLIS), 3);
    }

    @Override
    protected void onPostExecute(List<TimeSlot> timeSlots) {
        super.onPostExecute(timeSlots);
        for (TimeSlot timeSlot: timeSlots) {
            Log.d(TAG, timeSlot.toString());
        }
        createEventActivity.setPossibleTimeSlots(timeSlots);
        createEventActivity.dismissBlockForApi();
        createEventActivity.viewPager.setCurrentItem(2, true);
        createEventActivity.rgStep.check(R.id.rb_step_3);
        createEventActivity.pagerAdapter.notifyDataSetChanged();
    }
}
