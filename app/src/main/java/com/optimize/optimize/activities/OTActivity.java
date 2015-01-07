package com.optimize.optimize.activities;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.optimize.optimize.R;
import com.optimize.optimize.calendar.TimeSlot;

import java.util.List;

/**
 * Created by samwalker on 1/1/15.
 */
public class OTActivity extends ActionBarActivity {

    ProgressDialog pd;
    final String TAG_OT = "OTActivity";
    private List<TimeSlot> possibleTimeSlots;


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
        return possibleTimeSlots;
    }

    public void setPossibleTimeSlots(List<TimeSlot> possibleTimeSlots) {
        this.possibleTimeSlots = possibleTimeSlots;
    }
}
