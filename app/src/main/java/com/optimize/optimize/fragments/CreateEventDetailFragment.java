package com.optimize.optimize.fragments;

import android.app.Activity;
import android.os.AsyncTask;
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
import com.optimize.optimize.managers.OTEventManager;
import com.optimize.optimize.models.OTEvent;
import com.optimize.optimize.models.OTUserService;
import com.optimize.optimize.models.Participant;
import com.optimize.optimize.utilities.FastToast;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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

    List<TimeSlot> possibleTimeSlot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_event_detail, container, false);
        ButterKnife.inject(this, view);
//        List<TimeSlot> possibleTimeSlot = new ArrayList<>();
//
////        Test case
//        List<CalendarEvent> ce;
//        ce = CalendarService.getEventList(getBaseContext());
//        possibleTimeSlot.add(new TimeSlot(ce.get(0).getBegin(), ce.get(0).getEnd()));
        if (getOTActionBarActivity().getPossibleTimeSlots() != null) {
            for (TimeSlot t: getOTActionBarActivity().getPossibleTimeSlots()) {
                Log.d(TAG, "on create view get possible time slots: " + t.toString());
            }
        } else {
            Log.e(TAG, "on create view null possible time slots");
        }

        possibleTimeSlot = getOTActionBarActivity().getPossibleTimeSlots();

        Log.d(TAG, "running ... set adapter with timeslots no.: " + String.valueOf(possibleTimeSlot.size()));
        ArrayAdapter<TimeSlot> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_item, possibleTimeSlot);
        spEventTimeSlot.setAdapter(adapter);
        Log.d(TAG, "running ... set adapter ");


        return view;
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getOTActionBarActivity().getPossibleTimeSlots() != null) {
//            for (TimeSlot t: getOTActionBarActivity().getPossibleTimeSlots()) {
//                Log.d(TAG, "on Create get possible time slots: " + t.toString());
//            }
//            notifyTimeSlotChange();
//        } else {
//            Log.e(TAG, "on Create null possible time slots");
//        }
//
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        Log.i("Page3","Hi");
//        if (getOTActionBarActivity().getPossibleTimeSlots() != null) {
//            for (TimeSlot t: getOTActionBarActivity().getPossibleTimeSlots()) {
//                Log.d(TAG, " on start get possible time slots: " + t.toString());
//            }
//            notifyTimeSlotChange();
//        } else {
//            Log.e(TAG, "on start null possible time slots");
//        }
//
//    }
//
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        if (getOTActionBarActivity().getPossibleTimeSlots() != null) {
//            for (TimeSlot t: getOTActionBarActivity().getPossibleTimeSlots()) {
//                Log.d(TAG, " get possible time slots: " + t.toString());
//            }
//            notifyTimeSlotChange();
//        } else {
//            Log.e(TAG, " null possible time slots");
//        }
//
//    }

    public void notifyTimeSlotChange() {
        if (possibleTimeSlot != null) {
            possibleTimeSlot.clear();
            possibleTimeSlot.addAll(getOTActionBarActivity().getPossibleTimeSlots());
        }
    }

    @OnClick(R.id.btn_create_event)
    public void btnClickCreateEvent() {

        getOTActionBarActivity().blockForApi("Creating Event...");
        new CreateEventTask().execute();
    }

    @OnClick(R.id.btn_cancel)
    public void btnClickCancel() {
        finishActivity();
    }

    class CreateEventTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            String name = etEventName.getText().toString();
            String location = "To be confirmed...";
            String description = etEventDesc.getText().toString();
            String time = spEventTimeSlot.getSelectedItem().toString();

            TimeSlot timeSlot = (TimeSlot) spEventTimeSlot.getSelectedItem();
            Log.i("Results", "Title: " + name + "Des: " + description + "Time: " + time);


            ParseUser current = ParseUser.getCurrentUser();

            if(current != null) {
                final OTEvent otEvent = new OTEvent(name, timeSlot.getStart(), timeSlot.getEnd(), location, description);
                otEvent.setHostId(current.getObjectId());
                otEvent.saveInBackground();
            }
            CalendarService.exportEvent(getBaseContext(),
                    new CalendarEvent(name,
                            timeSlot.getStart(),
                            timeSlot.getEnd()),
                    description,
                    location,
                    CalendarService.getCalendarId(getBaseContext()));

            getOTActionBarActivity().dismissBlockForApi();
            getOTActionBarActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    FastToast.show("Event exported to Calendar", getApplicationContext());
                }
            });
            finishActivity();
            return null;
        }
    }

}
