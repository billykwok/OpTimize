package com.optimize.optimize.models;

import android.util.Log;

import com.optimize.optimize.calendar.CalendarEvent;
import com.parse.LogInCallback;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.List;

/**
 * Created by samwalker on 4/1/15.
 */
public class OTUser {

    private String email, password, username;
    private ParseUser parseUser;
    private List<CalendarEvent> calendarEvents;

    final String TAG = "OTUser";

    public OTUser() {
        parseUser = new ParseUser();
    }

    private OTUser(ParseUser parseUser) {
        updateInfo(parseUser);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        parseUser.setEmail(email);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        parseUser.setPassword(password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        parseUser.setUsername(username);
    }

    public void register(SignUpCallback signUpCallback) {
        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            Log.e(TAG, "field(s) is empty");
        } else {
            parseUser.signUpInBackground(signUpCallback);
        }
    }

    public static void login(String username, String password, LogInCallback logInCallback) {
        if (username.isEmpty() || password.isEmpty()) {
            Log.e("OTUser", "field(s) is empty");
        } else {
            ParseUser.logInInBackground(username, password, logInCallback);
        }
    }

    public static OTUser getCurrentOTUser(){
        return ParseUser.getCurrentUser() != null?new OTUser(ParseUser.getCurrentUser()):null;
    }

    public ParseUser getParseUser() {
        return parseUser;
    }

    void updateInfo(ParseUser parseUser) {
        this.email = parseUser.getEmail();
        this.username = parseUser.getUsername();
        this.password = "";
    }
}
