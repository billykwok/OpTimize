package com.optimize.android;

import java.util.HashMap;

public class ObjectContainer extends HashMap<String, Object> {

	public Object get(String key) {
		return super.get(key);
	}

	public <T> T get(String key, Class<T> cls) {
		Object object = get(key);
		return cls.isInstance(object) ? cls.cast(object) : null;
	}

}
