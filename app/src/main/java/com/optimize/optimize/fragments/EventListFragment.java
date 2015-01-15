package com.optimize.optimize.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.optimize.android.BaseFragment;
import com.optimize.optimize.R;
import com.optimize.optimize.adapters.OTEventAdapter;
import com.optimize.optimize.models.OTEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventListFragment extends BaseFragment {

    RecyclerView rvOTEvent;
    RecyclerView.Adapter rvAdapterOTEvent;
    RecyclerView.LayoutManager rvLayoutMangerOTEvent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_oteventlist, container, false);
        rvOTEvent = (RecyclerView) rootView.findViewById(R.id.rv_otevent);
        rvOTEvent.setHasFixedSize(true);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        rvLayoutMangerOTEvent = new LinearLayoutManager(getActivity());
        rvOTEvent.setLayoutManager(rvLayoutMangerOTEvent);

        rvAdapterOTEvent = new OTEventAdapter(testData());
        rvOTEvent.setAdapter(rvAdapterOTEvent);

        super.onViewCreated(view, savedInstanceState);
    }

    private List<OTEvent> testData() {
        List<OTEvent> otEventList = new ArrayList<>();
        for (int i = 1; i <= 20; ++i) {
            long start = Double.doubleToLongBits(Math.random());
            long end = start + 1000000000;
            otEventList.add(new OTEvent("Event " + Integer.toString(i), start, end, "Venue " + Integer.toString(i), "Description " + Integer.toString(i)));
        }
        return otEventList;
    }

}
