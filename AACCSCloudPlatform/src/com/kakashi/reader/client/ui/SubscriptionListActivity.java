package com.kakashi.reader.client.ui;

import geek.util.Log;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.kakashi.reader.R;
import com.kakashi.reader.busniess.pref.Prefs;
import com.kakashi.reader.model.Subscription;
import com.kakashi.reader.util.reader.ReaderUtil;

public class SubscriptionListActivity extends ListActivity {

	private final String TAG = SubscriptionListActivity.class.getSimpleName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mSubscriptionBroadcastReceiver.register();
		setContentView(R.layout.activity_subscritpion);
		
		loadDataFromDatabase();
	}

	
	private final int MSG_LOAD_START = 0;
	private final int MSG_LOAD_FINISH = 1;
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_LOAD_START:
				// TODO
				break;
			case MSG_LOAD_FINISH:
				// TODO
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
		
	};
	
	/**
	 * return true if has some cached in database.
	 */
	private boolean loadDataFromDatabase() {
		
		boolean result = false;
		
		Context c = getApplicationContext();
		String where = null;
		String orderby = Subscription._NEWEST_ITEM_TIME + " desc";
		if (Prefs.isViewUnreadOnly(c)) {
			where = Subscription._UNREAD_COUNT + " > 0";
		}
		
		Cursor cursor= managedQuery(Subscription.CONTENT_URI, null, where, null, orderby);
		if(null != cursor){
			result = true;
			Cursor csr = new Subscription.FilterCursor(cursor);
			if (null == this.mSubscriptionAdapter) {
				this.mSubscriptionAdapter = new SubscriptionAdapter(this, csr);
				setListAdapter(this.mSubscriptionAdapter);
			} else {
				this.mSubscriptionAdapter.changeCursor(csr);
			}
		}else{
			result = false;
			// means no cache data.
			Log.d(TAG, "no cache data in database");
		}
		return result;
	}

	private SubscriptionAdapter mSubscriptionAdapter;

	private class SubscriptionAdapter extends ResourceCursorAdapter {

		private SubscriptionAdapter(Context c, Cursor csr) {
			super(c, R.layout.subscription_list_item, csr, false);
		}

		@Override
		public void bindView(View view, Context c, Cursor csr) {
			Subscription.FilterCursor subCsr = (Subscription.FilterCursor) csr;

			ImageView iconView = (ImageView) view.findViewById(R.id.icon);
			TextView titleView = (TextView) view.findViewById(R.id.title);
			TextView newestView = (TextView) view.findViewById(R.id.newest);

			Subscription sub = subCsr.getSubscription();
			titleView.setText(sub.toLabelWithUnread());

			long newestItemTime = sub.getNewestItemTime();
			if (newestItemTime > 0) {
				newestView.setText(ReaderUtil.formatTimeAgo(newestItemTime));
			}

			Bitmap icon = sub.getIcon(SubscriptionListActivity.this);
			if (icon == null) {
				iconView.setImageResource(R.drawable.geek_icon);
			} else {
				iconView.setImageBitmap(icon);
			}

			view.setTag(sub.getId());
		}
	}

	SubscriptionBroadcastReceiver mSubscriptionBroadcastReceiver = new SubscriptionBroadcastReceiver();

	private class SubscriptionBroadcastReceiver extends BroadcastReceiver {

		private boolean mIsRegistered;

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Log.d(TAG, "receiver broadcast action = " + action);
			if (action.equals(ReaderUtil.ACTION_SYNC_SUBSCRIPTION_START)) {
			} else if (action
					.equals(ReaderUtil.ACTION_SYNC_SUBSCRIPTION_COMPLETE)) {
			} else if (action.equals(ReaderUtil.ACTION_UNREAD_RSS_MODIFIED)) {
				// TODO sign in failed...
			}
		}

		/**
		 * Unregister from this enclosing activity.
		 */
		@SuppressWarnings("unused")
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
			filter.addAction(ReaderUtil.ACTION_SYNC_SUBSCRIPTION_COMPLETE);
			filter.addAction(ReaderUtil.ACTION_UNREAD_RSS_MODIFIED);
			filter.addAction(ReaderUtil.ACTION_SYNC_SUBSCRIPTION_START);
			registerReceiver(this, filter);
			mIsRegistered = true;
		}

	}
}
