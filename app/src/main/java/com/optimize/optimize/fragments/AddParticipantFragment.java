package com.optimize.optimize.fragments;


import android.os.Bundle;
import android.app.Fragment;
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
import com.optimize.optimize.calendar.CalendarManager;
import com.optimize.optimize.models.OTUserService;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemSelected;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddParticipantFragment extends OTFragment {

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


    CalendarManager cm;


    ArrayAdapter<String> arrayAdapter;
    private int[] withinTypeRes = {R.string.within_day, R.string.within_week, R.string.within_month};



    public AddParticipantFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_participant, container, false);
        ButterKnife.inject(this, view);
        cm = CalendarManager.getInstance();
        arrayAdapter = new ArrayAdapter<String>(ot(), android.R.layout.simple_spinner_item, getWithInType());
        spinnerWithin.setAdapter(arrayAdapter);
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
        String username = etxtFindUser.getText().toString();
        ParseQuery<ParseUser> userParseQuery = ParseQuery.getQuery(ParseUser.class);
        userParseQuery.whereEqualTo("username", username);
        ot().blockForApi();
        userParseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> parseUsers, ParseException e) {
                ot().dismissBlockForApi();
                if (e == null) {
                    OTUserService otUserService = ParseObject.createWithoutData(OTUserService.class, parseUsers.get(0).getObjectId());
                    Log.d(TAG, otUserService.getObjectId());
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

    ArrayList<String> getWithInType() {
        ArrayList<String> withType = new ArrayList<>();
        for (int i = 0; i < withinTypeRes.length; i++) {
            withType.add(getString(withinTypeRes[i]));
        }
        return withType;
    }


}
