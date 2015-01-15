package com.optimize.android;

import android.app.Activity;
import android.content.Intent;

public class BaseApplication extends android.app.Application {

	private static BaseApplication instance;

	public static BaseApplication get() {
		return instance;
	}

	public BaseApplication() {
		instance = this;
	}

	public void startActivity(Class<? extends Activity> activityClass) {
		Intent intent = new Intent(this, activityClass);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		super.startActivity(intent);
	}

}
