package com.optimize.optimize.activities;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.optimize.optimize.R;
import com.optimize.optimize.fragments.EventListFragment;
import com.optimize.optimize.tasks.GetCalendarEventTask;
import com.optimize.optimize.utilities.FastToast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
                startActivity(AddParticipantActivity.class);
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


}
