package com.optimize.optimize.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.optimize.optimize.R;
import com.optimize.optimize.calendar.CalendarEvent;
import com.optimize.optimize.calendar.CalendarService;
import com.optimize.optimize.calendar.TimeSlot;
import com.optimize.optimize.models.OTEvent;
import com.optimize.optimize.models.OTUserService;
import com.optimize.optimize.models.Participant;
import com.optimize.optimize.utilities.FastToast;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class CreateEventDetailFragment extends OTFragment {

    @InjectView(R.id.et_event_name)
    EditText etEventName;

    @InjectView(R.id.sp_event_timeslot)
    Spinner spEventTimeSlot;

    @InjectView(R.id.et_event_desc)
    EditText etEventDesc;

    @InjectView(R.id.btn_create_event)
    Button btnCreateEvent;

    @InjectView(R.id.btn_cancel)
    Button btnCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_event_detail, container, false);
        ButterKnife.inject(this, view);

        List<TimeSlot> possibleTimeSlot = new ArrayList<>();

        //Test case
        List<CalendarEvent> ce;
        ce = CalendarService.getEventList(getBaseContext());
        possibleTimeSlot.add(new TimeSlot(ce.get(0).getBegin(), ce.get(0).getEnd()));

        // List<TimeSlot> possibleTimeSlot = OTEventManager.getInstance().getPossibleTimeSlot();

        ArrayAdapter<TimeSlot> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_item, possibleTimeSlot);
        spEventTimeSlot.setAdapter(adapter);

        return view;
    }

    @OnClick(R.id.btn_create_event)
    public void btnClickCreateEvent() {
        FastToast.show("Create event", getActivity());

        String name = etEventName.getText().toString();
        String location = "My home";
        String description = etEventDesc.getText().toString();
        String time = spEventTimeSlot.getSelectedItem().toString();

        TimeSlot timeSlot = (TimeSlot) spEventTimeSlot.getSelectedItem();
        Log.i("Results", "Title: " + name + "Des: " + description + "Time: " + time);


        ParseUser current = ParseUser.getCurrentUser();

        //test case
//        List<CalendarEvent> celist5 = new ArrayList<CalendarEvent>();
//        celist5.add(CalendarService.createEvent("Class", 2015, 1, 19, 9, 30,     2015, 1, 19, 10, 20));
//        celist5.add(CalendarService.createEvent("Class", 2015, 1, 19, 12, 30,    2015, 1, 19, 14, 20));
//        celist5.add(CalendarService.createEvent("Class", 2015, 1, 19, 16, 30,    2015, 1, 19, 18, 00));
//        celist5.add(CalendarService.createEvent("Class", 2015, 1, 20, 12, 0,     2015, 1, 20, 13, 20));
//        celist5.add(CalendarService.createEvent("Class", 2015, 1, 20, 16, 30,    2015, 1, 20, 17, 50));
//        celist5.add(CalendarService.createEvent("Class", 2015, 1, 21, 13, 50,    2015, 1, 21, 15, 50));
//        celist5.add(CalendarService.createEvent("Class", 2015, 1, 21, 18, 00,    2015, 1, 21, 21, 20));
//        celist5.add(CalendarService.createEvent("Class", 2015, 1, 22, 12, 0,     2015, 1, 22, 13, 20));
//        celist5.add(CalendarService.createEvent("Class", 2015, 1, 22, 16, 30,    2015, 1, 22, 17, 50));
//        celist5.add(CalendarService.createEvent("Class", 2015, 1, 23, 9, 00,     2015, 1, 23, 12, 50));
//        celist5.add(CalendarService.createEvent("Class", 2015, 1, 23, 16, 30,    2015, 1, 23, 18, 00));
//
//        final ParseUser sample_user5 = new ParseUser();
//        OTUserService.setEventList(celist5, sample_user5);
//        sample_user5.setUsername("sample_user_name5");
//        sample_user5.setPassword("sample_password5");
//        sample_user5.setEmail("sample_user5@gmail.com");
//
//        sample_user5.signUpInBackground();
//
//        sample_user5.saveInBackground(null);




        if(current != null) {
            final OTEvent otEvent = new OTEvent(name, timeSlot.getStart(), timeSlot.getEnd(), location, description);
            otEvent.setHostId(current.getObjectId());
            otEvent.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        List<ParseUser> users = getOTActionBarActivity().getParseUsers();
                        String otEventId = otEvent.getObjectId();
                        for (ParseUser parseUser : users) {
                            JSONArray otEventIdList = parseUser.getJSONArray("otEventsId");
                            otEventIdList.put(otEventId);
                            parseUser.put("otEventsId", otEventIdList);
                            parseUser.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {
                                        FastToast.show("save success", getApplicationContext());
                                    }
                                }
                            });
                        }
                        List<Participant> participantList = Participant.fromParseUsers(users);
                        otEvent.setParticipants(participantList);
                    }
                }
            });
        }

        CalendarService.exportEvent(getBaseContext(),
                new CalendarEvent(name,
                        timeSlot.getStart(),
                        timeSlot.getEnd()),
                description,
                location,
                CalendarService.getCalendarId(getBaseContext()));

                /*new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            FastToast.show("save success", getOTActionBarActivity());
                        } else {
                            e.printStackTrace();
                        }
                    }
                });*/

        finishActivity();
    }

    @OnClick(R.id.btn_cancel)
    public void btnClickCancel() {
        finishActivity();
    }

}
