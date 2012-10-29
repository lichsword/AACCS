package com.kakashi.reader.client.ui;

import java.util.ArrayList;

import org.apache.http.NameValuePair;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.kakashi.reader.R;
import com.kakashi.reader.service.ReaderService;
import com.kakashi.reader.util.reader.ReaderUtil;

public class SignInActivity extends Activity {

	private final String TAG = SignInActivity.class.getSimpleName();

	private EditText mGmailEditText;
	private EditText mPasswordEditText;
	private Button mSignInButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		mSignInBroadcastReceiver.register();
		initContentView();
	}

	@Override
	protected void onDestroy() {
		mSignInBroadcastReceiver.unregister();
		super.onDestroy();
	}

	private void initContentView() {
		mGmailEditText = (EditText) findViewById(R.id.GmailEditText);
		mPasswordEditText = (EditText) findViewById(R.id.PasswordEditText);
		mSignInButton = (Button) findViewById(R.id.SignInButton);
		mSignInButton.setOnClickListener(mSignInOnClickListener);
	}

	private final OnClickListener mSignInOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Log.d(TAG, "SignIn...BUTTON...CLICK ");
			startSignIn();
		}

	};

	ArrayList<NameValuePair> mNameValueList = new ArrayList<NameValuePair>();

	private void startSignIn() {
		Log.d(TAG, "startSignIn...OK");
		String gmail = mGmailEditText.getEditableText().toString();
		String password = mPasswordEditText.getEditableText().toString();
		Log.v(TAG, "gmail = " + gmail);
		Log.v(TAG, "password = " + password);
		Intent intent = new Intent(SignInActivity.this, ReaderService.class);
		intent.setAction(ReaderUtil.COMMAND_SIGN_IN);
		intent.putExtra(ReaderUtil.EXTRA_GMAIL, gmail);
		intent.putExtra(ReaderUtil.EXTRA_PASSWORD, password);
		startService(intent);

		Log.d(TAG, "startService...OK");
	}

	private final int DIALOG_ID_SIGN_IN = 1;

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_ID_SIGN_IN:
			ProgressDialog progressDialog = new ProgressDialog(
					SignInActivity.this);
			progressDialog.setMessage(getString(R.string.msg_Log_in));
			return progressDialog;
		default:
			break;
		}
		return super.onCreateDialog(id);
	}

	private SignInBroadcastReceiver mSignInBroadcastReceiver = new SignInBroadcastReceiver();

	private class SignInBroadcastReceiver extends BroadcastReceiver {

		private boolean mIsRegistered;

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Log.d(TAG, "receiver broadcast action = " + action);
			if (action.equals(ReaderUtil.ACTION_SIGN_IN_START)) {
				Log.d(TAG, "MSG_SHOW_LOADING_COMPLETE, send ...OK");
			} else if (action.equals(ReaderUtil.ACTION_SIGN_IN_SUCCESS)) {
				// TODO sign in success...
				startActivity(new Intent(SignInActivity.this,
						MainActivity.class));
				finish();
			} else if (action.equals(ReaderUtil.ACTION_SIGN_IN_FAILED)) {
				// TODO sign in failed...
			}
		}

		/**
		 * Unregister from this enclosing activity.
		 */
		public final void unregister() {
			if (mIsRegistered) {
				unregisterReceiver(this);
				mIsRegistered = false;
				Log.d(TAG, "SignInBroadcastReceiver has unregister.");
			}
		}

		/**
		 * Register the broadcast receiver to this enclosing activity.
		 */
		public final void register() {
			if (mIsRegistered) {
				Log.d(TAG, "SignInBroadcastReceiver has registered ago.");
				return;
			}
			Log
					.d(TAG,
							"SignInBroadcastReceiver never register, register right now.");
			final IntentFilter filter = new IntentFilter();
			filter.addAction(ReaderUtil.ACTION_SIGN_IN_START);
			filter.addAction(ReaderUtil.ACTION_SIGN_IN_SUCCESS);
			filter.addAction(ReaderUtil.ACTION_SIGN_IN_FAILED);
			registerReceiver(this, filter);
			mIsRegistered = true;
		}

	}
}