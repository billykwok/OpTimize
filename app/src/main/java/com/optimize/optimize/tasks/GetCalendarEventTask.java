package com.optimize.optimize.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.optimize.optimize.calendar.CalendarEvent;
import com.optimize.optimize.calendar.CalendarService;
import com.optimize.optimize.models.OTUserService;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

/**
 * Created by samwalker on 18/1/15.
 */
public class GetCalendarEventTask extends AsyncTask<Context, Void, List<CalendarEvent>> {

    final String TAG = "GetCalendarEventTask";

    @Override
    protected List<CalendarEvent> doInBackground(Context... params) {
        return CalendarService.getEventList(params[0]);
    }

    @Override
    protected void onPostExecute(List<CalendarEvent> calendarEvents) {
        super.onPostExecute(calendarEvents);
        ParseUser parseUser = ParseUser.getCurrentUser();
        if (parseUser == null) {
            Log.e(TAG, "Parse user is null");
        } else {
            if (calendarEvents == null) {
                Log.e(TAG, "calender event is null");
            } else {
                OTUserService.setEventList(calendarEvents, parseUser);
                parseUser.saveEventually(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.d(TAG, "save calendar events success");
                        } else {
                            e.printStackTrace();
                        }
                    }
                });

            }
        }
    }
}
