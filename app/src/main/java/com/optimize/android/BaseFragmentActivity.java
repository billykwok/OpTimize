package com.optimize.android;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;


public abstract class BaseFragmentActivity extends FragmentActivity {

	protected void startActivity(Class<? extends Activity> activityClass) {
		this.startActivity(activityClass, (Intent) null);
	}

	protected void startActivity(Class<? extends Activity> activityClass, Intent intent) {
		if (intent == null) {
            intent = new Intent(this, activityClass);
        } else {
            intent.setClass(this, activityClass);
        }
		super.startActivity(intent);
	}

	protected abstract ObjectContainer getRetainedObjectsContainer();

	public void putRetained(String key, Object object) {
		getRetainedObjectsContainer().put(key, object);
	}

	public Object getRetained(String key) {
		return getRetainedObjectsContainer().get(key);
	}

	public <T> T getRetained(String key, Class<T> cls) {
		return getRetainedObjectsContainer().get(key, cls);
	}

	public void attachFragment(int viewId, Fragment fragment) {
		getSupportFragmentManager().beginTransaction().replace(viewId, fragment).commit();
	}

}
