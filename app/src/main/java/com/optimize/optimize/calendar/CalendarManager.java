package com.optimize.optimize.calendar;

import android.text.format.DateUtils;

import com.optimize.optimize.WithInType;
import com.optimize.optimize.models.OTUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by james on 7/1/15.
 */
public class CalendarManager {

    private int startHour;
    private int endHour;
    private WithInType withIn;

    private static CalendarManager ourInstance = new CalendarManager();

    public static CalendarManager getInstance() {
        return ourInstance;
    }

    private CalendarManager() {
    }

    public static final long ONE_HOUR_MILLIS = DateUtils.HOUR_IN_MILLIS;
    public static final long HALF_HOUR_MILLIS = DateUtils.HOUR_IN_MILLIS / 2;
    public static final long ONE_DAY_MILLIS = DateUtils.DAY_IN_MILLIS;

    /**
     *
     * @param userList userList with List of Calendar Event
     * @param title Event Title
     * @param duration time duration in milli second
     * @param startTimeBoundary Start Time in a day (0-23)
     * @param endTimeBoundary End Time in a day, end time must be larger than Start Time (0-23)
     * @param comparisonStartTime Comparison Period Start Time in milli second
     * @param comparisonEndTime Comparison Period End Time in milli second
     * @param n Maximum number of possible Timeslot return.
     * @return possible TimeSlots List
     */
    public static List<TimeSlot> getOptimumTimeSlots(List<OTUser> userList,
                                                     String title,
                                                     long duration,
                                                     int startTimeBoundary,
                                                     int endTimeBoundary,
                                                     long comparisonStartTime,
                                                     long comparisonEndTime,
                                                     int n) {

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
            for (OTUser user : userList) {
                for (CalendarEvent event : user.getEventList()) {
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

    public CalendarManager(int startHour, int endHour) {
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public void setWithIn(WithInType withIn) {
        this.withIn = withIn;
    }

    public long hoursInMilliSec(int hour) {
        return hour*DateUtils.HOUR_IN_MILLIS;
    }

    public long getEndHourInMilliSec() {
        return hoursInMilliSec(endHour);
    }

    public long getStartHourInMilliSec() {
        return hoursInMilliSec(startHour);
    }

    public long getWitinInMilliSec() {
        switch (withIn) {
            case Day:
                return new Date().getTime() + 1 * DateUtils.DAY_IN_MILLIS;
            case Week:
                return new Date().getTime() + 7 * DateUtils.DAY_IN_MILLIS;
            case Month:
                return new Date().getTime() + 30 * DateUtils.DAY_IN_MILLIS;
        }

        return -1;
    }
}
