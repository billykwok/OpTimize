package com.optimize.optimize;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by samwalker on 4/1/15.
 */
public class OTApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "ZsFfXsCtRyddAgafqyaQYeCwlm0wpgSbGvi9pllv", "isCIVGhBxffrXr7ZIKsEGgogusoNPy7bFB39lmlj");
    }
}