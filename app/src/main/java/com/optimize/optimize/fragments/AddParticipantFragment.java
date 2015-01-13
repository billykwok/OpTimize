package com.optimize.optimize.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.optimize.optimize.EventTimeType;
import com.optimize.optimize.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

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

    @InjectView(R.id.spinnerWithin)
    Spinner spinnerWithin;

    ArrayAdapter<String> arrayAdapter;
    private int[] withinTypeRes = {R.string.within_day, R.string.within_month, R.string.within_month};



    public AddParticipantFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_participant, container, false);
        ButterKnife.inject(this, view);
        arrayAdapter = new ArrayAdapter<String>(ot(), android.R.layout.simple_spinner_item, getWithInType());
        spinnerWithin.setAdapter(arrayAdapter);
        return view;
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
