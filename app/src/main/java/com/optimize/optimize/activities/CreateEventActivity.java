package com.optimize.optimize.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;

import com.optimize.optimize.R;
import com.optimize.optimize.fragments.CreateEventTimeFragment;
import com.optimize.optimize.fragments.EventListFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CreateEventActivity extends OTActionBarActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        ButterKnife.inject(this);

        // Toolbar
        setTitle("New Event");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateEventActivity.this.finish();
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        // Load create event step 1
        CreateEventTimeFragment newFragment = new CreateEventTimeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.disallowAddToBackStack().replace(R.id.container, newFragment).commit();

    }

}
