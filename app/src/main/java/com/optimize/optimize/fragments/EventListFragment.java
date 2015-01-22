package com.optimize.optimize.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.optimize.android.BaseFragment;
import com.optimize.optimize.R;
import com.optimize.optimize.adapters.OTEventAdapter;
import com.optimize.optimize.hack.DividerItemDecoration;
import com.optimize.optimize.models.OTEvent;
import com.optimize.optimize.models.OTUserService;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventListFragment extends BaseFragment {

    RecyclerView rvOTEvent;
    RecyclerView.Adapter rvAdapterOTEvent;
    RecyclerView.LayoutManager rvLayoutMangerOTEvent;
    ParseUser current;
    List<OTEvent> otEvents;
    final String TAG = "EventListFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_oteventlist, container, false);
        rvOTEvent = (RecyclerView) rootView.findViewById(R.id.rv_otevent);
        rvOTEvent.setHasFixedSize(true);
        rvOTEvent.addItemDecoration(new DividerItemDecoration(getActivity(), null));
        current = ParseUser.getCurrentUser();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        rvLayoutMangerOTEvent = new LinearLayoutManager(getActivity());
        rvOTEvent.setLayoutManager(rvLayoutMangerOTEvent);
        otEvents = getOtEvent();
        rvAdapterOTEvent = new OTEventAdapter(otEvents);
        rvOTEvent.setAdapter(rvAdapterOTEvent);

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (otEvents != null) {
            Log.d(TAG, "otevents is not null");
            otEvents.clear();
            otEvents.addAll(getOtEvent());
            rvAdapterOTEvent.notifyDataSetChanged();
        } else {
            Log.d(TAG, "otevnts is null");
        }
    }

    private List<OTEvent> getOtEvent() {
        ParseQuery<OTEvent> otEventParseQuery = ParseQuery.getQuery(OTEvent.class);
        List<OTEvent> otEvents = null;
        try {

            otEvents = otEventParseQuery.whereEqualTo("hostId", current.getObjectId()).orderByDescending("updatedAt").find();

        } catch (ParseException e) {
            e.printStackTrace();
        }
//        otEventParseQuery.whereEqualTo("hostId", current.getObjectId()).findInBackground(new FindCallback<OTEvent>() {
//            @Override
//            public void done(List<OTEvent> otEvents, ParseException e) {
//                Log.d(TAG, "find in background size: "+ String.valueOf(otEvents.size()));
//            }
//        });
        Log.d(TAG, "otevents size: "+ String.valueOf(otEvents.size()));
        return otEvents;
    }
//    private List<OTEvent> testData() {
//        List<OTEvent> otEventList = new ArrayList<>();
//        for (int i = 1; i <= 20; ++i) {
//            long start = new Date().getTime();
//            long end = start + 100000000;
//            otEventList.add(new OTEvent("Event " + Integer.toString(i), start, end, "Venue " + Integer.toString(i), "Description " + Integer.toString(i)));
//        }
//        return otEventList;
//    }

}
