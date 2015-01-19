package com.optimize.optimize.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.optimize.android.widget.BetterViewPager;
import com.optimize.optimize.R;
import com.optimize.optimize.fragments.CreateEventDetailFragment;
import com.optimize.optimize.fragments.CreateEventParticipantFragment;
import com.optimize.optimize.fragments.CreateEventTimeFragment;
import com.optimize.optimize.fragments.EventListFragment;
import com.optimize.optimize.fragments.OTFragment;
import com.optimize.optimize.utilities.FastToast;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;

public class CreateEventActivity extends OTActionBarActivity implements RadioGroup.OnCheckedChangeListener {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.rg_step)
    RadioGroup rgStep;

    @InjectView(R.id.pager)
    BetterViewPager viewPager;

    int[] rbStepIds = { R.id.rb_step_1, R.id.rb_step_2, R.id.rb_step_3};
    PagerAdapter pagerAdapter;

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

        // Set up viewpager
        pagerAdapter = new StepPagerAdapter(getSupportFragmentManager());
        viewPager.setIsSwipeable(false).setAdapter(pagerAdapter);

        // Set up radiogroup
        rgStep.setOnCheckedChangeListener(this);
        rgStep.check(R.id.rb_step_1);
    }

    @OnClick(R.id.btn_prev_step)
    public void onClickPrevStep() {
        rgStep.check(rbStepIds[Math.max(0, viewPager.getCurrentItem() - 1)]);
    }

    @OnClick(R.id.btn_next_step)
    public void onClickNextStep() {
        rgStep.check(rbStepIds[Math.min(pagerAdapter.getCount() - 1, viewPager.getCurrentItem() + 1)]);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group.getId() == R.id.rg_step) {
            if (checkedId == R.id.rb_step_1) {
                viewPager.setCurrentItem(0, true);
            } else if (checkedId == R.id.rb_step_2) {
                viewPager.setCurrentItem(1, true);
            } else if (checkedId == R.id.rb_step_3) {
                viewPager.setCurrentItem(3, true);
            }
        }
    }

    private class StepPagerAdapter extends FragmentStatePagerAdapter {

        public StepPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return new CreateEventTimeFragment();
                case 1:
                    return new CreateEventParticipantFragment();
                case 2:
                    return new CreateEventDetailFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

    }

}
