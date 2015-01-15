package com.optimize.android;

import android.app.Activity;
import android.content.Intent;

public class ApplicationBase extends android.app.Application {

	private static ApplicationBase instance;

	public static ApplicationBase get() {
		return instance;
	}

	public ApplicationBase() {
		instance = this;
	}

	public void startActivity(Class<? extends Activity> activityClass) {
		Intent intent = new Intent(this, activityClass);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		super.startActivity(intent);
	}

}
