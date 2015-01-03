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
        Parse.initialize(this, "wPzAhT9KqFmASIXkyF2od2h6cBuJUmpuB1UbtCC0", "QUItckEDHzCehcyzYW2on1s87UE7wi6q1exbBSsn");
    }
}
