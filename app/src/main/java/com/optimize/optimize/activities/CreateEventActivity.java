package com.optimize.optimize.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.optimize.optimize.R;
import com.optimize.optimize.calendar.CalendarEvent;
import com.optimize.optimize.calendar.CalendarService;
import com.optimize.optimize.calendar.TimeSlot;
import com.optimize.optimize.models.OTEvent;
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

public class CreateEventActivity extends OTActionBarActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        ButterKnife.inject(this);

        // Toolbar
        setTitle("New Event");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateEventActivity.this.finish();
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        List<TimeSlot> possibleTimeSlot = new ArrayList<>();

        //Test case
        List<CalendarEvent> ce;
        ce = CalendarService.getEventList(getBaseContext());
        possibleTimeSlot.add(new TimeSlot(ce.get(0).getBegin(), ce.get(0).getEnd()));

        // List<TimeSlot> possibleTimeSlot = OTEventManager.getInstance().getPossibleTimeSlot();

        ArrayAdapter<TimeSlot> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_item, possibleTimeSlot);
        spEventTimeSlot.setAdapter(adapter);
        btnCreateEvent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FastToast.show("Create event", CreateEventActivity.this);

                String name = etEventName.getText().toString();
                String location = "My home";
                String description = etEventDesc.getText().toString();
                String time = spEventTimeSlot.getSelectedItem().toString();

                TimeSlot timeSlot = (TimeSlot) spEventTimeSlot.getSelectedItem();
                Log.i("Results", "Title: " + name + "Des: " + description + "Time: " + time);

                ParseUser current = ParseUser.getCurrentUser();

                if(current != null) {
                    final OTEvent otEvent = new OTEvent(name, timeSlot.getStart(), timeSlot.getEnd(), description, location);
                    otEvent.setHostId(current.getObjectId());
                    otEvent.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                List<ParseUser> users = CreateEventActivity.this.getParseUsers();
                                String otEventId = otEvent.getObjectId();
                                for (ParseUser parseUser : users) {
                                    //TODO add otEventId to otEventsId in Parse user
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
                        location,
                        description,
                        CalendarService.getCalendarId(getBaseContext()));

                /*new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            FastToast.show("save success", ot());
                        } else {
                            e.printStackTrace();
                        }
                    }
                });*/

                CreateEventActivity.this.finish();
            }
        });

        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateEventActivity.this.finish();
            }
        });

    }
}
