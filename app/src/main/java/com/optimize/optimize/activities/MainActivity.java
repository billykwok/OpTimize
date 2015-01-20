package com.optimize.optimize.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.optimize.optimize.R;
import com.optimize.optimize.calendar.CalendarEvent;
import com.optimize.optimize.calendar.CalendarService;
import com.optimize.optimize.fragments.EventListFragment;
import com.optimize.optimize.models.OTEvent;
import com.optimize.optimize.models.OTUserService;
import com.optimize.optimize.models.Participant;
import com.parse.LogInCallback;
import com.optimize.optimize.tasks.GetCalendarEventTask;
import com.optimize.optimize.utilities.FastToast;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends OTActionBarActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @InjectView(R.id.fab_add)
    FloatingActionButton fabAdd;

    MaterialMenuDrawable btnMaterialMenu;
    boolean isDrawerOpened;

    ParseUser sample_user4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

//        testCase();

        // Toolbar
        setTitle("Events");
        setSupportActionBar(toolbar);
        btnMaterialMenu = new MaterialMenuDrawable(this, Color.WHITE, MaterialMenuDrawable.Stroke.THIN);
        toolbar.setNavigationIcon(btnMaterialMenu);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        // Drawer
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                btnMaterialMenu.setTransformationOffset(
                        MaterialMenuDrawable.AnimationState.BURGER_ARROW,
                        isDrawerOpened ? 2 - slideOffset : slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                isDrawerOpened = true;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                isDrawerOpened = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                if (newState == DrawerLayout.STATE_IDLE) {
                    if (isDrawerOpened) {
                        btnMaterialMenu.setIconState(MaterialMenuDrawable.IconState.ARROW);
                    } else {
                        btnMaterialMenu.setIconState(MaterialMenuDrawable.IconState.BURGER);
                    }
                }
            }

        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDrawerOpened) {
                    drawerLayout.closeDrawer(Gravity.START);
                } else {
                    drawerLayout.openDrawer(Gravity.START);
                }
            }
        });

        // Floating Action Button
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FastToast.show("Hi", MainActivity.this);
                startActivity(CreateEventActivity.class);
            }
        });

        // Load event list fragment
        EventListFragment newFragment = new EventListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.disallowAddToBackStack().replace(R.id.container, newFragment).commit();

        new GetCalendarEventTask().execute(this);

        // Test Event
        // testMe();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // openSearch();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void testCase(){
        final List<CalendarEvent> celist2 = new ArrayList<CalendarEvent>();
        celist2.add(CalendarService.createEvent("sample_event", 2015, 1, 22, 9, 30, 2015, 1, 5, 10, 20));


        sample_user4 = new ParseUser();

        OTUserService.setEventList(celist2, sample_user4);
        sample_user4.setUsername("sample_user_name4");
        sample_user4.setPassword("sample_password4");
        sample_user4.setEmail("sample_user4@gmail.com");
        String id = null;

        sample_user4.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(com.parse.ParseException e) {
                if (e != null) {
                    sample_user4 = ParseUser.getCurrentUser();
                    testCaseAddIdToEvent(celist2);
                } else {
                    e.printStackTrace();
                }

            }
        });


//        sample_user4.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(com.parse.ParseException e) {
//                if (e == null) {
//                    FastToast.show("save success", MainActivity.this);
//                } else {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    public void testCaseAddIdToEvent(List<CalendarEvent> celist2) {
        for(CalendarEvent ce : celist2){
            OTEvent otEvent = new OTEvent(ce.getTitle(), ce.getBegin(), ce.getEnd(), "My home", "Description sample");
            Log.i("Test", sample_user4.getObjectId());
            otEvent.setHostId(sample_user4.getObjectId());
            otEvent.saveInBackground(null);
        }
    }
}
