package org.lichsword.android.manager;

import java.io.File;

import org.lichsword.android.model.activity.ButtonInfo;
import org.lichsword.util.FileUtil;

public class RefactorFileManager {
	private static RefactorFileManager sInstance;

	private RefactorFileManager() {
		super();
		// init default target dir path
		mTargetDirPath = FileUtil.getDefaultDirPath();
		buildAllChildDir();
	}

	public static RefactorFileManager getInstance() {
		if (null == sInstance) {
			sInstance = new RefactorFileManager();
		}// end if
		return sInstance;
	}

	private String mTargetDirPath = null;

	/**
	 * Set certain target dir path to replace default dir path.
	 * 
	 * @param targetDirPath
	 * @return true if set successfully, otherwise return false.
	 */
	public boolean setTargetDirPath(String targetDirPath) {
		boolean result = true;
		if (null != targetDirPath) {
			mTargetDirPath = targetDirPath;
			buildAllChildDir();
			result = true;
		} else {
			System.out
					.println("param targetDirPath should not be null...ERROR!!!");
			result = false;
		}
		return result;
	}

	private String getTargetDirPath() {
		return mTargetDirPath;
	}

	/**
	 * eg. "E:\workspace\UXTool\drawable"
	 * 
	 * @return
	 */
	public String getTargetDrawableDirPath() {
		File file = new File(getTargetDirPath(), FileUtil.ANDROID_RES_DRAWABLE);
		return file.getAbsolutePath();
	}

	/**
	 * eg. "E:\workspace\UXTool\drawable-hdpi"
	 * 
	 * @return
	 */
	public String getTargetDrawableHdpiDirPath() {
		File file = new File(getTargetDirPath(),
				FileUtil.ANDROID_RES_DRAWABLE_HDPI);
		return file.getAbsolutePath();
	}

	/**
	 * 
	 * @return File eg. File("E:\workspace\UXTool\drawable-hdpi")
	 */
	public File getTargetDrawableHdpiDir() {
		File file = new File(getTargetDirPath(),
				FileUtil.ANDROID_RES_DRAWABLE_HDPI);
		return file;
	}

	/**
	 * 
	 * @return
	 */
	private boolean buildDrawableDir() {
		boolean result = true;
		File file = new File(getTargetDrawableDirPath());
		if (!file.exists()) {
			System.out.println("Dir NOT exist, will mkdirs:\n\t"
					+ file.getAbsolutePath());
			result = file.mkdirs();
		}// end if
		return result;
	}

	/**
	 * 
	 * @return
	 */
	private boolean buildDrawableHdpiDir() {
		boolean result = true;
		File file = new File(getTargetDrawableHdpiDirPath());
		if (!file.exists()) {
			System.out.println("Dir NOT exist, will mkdirs:\n\t"
					+ file.getAbsolutePath());
			result = file.mkdirs();
		}// end if
		return result;
	}

	/**
	 * <p>
	 * build %TARGET_DIR%/drawable/
	 * </p>
	 * <p>
	 * build %TARGET_DIR%/drawable-hdpi/
	 * </p>
	 * 
	 * @return
	 */
	private boolean buildAllChildDir() {
		boolean result = true;
		result = buildDrawableDir();
		if (result) {
			result = buildDrawableHdpiDir();
		}// end if
		return result;
	}

	/**
	 * 
	 * @param srcPath
	 * @param destPath
	 * @return
	 */
	private boolean sortDrawablePicFile(String srcPath, String destPath) {
		FileUtil.copyFile(srcPath, destPath);
		return true;
	}

	private boolean isPressedBtnPicExist(String keyword, String extendName) {
		File file = new File(getTargetDrawableHdpiDirPath(), FileUtil
				.buildPicPressedName(keyword, extendName));
		return FileUtil.isFileExist(file, false);
	}

	private boolean isDisabledBtnPicExist(String keyword, String extendName) {
		File file = new File(getTargetDrawableHdpiDirPath(), FileUtil
				.buildPicDisabledName(keyword, extendName));
		return FileUtil.isFileExist(file, false);
	}

	private boolean isDefaultBtnPicExist(String keyword, String extendName) {
		File file = new File(getTargetDrawableHdpiDirPath(), FileUtil
				.buildPicDefaultName(keyword, extendName));
		return FileUtil.isFileExist(file, false);
	}

	/**
	 * 
	 * @param keyword
	 * @param extendName
	 * @return
	 */
	public boolean sortDrawableHdpiXMLFile(String keyword,
			ButtonInfo pressedButtonInfo, ButtonInfo disabledButtonInfo,
			ButtonInfo defaultButtonInfo) {
		boolean result = false;

		File xmlFile = FileUtil.buildSelectorXMLFile(getTargetDirPath(),
				keyword);
		FileUtil.writeDataToFile(xmlFile, FileUtil
				.buildSelectorXMLBeginToByte());

		StringBuilder sb = new StringBuilder();
		sb.append(FileUtil.buildSelectorXMLBeginToString());

		// 1.check pressed btn pic exist
		if (null != pressedButtonInfo && pressedButtonInfo.isFileOK()) {
			sb.append('\n');
			sb.append(FileUtil.TABEL_CHAR);
			sb.append(FileUtil.buildBtnPressedStateToString(keyword));
			result = true;
		} else {
			System.out.println("No found pressed pic:"
					+ FileUtil.buildPicPressedName(keyword, defaultButtonInfo
							.getExtentNameForPicture()));
		}

		// 2.check disabled btn pic exist
		if (null != disabledButtonInfo && disabledButtonInfo.isFileOK()) {
			sb.append('\n');
			sb.append(FileUtil.TABEL_CHAR);
			sb.append(FileUtil.buildBtnDisabledStateToString(keyword));
			result = true;
		} else {
			System.out.println("No found disabled pic:"
					+ FileUtil.buildPicDisabledName(keyword, disabledButtonInfo
							.getExtentNameForPicture()));
		}
		// 3.check default btn pic exist
		if (null != defaultButtonInfo && defaultButtonInfo.isFileOK()) {
			sb.append('\n');
			sb.append(FileUtil.TABEL_CHAR);
			sb.append(FileUtil.buildBtnDefaultStateToString(keyword));
			result = true;
		} else {
			System.out.println("No found default pic:"
					+ FileUtil.buildPicDefaultName(keyword, defaultButtonInfo
							.getExtentNameForPicture()));
		}

		sb.append('\n');
		sb.append(FileUtil.buildSelectorXMLEndToString());

		String sortedXMLString = sb.toString();
		FileUtil.writeDataToFile(xmlFile, sortedXMLString.getBytes());

		return result;
	}

	public static void main(String args[]) {
		RefactorFileManager manager = RefactorFileManager.getInstance();
		manager.buildAllChildDir();
		// manager.sortDrawableHdpiXMLFile("test", ".png");
	}
}
