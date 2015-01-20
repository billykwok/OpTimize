package com.optimize.optimize.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import com.optimize.android.BaseActionBarActivity;
import com.optimize.android.ObjectContainer;
import com.optimize.optimize.EventTimeType;
import com.optimize.optimize.R;
import com.optimize.optimize.calendar.TimeSlot;
import com.optimize.optimize.fragments.TimePickerFragment;
import com.optimize.optimize.models.Participant;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samwalker on 1/1/15.
 */
public class OTActionBarActivity extends BaseActionBarActivity {

    ProgressDialog pd;
    final String TAG_OT = "OTActivity";
    private List<TimeSlot> possibleTimeSlots;
    private List<Participant> participants;
    private List<ParseUser> parseUsers;

    public static final String TAG = "OTActivity";

    public void blockForApi(String message) {
        if (pd == null) {
            pd = new ProgressDialog(this);
        }
        pd.setMessage(message);
        pd.show();
    }

    public void blockForApi(int StringRes) {
        String message = getResources().getString(StringRes);
        blockForApi(message);
    }

    public void blockForApi() {
        blockForApi(R.string.api_loading); // show loading
    }

    public void dismissBlockForApi() {
        if (pd != null) {
            pd.dismiss();
        } else {
            Log.e(TAG_OT, "dismiss called when pd is null");
        }
    }

    public List<TimeSlot> getPossibleTimeSlots() {
        if (possibleTimeSlots == null) {
            possibleTimeSlots = new ArrayList<>();
        }
        return possibleTimeSlots;
    }

    public void setPossibleTimeSlots(List<TimeSlot> possibleTimeSlots) {
        this.possibleTimeSlots = possibleTimeSlots;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public List<ParseUser> getParseUsers() {
        return parseUsers;
    }

    public void setParseUsers(List<ParseUser> parseUsers) {
        this.parseUsers = parseUsers;
    }

    public void showTimePickerDialog(EventTimeType eventTimeType) {
        Bundle b = new Bundle();
        b.putSerializable("eventTimeType", eventTimeType);
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.setArguments(b);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    @Override
    protected ObjectContainer getRetainedObjectsContainer() {
        return null;
    }

}
