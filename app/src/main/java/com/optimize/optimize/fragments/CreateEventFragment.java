package com.optimize.optimize.fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View.OnClickListener;
import com.optimize.optimize.R;
import com.optimize.optimize.activities.OTActivity;
import com.optimize.optimize.calendar.CalendarEvent;
import com.optimize.optimize.calendar.CalendarService;
import com.optimize.optimize.calendar.TimeSlot;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateEventFragment extends OTFragment implements OnClickListener{


    public CreateEventFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View scrollView = inflater.inflate(R.layout.fragment_create_event, container, false);
        LinearLayout linearLayout = (LinearLayout) scrollView.findViewById(R.id.create_event_form_layout);

        List<TimeSlot> possibleTimeSlot = null;

        Spinner timeSlotSpinner = (Spinner) linearLayout.findViewById(R.id.TimeSlotSpinner);

        ArrayAdapter<TimeSlot> adapter = new ArrayAdapter<TimeSlot>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, possibleTimeSlot);
        timeSlotSpinner.setAdapter(adapter);

        Button createButton = (Button) linearLayout.findViewById(R.id.createEventButton);
        createButton.setOnClickListener(this);

        TextView time = (TextView)linearLayout.findViewById(R.id.Time);

        // Inflate the layout for this fragment
        return scrollView;
    }

    @Override
    public void onClick(View v) {
        EditText titleField = (EditText) getView().findViewById(R.id.EventTitle);
        String title = titleField.getText().toString();

        EditText locationField = (EditText) getView().findViewById(R.id.EventLocation);
        String location = locationField.getText().toString();

        EditText descriptionField = (EditText) getView().findViewById(R.id.EventDescription);
        String description = descriptionField.getText().toString();

        Spinner timeSpinner = (Spinner) getView().findViewById(R.id.TimeSlotSpinner);
        String time = timeSpinner.getSelectedItem().toString();
        TimeSlot timeSlot = (TimeSlot) timeSpinner.getSelectedItem();
        Log.i("Results", "Title: " + title + "Location: " + location + "Des: " +
                description + "Time: " + time);

        CalendarService.exportEvent(getActivity().getBaseContext(), new CalendarEvent(title, timeSlot.getStart(), timeSlot.getEnd()), CalendarService.getCalendarId(getActivity().getBaseContext()));
    }


}
