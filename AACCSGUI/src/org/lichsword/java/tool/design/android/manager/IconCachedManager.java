package org.lichsword.java.tool.design.android.manager;

import java.io.File;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.lichsword.java.tool.design.android.model.activity.ButtonInfo;
import org.lichsword.java.tool.design.android.model.activity.OptionMenuModel;
import org.lichsword.java.tool.design.android.model.activity.OptionMenuModel.OptionMenuItem;
import org.lichsword.java.tool.design.util.ColorUtil;
import org.lichsword.java.tool.design.util.FileUtil;
import org.lichsword.java.tool.design.util.TipUtil;

public class IconCachedManager {
	private static IconCachedManager sInstance;

	private IconCachedManager() {
		super();
	}

	public static IconCachedManager getInstance() {
		if (null == sInstance) {
			sInstance = new IconCachedManager();
		}// end if
		return sInstance;
	}

	private HashMap<Integer, Icon> mCachedIcons = new HashMap<Integer, Icon>();

	public static final String DEMO_ICON_PATH = "D:/temp/demo_icon.jpg";

	public void addIcon(String iconPath) {
		String temp = new String(iconPath);
		temp = temp.replace('\\', '/');
		ImageIcon icon = new ImageIcon(temp);
		mCachedIcons.put(iconPath.hashCode(), icon);
	}

	public void removeIcon(String iconPath) {
		if (null == iconPath) {
			return;
		}// end if
		mCachedIcons.remove(iconPath.hashCode());
	}

	public Icon getIcon(String iconPath) {
		return mCachedIcons.get(iconPath.hashCode());
	}

	@SuppressWarnings("unused")
	private boolean clearCache() {
		mCachedIcons.clear();
		return true;
	}

	private ButtonInfo mDefaultButtonInfo = new ButtonInfo(
			FileUtil.SUFFIX_BTN_DEFAULT, FileUtil.SUFFIX_BTN_DEFAULT);
	private ButtonInfo mPressedButtonInfo = new ButtonInfo(
			FileUtil.SUFFIX_BTN_PRESSED, FileUtil.SUFFIX_BTN_PRESSED);
	private ButtonInfo mDisabledButtonInfo = new ButtonInfo(
			FileUtil.SUFFIX_BTN_DISABLED, FileUtil.SUFFIX_BTN_DISABLED);

	public void DragFileChangeHappen(String filePath, int whichPic) {
		switch (whichPic) {
		case ButtonInfo.PIC_DEFAULT:
			refreshButtonPicInfo(mDefaultButtonInfo, filePath);
			break;
		case ButtonInfo.PIC_DISABLED:
			refreshButtonPicInfo(mDisabledButtonInfo, filePath);
			break;
		case ButtonInfo.PIC_PRESSED:
			refreshButtonPicInfo(mPressedButtonInfo, filePath);
			break;
		default:
			break;
		}
	}

	private void refreshButtonPicInfo(ButtonInfo info, String picFilePath) {
		// delete old
		removeIcon(info.getLastIconFilePath());
		// set new path
		info.setLastIconFilePath(picFilePath);
		// add new path
		addIcon(picFilePath);
	}

	public void confirmBuildButton(String keyword) {
		BuildButtonInfoThread mBuildThread = new BuildButtonInfoThread(keyword);
		mBuildThread.start();
	}

	private class BuildButtonInfoThread extends Thread {

		private String mKeyword;

		public BuildButtonInfoThread(String keyword) {
			super();
			mKeyword = keyword;
		}

		@Override
		public void run() {
			// start build
			if (null == mListener) {
				System.out
						.println("BuildProcessListener should NOT be null...ERROR");
				return;
			}// end if

			// begin build
			mListener.buildBegin(null);
			// check param
			mListener.buildProcess("prepared build, checking param...",
					ColorUtil.V);
			// check default picture file exist
			checkPictureParam(mDefaultButtonInfo);
			checkPictureParam(mPressedButtonInfo);
			checkPictureParam(mDisabledButtonInfo);
			// begin transfer rename picture.
			mListener.buildProcess("Transfer renamed picture...", ColorUtil.V);
			transferRenamePicture(mDefaultButtonInfo, mKeyword);
			transferRenamePicture(mPressedButtonInfo, mKeyword);
			transferRenamePicture(mDisabledButtonInfo, mKeyword);
			// generater xml file
			mListener.buildProcess("Generater selector XML file...",
					ColorUtil.V);
			RefactorFileManager.getInstance()
					.sortDrawableHdpiXMLFile(mKeyword, mPressedButtonInfo,
							mDisabledButtonInfo, mDefaultButtonInfo);
			mListener.buildProcess("Generater selector XML file...OK",
					ColorUtil.V);
			mListener.buildProcess("Finish", ColorUtil.V);
			mListener.buildEnd(null);
			super.run();
		}

	};

