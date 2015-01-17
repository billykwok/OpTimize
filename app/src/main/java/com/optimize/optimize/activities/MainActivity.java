package com.optimize.optimize.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
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
import com.optimize.optimize.models.OTUserService;
import com.parse.LogInCallback;
import com.optimize.optimize.utilities.FastToast;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Arrays;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

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
            public void onDrawerOpened(View drawerView) { isDrawerOpened = true; }

            @Override
            public void onDrawerClosed(View drawerView) { isDrawerOpened = false; }

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

        // Test Event
        // testMe();
    }



//        List<String> permissions = Arrays.asList("public_profile", "user_friends", "user_about_me",
//                "user_relationships", "user_birthday", "user_location");
//        ParseFacebookUtils.logIn(permissions, this, new LogInCallback() {
//            @Override
//            public void done(ParseUser parseUser, ParseException e) {
//                if (e == null) {
//                    if (parseUser != null) {
//                        Log.d(TAG, "ParseUser with Facebook: "+ parseUser.getUsername());
//                    } else if (parseUser.isNew()) {
//                        Log.d(TAG, "ParseUser with Facebook: "+ parseUser.getUsername() + "is new");
//                    } else {
//                        Log.e(TAG, "Facebook login error");
//                    }
//                    FastToast.show("save success", MainActivity.this);
//                } else {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }


}
