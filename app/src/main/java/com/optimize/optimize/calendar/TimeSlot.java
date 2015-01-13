package com.optimize.optimize.calendar;

import com.optimize.optimize.WithInType;
import com.optimize.optimize.models.OTUserService;
import com.optimize.optimize.utilities.DateUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by james on 5/1/15.`
 */

public class TimeSlot implements Comparable {
    long start, end;
    int numOfConflict, numOfAttendees;

    public TimeSlot(long start, long end) {
        this.start = start;
        this.end = end;
        numOfConflict = 0;
        numOfAttendees = 0;
    }

    public int getNumOfConflict() {
        return numOfConflict;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    ;

    @Override
    public String toString() {
        return "From " + DateUtils.convertDate(start, "dd/MM hh:mm") +
                " To " + DateUtils.convertDate(end, "hh:mm") +
                " Conflict: " + numOfConflict;
    }

    @Override
    public int compareTo(Object another) {
        int compareConflict = ((TimeSlot) another).getNumOfConflict();
        return this.numOfConflict - compareConflict;
    }

    public class Builder {

        public static final long ONE_HOUR_MILLIS = android.text.format.DateUtils.HOUR_IN_MILLIS;
        public static final long HALF_HOUR_MILLIS = android.text.format.DateUtils.HOUR_IN_MILLIS / 2;
        public static final long ONE_DAY_MILLIS = android.text.format.DateUtils.DAY_IN_MILLIS;

        private int startHour;
        private int endHour;
        private WithInType withIn;
        private int duration;
        private List<OTUserService> users;

        public Builder setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder setUsers(List<OTUserService> users) {
            this.users = users;
            return this;
        }

        public Builder setStartHour(int startHour) {
            this.startHour = startHour;
            return this;
        }


        public Builder setEndHour(int endHour) {
            this.endHour = endHour;
            return this;
        }

        public Builder setWithIn(WithInType withIn) {
            this.withIn = withIn;
            return this;
        }

        public long hoursInMilliSec(int hour) {
            return hour * android.text.format.DateUtils.HOUR_IN_MILLIS;
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
                    return new Date().getTime() + 1 * android.text.format.DateUtils.DAY_IN_MILLIS;
                case Week:
                    return new Date().getTime() + 7 * android.text.format.DateUtils.DAY_IN_MILLIS;
                case Month:
                    return new Date().getTime() + 30 * android.text.format.DateUtils.DAY_IN_MILLIS;
            }

            return -1;
        }

        public List<TimeSlot> build() {
            return getOptimumTimeSlots(users, null, duration, startHour, endHour, new Date().getTime(),getWitinInMilliSec(), 3 );
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
        public List<TimeSlot> getOptimumTimeSlots(List<OTUserService> userList,
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
                for (OTUserService user : userList) {
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

    }
}
