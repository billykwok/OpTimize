package com.optimize.optimize.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.optimize.optimize.EventDate;
import com.optimize.optimize.EventTime;
import com.optimize.optimize.EventTimeType;
import com.optimize.optimize.R;
import com.optimize.optimize.utilities.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;

public class CreateEventTimeFragment extends OTFragment {

    @InjectViews({ R.id.tv_starting_date, R.id.tv_ending_date })
    List<TextView> tvDates;

    @InjectViews({ R.id.tv_starting_time, R.id.tv_ending_time })
    List<TextView> tvTimes;

    @InjectView(R.id.tv_duration)
    TextView tvDuration;

    DatePickerDialog dialogStartingDate;
    DatePickerDialog dialogEndingDate;
    TimePickerDialog dialogStartingTime;
    TimePickerDialog dialogEndingTime;
    TimePickerDialog dialogDuration;

    DatePickerDialog.OnDateSetListener listenerStartingDate;
    DatePickerDialog.OnDateSetListener listenerEndingDate;
    TimePickerDialog.OnTimeSetListener listenerStartingTime;
    TimePickerDialog.OnTimeSetListener listenerEndingTime;
    TimePickerDialog.OnTimeSetListener listenerDuration;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_event_time, container, false);
        ButterKnife.inject(this, view);

        listenerStartingDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                tvDates.get(0).setText(new EventDate(year, monthOfYear, dayOfMonth).toString());
            }
        };

        listenerEndingDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                tvDates.get(1).setText(new EventDate(year, monthOfYear, dayOfMonth).toString());
            }
        };

        listenerStartingTime = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                tvTimes.get(0).setText(new EventTime(hourOfDay, minute).toString());
            }
        };

        listenerEndingTime = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                tvTimes.get(1).setText(new EventTime(hourOfDay, minute).toString());
            }
        };

        listenerDuration = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                tvDuration.setText(new EventTime(hourOfDay, minute).toString());
            }
        };

        dialogStartingDate = newDatePickerDialog(listenerStartingDate);
        dialogEndingDate = newDatePickerDialog(listenerEndingDate);
        dialogStartingTime = newTimePickerDialog(listenerStartingTime);
        dialogEndingTime = newTimePickerDialog(listenerEndingTime);
        dialogDuration = newTimePickerDialog(listenerDuration);

        return view;
    }

    @OnClick(R.id.tv_starting_date)
    void onClickStartingDate() {
        showDatePickerDialog(dialogStartingDate);
    }

    @OnClick(R.id.tv_ending_date)
    void onClickEndingDate() {
        showDatePickerDialog(dialogEndingDate);
    }

    @OnClick(R.id.tv_starting_time)
    void onClickStartingTime() {
        showTimePickerDialog(dialogStartingTime);
    }

    @OnClick(R.id.tv_ending_time)
    void onClickEndingTime() {
        showTimePickerDialog(dialogEndingTime);
    }

    @OnClick(R.id.tv_duration)
    void onClickDuration() {
        showTimePickerDialog(dialogDuration, 1, 0);
    }

    private DatePickerDialog newDatePickerDialog(DatePickerDialog.OnDateSetListener listener) {
        return new DatePickerDialog(getActivity(),
                                    listener,
                                    DateUtils.getCurrentYear(),
                                    DateUtils.getCurrentMonth(),
                                    DateUtils.getCurrentDay());
    }

    private TimePickerDialog newTimePickerDialog(TimePickerDialog.OnTimeSetListener listener) {
        Calendar c = Calendar.getInstance();
        return new TimePickerDialog(getActivity(),
                                    listener,
                                    DateUtils.getCurrentHour(),
                                    DateUtils.getCurrentMinute(),
                                    true);
    }

    private void showDatePickerDialog(DatePickerDialog dialog) {
        Calendar c = Calendar.getInstance();
        dialog.getDatePicker().updateDate(DateUtils.getCurrentYear(),
                                          DateUtils.getCurrentMonth(),
                                          DateUtils.getCurrentDay());
        dialog.show();
    }

    private void showTimePickerDialog(TimePickerDialog dialog) {
        Calendar c = Calendar.getInstance();
        dialog.updateTime(DateUtils.getCurrentHour(), DateUtils.getCurrentMinute());
        dialog.show();
    }

    private void showTimePickerDialog(TimePickerDialog dialog, int hour, int minute) {
        Calendar c = Calendar.getInstance();
        dialog.updateTime(hour, minute);
        dialog.show();
    }

}
