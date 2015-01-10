package com.optimize.optimize.calendar;

import com.optimize.optimize.utilities.DateUtils;

/**
 * Created by james on 5/1/15.`
 */

public class TimeSlot implements Comparable{
    long start, end;
    int numOfConflict, numOfAttendees;

    public TimeSlot(long start, long end){
        this.start = start;
        this.end = end;
        numOfConflict = 0;
        numOfAttendees = 0;
    }

    public int getNumOfConflict(){ return numOfConflict;}

    public long getStart(){ return start;}

    public long getEnd(){return end;};

    @Override
    public String toString(){
        return "From " + DateUtils.convertDate(start, "dd/MM/yyyy hh:mm")+
                " To " + DateUtils.convertDate(end, "dd/MM/yyyy hh:mm") +
                " Conflict: " + numOfConflict;
    }

    @Override
    public int compareTo(Object another){
        int compareConflict= ((TimeSlot) another).getNumOfConflict();
        return this.numOfConflict - compareConflict;
    }
}
