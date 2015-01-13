package com.optimize.optimize.activities;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

import com.optimize.optimize.EventTimeType;
import com.optimize.optimize.R;
import com.optimize.optimize.calendar.CalendarEvent;
import com.optimize.optimize.calendar.CalendarService;
import com.optimize.optimize.fragments.AddParticipantFragment;
import com.optimize.optimize.fragments.NavigationDrawerFragment;
import com.optimize.optimize.models.OTEvent;
import com.optimize.optimize.models.OTUser;
import com.optimize.optimize.models.Participant;
import com.optimize.optimize.utilities.ToTo;
import com.parse.ParseException;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends OTActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        //test case
        List<CalendarEvent> celist1 = new ArrayList<CalendarEvent>();
        celist1.add(CalendarService.createEvent("ISOM1380", 2015, 1, 5, 9, 0, 2015, 1, 5, 12, 0));
        celist1.add(CalendarService.createEvent("TutorialMon", 2015, 1, 5, 13, 30, 2015, 1, 5, 15, 30));
        celist1.add(CalendarService.createEvent("Meeting", 2015, 1, 5, 20, 0, 2015, 1, 5, 23, 0));
        celist1.add(CalendarService.createEvent("ISOM1380(2)", 2015, 1, 7, 9, 0, 2015, 1, 7, 12, 0));
        celist1.add(CalendarService.createEvent("TutorialMon", 2015, 1, 7, 13, 30, 2015, 1, 7, 15, 30));
        celist1.add(CalendarService.createEvent("Tutorial(ang)", 2015, 1, 8, 9, 30, 2015, 1, 8, 11, 30));
        celist1.add(CalendarService.createEvent("Piano", 2015, 1, 8, 12, 00, 2015, 1, 8, 13, 00));
        celist1.add(CalendarService.createEvent("Tutorial(qin)", 2015, 1, 8, 13, 30, 2015, 1, 8, 15, 30));

        OTUser sample_user = new OTUser();

        sample_user.setEventList(celist1);
        sample_user.setEmail("sample_user@gmail.com");
        sample_user.setPassword("sample_password");
        sample_user.setUsername("sample_user_name");

        sample_user.signUpInBackground();

        sample_user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    ToTo.show("save success", MainActivity.this);
                } else {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, new AddParticipantFragment())
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
