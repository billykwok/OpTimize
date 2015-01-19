package com.optimize.optimize.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.optimize.optimize.EventTimeType;
import com.optimize.optimize.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateEventTimeFragment extends OTFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_event_time, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    /*@OnClick(R.id.tv_starting_date)
    void onClickStartingDate() {
        getOTActionBarActivity().showDate(EventTimeType.Start);
    }

    @OnClick(R.id.tv_ending_date)
    void onClickEndingDate() {
        getOTActionBarActivity().showTimePickerDialog(EventTimeType.End);
    }*/

    @OnClick(R.id.tv_starting_time)
    void onClickStartingTime() {
        getOTActionBarActivity().showTimePickerDialog(EventTimeType.Start);
    }

    @OnClick(R.id.tv_ending_time)
    void onClickEndingTime() {
        getOTActionBarActivity().showTimePickerDialog(EventTimeType.End);
    }

}
