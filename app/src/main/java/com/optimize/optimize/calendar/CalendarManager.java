package com.optimize.optimize.calendar;

import android.text.format.DateUtils;
import android.util.Log;

import com.optimize.optimize.WithInType;
import com.optimize.optimize.models.OTUserService;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Collections;
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

    public static final long ONT_SEC_MILLIS = DateUtils.SECOND_IN_MILLIS;
    public static final long ONE_MIN_MILLIS = DateUtils.MINUTE_IN_MILLIS;
    public static final long ONE_HOUR_MILLIS = DateUtils.HOUR_IN_MILLIS;
    public static final long HALF_HOUR_MILLIS = DateUtils.HOUR_IN_MILLIS / 2;
    public static final long ONE_DAY_MILLIS = DateUtils.DAY_IN_MILLIS;

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

    public int getEndHour() {
        return endHour;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setWithIn(WithInType withIn) {
        this.withIn = withIn;
        Log.d(TAG, withIn.toString());
    }

    public WithInType getWithIn() {
        return withIn;
    }

    public long getEndHourInMilliSec() {
        return hoursInMilliSec(endHour);
    }

    public long getStartHourInMilliSec() {
        return hoursInMilliSec(startHour);
    }

    public long hoursInMilliSec(int hour) {
        return hour * android.text.format.DateUtils.HOUR_IN_MILLIS;
    }

    /**
     * @param userList            userList with List of Calendar Event
     * @param title               Event Title
     * @param duration            time duration in milli second
     * @param startTimeBoundary   Start Time in a day (0-23)
     * @param endTimeBoundary     End Time in a day, end time must be larger than Start Time (0-23)
     * @param comparisonStartTime Comparison Period Start Time in milli second
     * @param comparisonEndTime   Comparison Period End Time in milli second
     * @param n                   Maximum number of possible Timeslot return.
     * @return possible TimeSlots List
     */

    public static List<TimeSlot> getOptimumTimeSlots(List<ParseUser> userList,
                                              String title,
                                              long duration,
                                              int startTimeBoundary,
                                              int endTimeBoundary,
                                              long comparisonStartTime,
                                              long comparisonEndTime,
                                              int n) {

//        // Set comparisonStartTime back to 0:00
//        int hourOffSet = Integer.parseInt(com.optimize.optimize.utilities.DateUtils.convertDate(comparisonStartTime, "HH"));
//        int minOffSet = Integer.parseInt(com.optimize.optimize.utilities.DateUtils.convertDate(comparisonStartTime, "mm"));
//        int secOffSet = Integer.parseInt(com.optimize.optimize.utilities.DateUtils.convertDate(comparisonStartTime, "ss"));
//        long timeOffSet = hourOffSet * ONE_HOUR_MILLIS + minOffSet * ONE_MIN_MILLIS + secOffSet * ONT_SEC_MILLIS;
//        comparisonStartTime = comparisonStartTime - timeOffSet;
//        //If input of comparisonEndTime is not the end of the day, this code is needed
//        comparisonEndTime = comparisonEndTime - timeOffSet + 1 * ONE_DAY_MILLIS;

        // Generate Possible time slots within the boundary time
        List<TimeSlot> possibleTimeSlots = new ArrayList<TimeSlot>();
        long startTimeBoundaryMillis = comparisonStartTime + startTimeBoundary * ONE_HOUR_MILLIS;
        long endTimeBoundaryMillis = comparisonStartTime + endTimeBoundary * ONE_HOUR_MILLIS;
        for (; startTimeBoundaryMillis <= comparisonEndTime; startTimeBoundaryMillis += ONE_DAY_MILLIS, endTimeBoundaryMillis += ONE_DAY_MILLIS) {
            for (long start = startTimeBoundaryMillis, end = start + duration; end <= endTimeBoundaryMillis; start += HALF_HOUR_MILLIS, end += HALF_HOUR_MILLIS)
                possibleTimeSlots.add(new TimeSlot(start, end));
        }

        // Search for numOfConflict for each events
        for (TimeSlot timeSlot : possibleTimeSlots) {
            for (ParseUser user : userList) {
                for (CalendarEvent event : OTUserService.getEvents(user)) {
                    if ((event.getBegin() >= timeSlot.start && event.getBegin() <= timeSlot.end) ||
                            event.getEnd() >= timeSlot.start && event.getEnd() <= timeSlot.end) {
                        timeSlot.numOfConflict++;
                        break;
                    }
                }
            }
        }

//        for (TimeSlot ts : possibleTimeSlots)
        //          Log.i("TimeSlots", ts.toString());

        Collections.sort(possibleTimeSlots);
        List<TimeSlot> possibleEventTime = new ArrayList<TimeSlot>();

        if (n > possibleTimeSlots.size())
            possibleEventTime = possibleTimeSlots;

        for (int i = 0; i < n; ++i) {
            possibleEventTime.add(possibleTimeSlots.get(i));
        }
        return possibleEventTime;
    }
}
