package com.optimize.optimize.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.optimize.optimize.tasks.GetOptimumTimeSlotsTask;
import com.optimize.optimize.EventTimeType;
import com.optimize.optimize.R;
import com.optimize.optimize.WithInType;
import com.optimize.optimize.activities.OTEventManager;
import com.optimize.optimize.adapters.AddParticipantsAdapter;
import com.optimize.optimize.calendar.CalendarManager;
import com.optimize.optimize.utilities.FastToast;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemSelected;

public class CreateEventParticipantFragment extends OTFragment {

    @InjectView(R.id.btnFind)
    Button btnFind;

    @InjectView(R.id.etxtFindUser)
    EditText etxtFindUser;

    @InjectView(R.id.lsParticipants)
    ListView lsParticipants;

    @InjectView(R.id.btn_compare)
    Button btnCompare;

    CalendarManager cm;

    List<ParseUser> parseUsers;

    ArrayAdapter<String> arrayAdapter;
    AddParticipantsAdapter addParticipantsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_event_participant, container, false);
        ButterKnife.inject(this, view);
        cm = CalendarManager.getInstance();
        parseUsers = new ArrayList<>();
        addParticipantsAdapter = new AddParticipantsAdapter(getOTActionBarActivity(), parseUsers);
        lsParticipants.setAdapter(addParticipantsAdapter);
        return view;
    }

    @OnClick(R.id.btnFind)
    void onBtnFindClick() {
        String username = etxtFindUser.getText().toString().trim();
        ParseQuery<ParseUser> userParseQuery = ParseQuery.getQuery(ParseUser.class);
        userParseQuery.whereEqualTo("username", username);
        getOTActionBarActivity().blockForApi();
        userParseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> parseUsers, ParseException e) {
                getOTActionBarActivity().dismissBlockForApi();
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
                    FastToast.show(R.string.no_such_user, getOTActionBarActivity());
                }
            }
        });
    }

    @OnClick(R.id.btn_compare)
    void onBtnCompareClicked() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OTEventManager om = OTEventManager.getInstance();
                om.setParseUserList(parseUsers);

                new GetOptimumTimeSlotsTask().execute();
            }
        }).start();

    }

}
