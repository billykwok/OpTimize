package com.optimize.optimize.fragments;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.optimize.optimize.R;
import com.optimize.optimize.activities.OTActionBarActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class OTFragment extends Fragment {

    final String TAG = "OTFragment";
    public OTFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }

    public OTActionBarActivity ot(){
        return (OTActionBarActivity)getActivity();
    }


}
