package org.lichsword.android.model.activity;

import java.io.File;

import org.lichsword.swt.util.FileUtil;

public class ButtonInfo {

	private String TAG;
	//
	public static final int PIC_DEFAULT = 0;
	public static final int PIC_PRESSED = 1;
	public static final int PIC_DISABLED = 2;
	//
	public static final String UNKNOW_NAME = "N/A";
	//
	private String mLastIconFilePath;
	//
	private String mExtendName;
	private String mSuffixName;

	public void reset() {
		mExtendName = null;
		mLastIconFilePath = null;
	}

	//
	public ButtonInfo(String tag, String suffixName) {
		super();
		setTAG(tag);
		setSuffixName(suffixName);
	}

	public void setLastIconFilePath(String mLastIconFilePath) {
		this.mLastIconFilePath = mLastIconFilePath;
		mExtendName = FileUtil.getExtentNameForPicture(mLastIconFilePath);
	}

	public String getLastIconFilePath() {
		return mLastIconFilePath;
	}

	public String getExtentNameForPicture() {
		return mExtendName;
	}

	private void setTAG(String tag) {
		TAG = tag;
	}

	public String getTAG() {
		return TAG;
	}

	public boolean isFileOK() {
		return checkPictureFileOK();
	}

	public boolean checkPictureFileOK() {
		boolean result = true;
		if (null != mLastIconFilePath) {
			File file = new File(mLastIconFilePath);
			result = file.exists();
		} else {
			result = false;
		}
		return result;
	}

	private void setSuffixName(String mSuffixName) {
		this.mSuffixName = mSuffixName;
	}

	public String getSuffixName() {
		return mSuffixName;
	}

	public String getRefactName(String keyword) {
		String result = UNKNOW_NAME;
		if (checkPictureFileOK()) {
			StringBuilder sb = new StringBuilder();
			sb.append(FileUtil.PREFIX_BTN);
			sb.append(FileUtil.LINK_CHAR);
			sb.append(keyword);
			sb.append(FileUtil.LINK_CHAR);
			sb.append(mSuffixName);
			sb.append('.');
			sb.append(mExtendName);
			result = sb.toString();
		} else {
			result = UNKNOW_NAME;
		}
		return result;
	}

}
