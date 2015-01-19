package com.optimize.optimize.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.optimize.optimize.EventTimeType;
import com.optimize.optimize.R;

import butterknife.OnClick;

public class CreateEventTimeFragment extends OTFragment {

    /*@OnClick(R.id.btnStart)
    void onBtnStartClicked() {
        getOTActionBarActivity().showTimePickerDialog(EventTimeType.Start);
    }

    @OnClick(R.id.btnEnd)
    void onBtnEndClicked() {
        getOTActionBarActivity().showTimePickerDialog(EventTimeType.End);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_event_time, container, false);
    }

}
