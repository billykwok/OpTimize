package com.optimize.optimize.fragments;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.optimize.optimize.EventTimeType;
import com.optimize.optimize.R;
import com.optimize.optimize.WithInType;
import com.optimize.optimize.adapters.AddParticipantsAdapter;
import com.optimize.optimize.calendar.CalendarManager;
import com.optimize.optimize.calendar.TimeSlot;
import com.optimize.optimize.utilities.FastToast;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemSelected;

public class CreateEventParticipantFragment extends OTFragment {

    @InjectView(R.id.btnStart)
    Button btnStart;

    @InjectView(R.id.btnEnd)
    Button btnEnd;

    @InjectView(R.id.btnFind)
    Button btnFind;

    @InjectView(R.id.etxtFindUser)
    EditText etxtFindUser;

    @InjectView(R.id.lsParticipants)
    ListView lsParticipants;

    @InjectView(R.id.spinnerWithin)
    Spinner spinnerWithin;

    @InjectView(R.id.btnCompare)
    Button btnCompare;

    CalendarManager cm;

    List<ParseUser> parseUsers;

    ArrayAdapter<String> arrayAdapter;
    AddParticipantsAdapter addParticipantsAdapter;
    private int[] withinTypeRes = {R.string.within_day, R.string.within_week, R.string.within_month};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_event_participant, container, false);
        ButterKnife.inject(this, view);
        cm = CalendarManager.getInstance();
        parseUsers = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<String>(ot(), android.R.layout.simple_spinner_item, getWithInType());
        spinnerWithin.setAdapter(arrayAdapter);
        addParticipantsAdapter = new AddParticipantsAdapter(ot(), parseUsers);
        lsParticipants.setAdapter(addParticipantsAdapter);
        return view;
    }

    @OnItemSelected(R.id.spinnerWithin)
    void onSpinnerWithinClicked(int position) {
        switch (position) {
            case 0:
                cm.setWithIn(WithInType.Day);
                return;
            case 1:
                cm.setWithIn(WithInType.Week);
                return;
            case 2:
                cm.setWithIn(WithInType.Month);
                return;
        }
    }

    @OnClick(R.id.btnFind)
    void onBtnFindClick() {
        String username = etxtFindUser.getText().toString().trim();
        ParseQuery<ParseUser> userParseQuery = ParseQuery.getQuery(ParseUser.class);
        userParseQuery.whereEqualTo("username", username);
        ot().blockForApi();
        userParseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> parseUsers, ParseException e) {
                ot().dismissBlockForApi();
                if (parseUsers.size() > 0) {
                    JSONArray j = parseUsers.get(0).getJSONArray("calendarEvents");
                    if (j != null) {
                        Log.d(TAG, j.toString());
                    } else {
                        Log.e(TAG, "null array");
                    }
                    CreateEventParticipantFragment.this.parseUsers.addAll(parseUsers);
                    CreateEventParticipantFragment.this.addParticipantsAdapter.notifyDataSetChanged();
                } else {
                    FastToast.show(R.string.no_such_user, ot());
                }
            }
        });
    }

    @OnClick(R.id.btnStart)
    void onBtnStartClicked() {
        ot().showTimePickerDialog(EventTimeType.Start);
    }

    @OnClick(R.id.btnEnd)
    void onBtnEndClicked() {
        ot().showTimePickerDialog(EventTimeType.End);
    }

    @OnClick(R.id.btnCompare)
    void onBtnCompareClicked() {
        List<TimeSlot> timeSlots = CalendarManager.getOptimumTimeSlots(parseUsers,null, 3* DateUtils.HOUR_IN_MILLIS, cm.getStartHour(), cm.getEndHour(), new Date().getTime(), (new Date().getTime()+30*DateUtils.DAY_IN_MILLIS), 3);
        for (TimeSlot timeSlot: timeSlots) {
            Log.d(TAG, timeSlot.toString());
        }
    }

    private ArrayList<String> getWithInType() {
        ArrayList<String> withType = new ArrayList<>();
        for (int i = 0; i < withinTypeRes.length; i++) {
            withType.add(getString(withinTypeRes[i]));
        }
        return withType;
    }


}
