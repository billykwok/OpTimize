package com.optimize.optimize;

import com.optimize.android.BaseApplication;
import com.optimize.optimize.models.OTEvent;
import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by samwalker on 4/1/15.
 */
public class OTApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "ZsFfXsCtRyddAgafqyaQYeCwlm0wpgSbGvi9pllv", "isCIVGhBxffrXr7ZIKsEGgogusoNPy7bFB39lmlj");
        ParseObject.registerSubclass(OTEvent.class);
    }
}