	private BuildProcessListener mListener;

	public void setBuildProcessListener(BuildProcessListener listener) {
		mListener = listener;
	}

	private boolean checkPictureParam(ButtonInfo info) {
		boolean isPictureOK = true;
		if (info.checkPictureFileOK()) {
			mListener.buildProcess("check " + info.getTAG()
					+ " picture for button exist, prepare for sort...OK",
					ColorUtil.I);
		} else {
			mListener.buildProcess("check " + info.getTAG()
					+ " picture for button miss...Jump Over", ColorUtil.E);
		}
		return isPictureOK;
	}

	private boolean transferRenamePicture(ButtonInfo info, String keyword) {
		boolean result = true;
		boolean fileOK = info.isFileOK();
		if (fileOK) {
			String refactName = info.getRefactName(keyword);
			File file = new File(RefactorFileManager.getInstance()
					.getTargetDrawableHdpiDirPath(), refactName);
			FileUtil.copyFile(info.getLastIconFilePath(),
					file.getAbsolutePath());
			mListener.buildProcess("Transfer " + info.getTAG()
					+ " picture...OK", ColorUtil.I);
			result = true;
		} else {
			mListener.buildProcess("Transfer " + info.getTAG()
					+ " picture...Jump Over", ColorUtil.E);
			result = false;
		}
		return result;
	}

	public String getRefactName(String keyword, int whichPic) {
		String refactName = ButtonInfo.UNKNOW_NAME;
		ButtonInfo buttonInfo = getButtonInfo(whichPic);
		if (null != buttonInfo) {
			refactName = buttonInfo.getRefactName(keyword);
		} else {
			refactName = ButtonInfo.UNKNOW_NAME;
		}
		return refactName;
	}

	private ButtonInfo getButtonInfo(int whichButton) {
		ButtonInfo info = null;
		switch (whichButton) {
		case ButtonInfo.PIC_PRESSED:
			info = mPressedButtonInfo;
			break;
		case ButtonInfo.PIC_DISABLED:
			info = mDisabledButtonInfo;
			break;
		case ButtonInfo.PIC_DEFAULT:
			info = mDefaultButtonInfo;
			break;
		default:
			break;
		}
		return info;
	}

	public String preCheckButtonInfo(boolean checkDefault,
			boolean checkPressed, boolean checkDisabled) {
		String tip = TipUtil.INFO_CHECK_PICTIRE_PASSED;

		if (checkDefault) {
			if (!mDefaultButtonInfo.isFileOK()) {
				tip = TipUtil.ERROR_DEFAULT_PICTURE_NOT_DRAG_RIGHT;
			}// end if
		}// end if
		if (checkPressed) {
			if (!mPressedButtonInfo.isFileOK()) {
				tip = TipUtil.ERROR_PRESSED_PICTURE_NOT_DRAG_RIGHT;
			}// end if
		}// end if
		if (checkDisabled) {
			if (!mDisabledButtonInfo.isFileOK()) {
				tip = TipUtil.ERROR_DISABLED_PICTURE_NOT_DRAG_RIGHT;
			}// end if
		}// end if

		return tip;
	}

	public void clearAllInfo() {
		mCachedIcons.clear();
		mDefaultButtonInfo.reset();
		mPressedButtonInfo.reset();
		mDisabledButtonInfo.reset();
	}

	public void refreshOptionMenuItem(OptionMenuItem item, String iconImagePath) {
		// delete old
		removeIcon(item.getIconImagePath());
		// set new path
		item.setIconImagePath(iconImagePath);
		// add new path
		addIcon(iconImagePath);
	}
}
