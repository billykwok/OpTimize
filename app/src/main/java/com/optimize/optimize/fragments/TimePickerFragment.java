package com.optimize.optimize.fragments;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import com.optimize.optimize.EventTimeType;
import com.optimize.optimize.calendar.CalendarManager;
import com.optimize.optimize.utilities.ToTo;

import java.util.Calendar;

/**
 * Created by samwalker on 10/1/15.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{
    EventTimeType eventTimeType;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        eventTimeType = (EventTimeType) getArguments().getSerializable("eventTimeType");
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this , hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        switch (eventTimeType) {
            case Start:
//                ToTo.show("Start: "+String.valueOf(hourOfDay) + " "+ String.valueOf(minute), getActivity());
                CalendarManager.getInstance().setStartHour(hourOfDay);
                return;
            case End:
//                ToTo.show("End: "+String.valueOf(hourOfDay) + " "+ String.valueOf(minute), getActivity());
                CalendarManager.getInstance().setEndHour(hourOfDay);
                return;
        }

    }
}


