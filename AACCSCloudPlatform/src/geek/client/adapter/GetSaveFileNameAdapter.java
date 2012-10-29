package geek.client.adapter;

import java.io.File;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kakashi.reader.R;

public class GetSaveFileNameAdapter extends BaseAdapter {

	@SuppressWarnings("unused")
	private final String TAG = GetSaveFileNameAdapter.class.getSimpleName();

	private File[] mFileList = null;

	public GetSaveFileNameAdapter(Context context, File[] fileList) {
		super();
		mFileList = fileList;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mFileList.length;
	}

	@Override
	public File getItem(int position) {
		return mFileList[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private final LayoutInflater mInflater;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final File item = getItem(position);
		if (null == convertView) {
			convertView = mInflater.inflate(R.layout.list_item_save_file_name,
					null);
		}// end if

		ViewHolder viewHolder = (ViewHolder) convertView.getTag();
		if (null == viewHolder) {
			viewHolder = new ViewHolder();
			viewHolder.mIconImageView = (ImageView) convertView
					.findViewById(R.id.iconFolderImageView);
			viewHolder.mTitleTextView = (TextView) convertView
					.findViewById(R.id.folderNameTextView);
		}// end if

		if (item.isDirectory()) {
			viewHolder.mIconImageView
					.setImageResource(R.drawable.geek_icon_folder);
		} else {
			viewHolder.mIconImageView
					.setImageResource(R.drawable.geek_icon_file);
		}
		viewHolder.mTitleTextView.setText(item.getName());

		convertView.setTag(viewHolder);

		return convertView;
	}

	private class ViewHolder {
		public ImageView mIconImageView;
		public TextView mTitleTextView;
	}

}
