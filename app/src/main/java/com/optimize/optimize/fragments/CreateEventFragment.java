package com.optimize.optimize.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.optimize.optimize.R;
import com.optimize.optimize.activities.MainActivity;
import com.optimize.optimize.calendar.CalendarEvent;
import com.optimize.optimize.calendar.CalendarService;
import com.optimize.optimize.calendar.TimeSlot;
import com.optimize.optimize.models.OTEvent;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class CreateEventFragment extends OTFragment implements OnClickListener{

    public CreateEventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View scrollView = inflater.inflate(R.layout.fragment_create_event, container, false);
        LinearLayout linearLayout = (LinearLayout) scrollView.findViewById(R.id.create_event_form_layout);

        List<TimeSlot> possibleTimeSlot = new ArrayList<>();

        //Test case
        List<CalendarEvent> ce;
        ce = CalendarService.getEventList(getActivity().getBaseContext(),CalendarService.getCalendarIdList(getActivity().getBaseContext()));
        possibleTimeSlot.add(new TimeSlot(ce.get(0).getBegin(), ce.get(0).getEnd()));

        // List<TimeSlot> possibleTimeSlot = ot().getPossibleTimeSlots();
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
        switch (v.getId()) {
            case R.id.createEventButton:
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

                final OTEvent otEvent = new OTEvent(title, timeSlot.getStart(), timeSlot.getEnd(), description, location);
                otEvent.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            List<ParseUser> users = ot().getParseUsers();
                            String otEventId = otEvent.getObjectId();
                            for (ParseUser parseUser: users) {
                                //TODO add otEventId to otEventsId in Parse user
                            }
                        }
                    }
                });

                CalendarService.exportEvent(getActivity().getBaseContext(), new CalendarEvent(title,
                                            timeSlot.getStart(), timeSlot.getEnd()), location, description,
                                            CalendarService.getCalendarId(getActivity().getBaseContext()));

                /*new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            ToTo.show("save success", ot());
                        } else {
                            e.printStackTrace();
                        }
                    }
                });*/

                Intent i = new Intent(getActivity().getBaseContext(), MainActivity.class);
                startActivity(i);
                break;

            case R.id.cancelButton:
                getFragmentManager().popBackStack();
                break;
        }
    }
}
