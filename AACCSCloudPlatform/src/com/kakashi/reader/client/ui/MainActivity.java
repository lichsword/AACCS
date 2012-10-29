package com.kakashi.reader.client.ui;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

import com.kakashi.reader.R;

public class MainActivity extends ActivityGroup {

	private LocalActivityManager mLocalActivityManager;
	private FrameLayout mContentLayout;

	private View mSubsView;
	private Activity mSubsActivity;
	private final String ACTIVITY_ID_SUBS = "subs";

	private View mTagsView;
	private Activity mTagsActivity;
	private final String ACTIVITY_ID_TAGS = "tags";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initContentView();

		ensureSubListActivity();

		ensureTagListActivity();

		// TODO
		if (true) {
			showSubsWindow();
		} else {
			showTagsWindow();
		}

	}

	private void initContentView() {
		mLocalActivityManager = getLocalActivityManager();
		mContentLayout = (FrameLayout) findViewById(R.id.contentLayout);
	}

	private void ensureSubListActivity() {
		if (mSubsView == null || mSubsActivity == null) {
			final Intent intent = new Intent(this,
					SubscriptionListActivity.class);
			final Window window = mLocalActivityManager.startActivity(
					ACTIVITY_ID_SUBS, intent);
			final View view = window.getDecorView();
			if (null == view.getParent()) {
				mContentLayout.addView(view, LayoutParams.FILL_PARENT,
						LayoutParams.FILL_PARENT);
				mSubsActivity = (SubscriptionListActivity) mLocalActivityManager
						.getActivity(ACTIVITY_ID_SUBS);
				mSubsView = view;
			}
		}
	}

	private void ensureTagListActivity() {
		if (mTagsView == null || mTagsActivity == null) {
			final Intent intent = new Intent(this,
					SubscriptionListActivity.class);
			final Window window = mLocalActivityManager.startActivity(
					ACTIVITY_ID_TAGS, intent);
			final View view = window.getDecorView();
			if (null == view.getParent()) {
				mContentLayout.addView(view, LayoutParams.FILL_PARENT,
						LayoutParams.FILL_PARENT);
				mTagsActivity = (SubscriptionListActivity) mLocalActivityManager
						.getActivity(ACTIVITY_ID_TAGS);
				mTagsView = view;
			}
		}
	}

	private void showSubsWindow() {

		final View contentView = mContentLayout;
		final View subsView = mSubsView;
		subsView.setVisibility(View.VISIBLE);
		subsView.bringToFront();

		if (contentView != null) {
			contentView.setVisibility(View.GONE);
		}

	}

	private void showTagsWindow() {

		final View contentView = mContentLayout;
		final View tagsView = mTagsView;
		tagsView.setVisibility(View.VISIBLE);
		tagsView.bringToFront();

		if (contentView != null) {
			contentView.setVisibility(View.GONE);
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

}
