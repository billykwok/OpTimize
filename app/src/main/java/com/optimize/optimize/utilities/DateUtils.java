package com.optimize.optimize.utilities;

import android.text.format.DateFormat;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by samwalker on 22/12/14.
 */
public class DateUtils {

    public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int getCurrentMonth() {
        return Calendar.getInstance().get(Calendar.MONTH);
    }

    public static int getCurrentDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    public static int getCurrentHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    public static int getCurrentMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    public static String convertDate(long dateInMilliseconds, String dateFormat) {
        return DateFormat.format(dateFormat, dateInMilliseconds).toString();
    }

    public static String convertDate(long dateInMilliseconds) {
        return DateFormat.format("MMM dd, yy HH:mm", dateInMilliseconds).toString();
    }
//    final static String TAG = "DateUtils";
//    public DateUtils(){
//
//    }
//
//    public static String getMsgDisplayTimeFromTimeStamp(long timeStamp) {
//        Date date = new Date(timeStamp);
//        SimpleDateFormat displayFormat = new SimpleDateFormat("EEE kk:mm");
//        String display = displayFormat.format(date);
//        Log.d(TAG, "Date: " + display);
//        return display;
//    }
//
//    public static Date displayDateStringToDare(String dateString) throws ParseException {
//        SimpleDateFormat displayFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
//        Date date = null;
//        date = displayFormat.parse(dateString);
//        return date;
//    }
//
//    public static String getDateDisplayString(String dateString) {
//        Date date = null;
//        String result = "";
//        try {
//            date = displayDateStringToDare(dateString);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        if (date != null) {
//            SimpleDateFormat format = new SimpleDateFormat("d MMM yyyy");
//            result = format.format(date);
//        }
//        return result;
//    }
//
//    public static String getTimeDisplayString(String dateString) {
//        Date date = null;
//        String result = "";
//        try {
//            date = displayDateStringToDare(dateString);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        if (date != null) {
//            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
//            result = format.format(date);
//        }
//        return result;
//    }


}
