/**
 * @author yuewang
 */
package com.kakashi.reader.service;

import geek.util.Log;

import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;

import com.kakashi.reader.busniess.rss.GoogleReaderClient;
import com.kakashi.reader.busniess.rss.ReaderClient;
import com.kakashi.reader.busniess.rss.ReaderException;
import com.kakashi.reader.util.reader.ReaderUtil;

public class ReaderService extends Service {

	private final String TAG = ReaderService.class.getSimpleName();

	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "onBind()...OK");
		return null;
	}

	private ReaderClient mReaderClient = null;

	@Override
	public void onCreate() {
		Log.v(TAG, "onCreate()...OK");
		mReaderClient = new GoogleReaderClient(getApplicationContext());
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Log.v(TAG, "onStart()...OK");
		processAction(intent);
		super.onStart(intent, startId);
	}

	private void processAction(Intent intent) {
		String action = intent.getAction();
		Log.d(TAG, "processAction: action = " + action);

		if (action.equals(ReaderUtil.COMMAND_SIGN_IN)) {
			Log.d(TAG, "COMMAND_SIGN_IN...OK");
			if(signIn(intent)){
				Log.d(TAG, "Sign in...OK");
				broadcast(ReaderUtil.ACTION_SIGN_IN_SUCCESS);
			}else{
				Log.e(TAG, "Sign in...FAILED");
				broadcast(ReaderUtil.ACTION_SIGN_IN_FAILED);
			}
		} else {
			// TODO others
		}

	}

	private boolean signIn(Intent intent) {
		String gmail = intent.getStringExtra(ReaderUtil.EXTRA_GMAIL);
		String password = intent
				.getStringExtra(ReaderUtil.EXTRA_PASSWORD);
		Log.d(TAG, "gmail = " + gmail);
		Log.d(TAG, "password = " + password);
		return signIn(gmail, password);
	}

	private boolean signIn(String gmail, String password) {
		boolean success = false;
		try {
			broadcast(ReaderUtil.ACTION_SIGN_IN_START);
			if (mReaderClient.login(gmail, password)) {
				// TODO save account
				success = true;
			}
		} catch (IOException e) {
			success = false;
			e.printStackTrace();
		} catch (ReaderException e) {
			success = false;
			e.printStackTrace();
		}
		return success;
	}
	
	
	
	
	
	/**
	 * Send an broadcast with specified action and {@linkplain Intent}.
	 * 
	 * @param action
	 *            The action to send.
	 * @param intent
	 *            the {@linkplain Intent} to send.
	 */
	private void broadcast(final String action) {
		broadcast(action, null);
	}

	/**
	 * Send an broadcast with specified action and {@linkplain Intent}.
	 * 
	 * @param action
	 *            The action to send.
	 * @param intent
	 *            the {@linkplain Intent} to send.
	 */
	private void broadcast(final String action, Intent intent) {
		if (TextUtils.isEmpty(action) && null == intent) {
			throw new IllegalArgumentException(
					"action and intent can not be null at the same time.");
		}
		if (null == intent) {
			intent = new Intent(action);
		} else if (!TextUtils.isEmpty(action)) {
			intent.setAction(action);
		}
		this.sendBroadcast(intent);
	}
}
