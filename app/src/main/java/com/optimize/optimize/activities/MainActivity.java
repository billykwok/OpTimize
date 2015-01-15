package com.optimize.optimize.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.optimize.optimize.R;
import com.optimize.optimize.calendar.CalendarEvent;
import com.optimize.optimize.calendar.CalendarService;
import com.optimize.optimize.fragments.CreateEventFragment;
import com.optimize.optimize.fragments.EventListFragment;
import com.optimize.optimize.models.OTUserService;
import com.optimize.optimize.utilities.FastToast;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends OTActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventListFragment newFragment = new EventListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.container, newFragment);
        transaction.commit();

        // Test Event
        // testMe();
    }

    private void testMe() {

        CreateEventFragment newFragment = new CreateEventFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.container, newFragment);
        transaction.commit();

        //test case
        List<CalendarEvent> celist1 = new ArrayList<>();
        celist1.add(CalendarService.createEvent("ISOM1380", 2015, 1, 19, 9, 0, 2015, 1, 19, 12, 0));
        celist1.add(CalendarService.createEvent("TutorialMon", 2015, 1, 19, 13, 30, 2015, 1, 19, 15, 30));
        celist1.add(CalendarService.createEvent("Meeting", 2015, 1, 19, 20, 0, 2015, 1, 19, 23, 0));
        celist1.add(CalendarService.createEvent("ISOM1380(2)", 2015, 1, 21, 9, 0, 2015, 1, 21, 12, 0));
        celist1.add(CalendarService.createEvent("TutorialMon", 2015, 1, 21, 13, 30, 2015, 1, 21, 15, 30));
        celist1.add(CalendarService.createEvent("Tutorial(ang)", 2015, 1, 22, 9, 30, 2015, 1, 22, 11, 30));
        celist1.add(CalendarService.createEvent("Piano", 2015, 1, 22, 12, 00, 2015, 1, 22, 13, 00));
        celist1.add(CalendarService.createEvent("Tutorial(qin)", 2015, 1, 22, 13, 30, 2015, 1, 22, 15, 30));

        ParseUser sample_user1 = new ParseUser();

        OTUserService.setEventList(celist1, sample_user1);
        sample_user1.setUsername("sample_user_name1");
        sample_user1.setPassword("sample_password1");
        sample_user1.setEmail("sample_user1@gmail.com");

        sample_user1.signUpInBackground();

        sample_user1.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    FastToast.show("save success", MainActivity.this);
                } else {
                    e.printStackTrace();
                }
            }
        });

        List<CalendarEvent> celist2 = new ArrayList<>();
        celist2.add(CalendarService.createEvent("MathTutorial", 2015, 1, 19, 9, 30, 2015, 1, 19, 10, 20));
        celist2.add(CalendarService.createEvent("2711Tutorial", 2015, 1, 19, 10, 30, 2015, 1, 19, 11, 20));
        celist2.add(CalendarService.createEvent("Math", 2015, 1, 19, 13, 30, 2015, 1, 19, 14, 20));
        celist2.add(CalendarService.createEvent("Econ", 2015, 1, 20, 9, 0, 2015, 1, 20, 10, 20));
        celist2.add(CalendarService.createEvent("MGMT", 2015, 1, 20, 10, 30, 2015, 1, 20, 11, 50));
        celist2.add(CalendarService.createEvent("Labu", 2015, 1, 20, 13, 00, 2015, 1, 20, 14, 50));
        celist2.add(CalendarService.createEvent("COMP2011", 2015, 1, 20, 15, 00, 2015, 1, 20, 16, 20));
        celist2.add(CalendarService.createEvent("Econ2", 2015, 1, 22, 9, 0, 2015, 1, 22, 10, 20));
        celist2.add(CalendarService.createEvent("MGMT2", 2015, 1, 22, 10, 30, 2015, 1, 22, 11, 50));
        celist2.add(CalendarService.createEvent("Labu2", 2015, 1, 22, 13, 00, 2015, 1, 22, 14, 50));
        celist2.add(CalendarService.createEvent("COMP2011_2", 2015, 1, 22, 15, 00, 2015, 1, 22, 16, 20));

        ParseUser sample_user2 = new ParseUser();

        OTUserService.setEventList(celist2, sample_user2);
        sample_user2.setUsername("sample_user_name2");
        sample_user2.setPassword("sample_password2");
        sample_user2.setEmail("sample_user2@gmail.com");



        sample_user2.signUpInBackground();

        sample_user2.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    FastToast.show("save success", MainActivity.this);
                } else {
                    e.printStackTrace();
                }
            }
        });

        List<CalendarEvent> celist3 = new ArrayList<>();
        celist3.add(CalendarService.createEvent("TEMG", 2015, 1, 19, 16, 30, 2015, 1, 19, 17, 20));
        celist3.add(CalendarService.createEvent("ReU", 2015, 1, 20, 19, 0, 2015, 1, 20, 23, 00));
        celist3.add(CalendarService.createEvent("Study", 2015, 1, 21, 10, 30, 2015, 1, 21, 15, 30));
        celist3.add(CalendarService.createEvent("Show", 2015, 1, 22, 20, 00, 2015, 1, 22, 23, 00));

        ParseUser sample_user3 = new ParseUser();

        OTUserService.setEventList(celist3, sample_user3);
        sample_user3.setUsername("sample_user_name3");
        sample_user3.setPassword("sample_password3");
        sample_user3.setEmail("sample_user3@gmail.com");

        sample_user3.signUpInBackground();

        sample_user3.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    FastToast.show("save success", MainActivity.this);
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

}
