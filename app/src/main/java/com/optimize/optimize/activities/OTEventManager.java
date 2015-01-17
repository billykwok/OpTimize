package com.optimize.optimize.activities;

import com.optimize.optimize.calendar.TimeSlot;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by james on 17/1/15.
 */
public class OTEventManager {
    private List<ParseUser> parseUserList;
    private List<TimeSlot> possibleTimeSlot;

    private static OTEventManager ourInstance = new OTEventManager();

    public static OTEventManager getInstance() {
        return ourInstance;
    }

    private OTEventManager() {
    }

    public List<ParseUser> getParseUserList() {
        return parseUserList;
    }

    public void setParseUserList(List<ParseUser> parseUserList) {
        this.parseUserList = parseUserList;
    }

    public List<TimeSlot> getPossibleTimeSlot() {
        return possibleTimeSlot;
    }

    public void setPossibleTimeSlot(List<TimeSlot> possibleTimeSlot) {
        this.possibleTimeSlot = possibleTimeSlot;
    }
}
