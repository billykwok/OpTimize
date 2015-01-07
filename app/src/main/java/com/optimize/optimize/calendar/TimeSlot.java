package com.optimize.optimize.calendar;

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

    @Override
    public String toString(){
        return start + " " + end + " " + numOfConflict;
    }

    @Override
    public int compareTo(Object another){
        int compareConflict= ((TimeSlot) another).getNumOfConflict();
        return this.numOfConflict - compareConflict;
    }
}
