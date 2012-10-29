package com.kakashi.reader;

import geek.util.Log;
import android.app.Application;

public class ReaderApplication extends Application {

	private final String TAG = ReaderApplication.class.getSimpleName();

	private static ReaderApplication sApplicationInstance;

	@Override
	public void onCreate() {
		Log.setFilterLevel(Log.ALL);
		Log.d(TAG, "ReaderApplication onCreate()...OK");
		sApplicationInstance = this;
		super.onCreate();
	}

	public static ReaderApplication getInstance() {
		return sApplicationInstance;
	}

}
