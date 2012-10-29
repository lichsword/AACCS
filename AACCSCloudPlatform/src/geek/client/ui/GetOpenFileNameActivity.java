package geek.client.ui;

import geek.client.adapter.GetOpenFileNameAdapter;
import geek.util.IntentUtil;
import geek.util.Log;
import geek.util.StorageUtil;

import java.io.File;
import java.util.Stack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.kakashi.reader.R;

public class GetOpenFileNameActivity extends Activity {

	private final String TAG = GetOpenFileNameActivity.class.getSimpleName();

	private Stack<File> mHistoryDirStack = new Stack<File>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate()...OK");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_open_file_name);

		initContentView();
	}

	private EditText mOpenFileNameEditText = null;
	private Button mConfirmButton = null;
	private ListView mListView = null;

	private GetOpenFileNameAdapter mAdapter = null;

	private File mDir = null;

	private void initContentView() {
		mOpenFileNameEditText = (EditText) findViewById(R.id.openFileNameEditText);

		mConfirmButton = (Button) findViewById(R.id.openFileNameConfirmButton);
		mConfirmButton.setOnClickListener(mOnClickListener);

		mListView = (ListView) findViewById(R.id.fileList);

		if (null == mDir) {
			// TODO
			mDir = StorageUtil.getExternalStorageDirectory();
		}

		refreshList();

		mListView.setOnItemClickListener(mItemClickListener);
	}

	private File mSelectedFile = null;

	private OnItemClickListener mItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			File item = mAdapter.getItem(position);
			if (item.isDirectory()) {
				push(mDir);
				mDir = item;
				mOpenFileNameEditText.setText("");
				mSelectedFile = null;
				refreshList();
			} else {
				mOpenFileNameEditText.setText(item.getName());
				mSelectedFile = item;
			}
		}

	};

	private OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			String content = mOpenFileNameEditText.getEditableText().toString();

			if (!TextUtils.isEmpty(content)) {
				Intent data = new Intent();
				data.putExtra(IntentUtil.EXTRA_CONTENT, mSelectedFile
						.getAbsolutePath());
				setResult(Activity.RESULT_OK, data);
				finish();
			}// end if
		}

	};

	@Override
	protected void onDestroy() {
		Log.d(TAG, "onDestroy()...OK");
		super.onDestroy();
	}

	private boolean isRootDir() {
		return mHistoryDirStack.isEmpty();
	}

	private File pop() {
		return mHistoryDirStack.pop();
	}

	private void push(File dir) {
		mHistoryDirStack.push(dir);
	}

	@Override
	public boolean onKeyDown(final int keyCode, final KeyEvent event) {
		Log.d(TAG, "[onKeyDown]keyCode=" + keyCode);
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if (isRootDir()) {
				setResult(Activity.RESULT_CANCELED);
				finish();
			} else {
				mDir = pop();
				refreshList();
				return true;
			}
		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void refreshList() {
		if (null != mDir) {
			setTitle(mDir.getPath());
		} else {
			Log.e(TAG, "mDir should not be null!!!");
		}
		mAdapter = new GetOpenFileNameAdapter(getApplicationContext(), mDir
				.listFiles());
		mListView.setAdapter(mAdapter);
	}
}
