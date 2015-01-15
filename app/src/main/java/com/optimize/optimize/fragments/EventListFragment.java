package com.optimize.optimize.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.optimize.android.BaseFragment;
import com.optimize.optimize.R;

import butterknife.InjectView;

public class EventListFragment extends BaseFragment {

    @InjectView(R.id.lv_otevent)
    ListView lvOTEvent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_eventlist, container, false);
    }

    

}
