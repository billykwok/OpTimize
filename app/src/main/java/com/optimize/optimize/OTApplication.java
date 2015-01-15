package com.optimize.optimize;

import android.app.Application;

import com.optimize.android.ApplicationBase;
import com.optimize.optimize.models.OTEvent;
import com.optimize.optimize.models.OTUserService;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by samwalker on 4/1/15.
 */
public class OTApplication extends ApplicationBase {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "ZsFfXsCtRyddAgafqyaQYeCwlm0wpgSbGvi9pllv", "isCIVGhBxffrXr7ZIKsEGgogusoNPy7bFB39lmlj");
        ParseObject.registerSubclass(OTEvent.class);
    }
}
