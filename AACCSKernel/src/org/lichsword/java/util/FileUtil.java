/******************************************************************************
 *
 * Copyright 2012 Lichsword Studio, All right reserved.
 *
 * File name   : DotaCategory.java
 * Create time : 2012-10-11
 * Author      : lichsword
 * Description : TODO
 *
 *****************************************************************************/
package org.lichsword.java.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

public class FileUtil {

	private static boolean debug = true;

	public static void enableDebug(boolean enable) {
		debug = enable;
	}

	/**
	 * 4 space char
	 */
	public static final String TABEL_CHAR = "    ";
	/**
	 * '_'
	 */
	public static final char LINK_CHAR = '_';
	/**
	 * "btn"
	 */
	public static final String PREFIX_BTN = "btn";
	private static final String PREFIX_MENU = "menu";
	/**
	 * "default"
	 */
	public static final String SUFFIX_BTN_DEFAULT = "default";
	/**
	 * "pressed"
	 */
	public static final String SUFFIX_BTN_PRESSED = "pressed";
	/**
	 * "disabled"
	 */
	public static final String SUFFIX_BTN_DISABLED = "disabled";
	//
	private static final String TYPE_9_PNG = "9.png";
	private static final String TYPE_PNG = "png";
	private static final String TYPE_JPG = "jpg";
	//
	private static final String SELECTOR_HEAD_LINE_1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	private static final String SELECTOR_HEAD_LINE_2 = "<selector xmlns:android=\"http://schemas.android.com/apk/res/android\">";
	//
	private static final String SELECTOR_STATE_PRESSED_BEGIN = "    <item android:state_pressed=\"true\"  android:drawable=\"@drawable/";
	private static final String SELECTOR_STATE_PRESSED_END = "\" />";
	//
	private static final String SELECTOR_STATE_DEFAULT_BEGIN = "    <item android:drawable=\"@drawable/";
	private static final String SELECTOR_STATE_DEFAULT_END = "\" />";
	//
	private static final String SELECTOR_STATE_DISABLE_BEGIN = "    <item android:state_enabled=\"false\" android:drawable=\"@drawable/";
	private static final String SELECTOR_STATE_DISABLE_END = "\" />";
	//
	private static final String SELECTOR_TAIL_LINE = "</selector>";
	/**
	 * The folder name "drawable"
	 */
	public static final String ANDROID_RES_DRAWABLE = "drawable";

	/**
	 * The folder name "drawable-hdpi"
	 */
	public static final String ANDROID_RES_DRAWABLE_HDPI = "drawable-hdpi";

	/**
	 * 
	 */
	private static final String[] SUPPORT_PIC_TYPE = { TYPE_9_PNG, TYPE_PNG,
			TYPE_JPG };

	@SuppressWarnings("unused")
	private String buildName(String kernelWord, String extendName, TYPE type,
			STATE state) {
		String result;
		if (TYPE.BUTTON == type) {
			result = buildButtonName(kernelWord, state);
		} else if (TYPE.MENU == type) {
			result = buildMenuName(kernelWord);
		} else {
			result = null;
		}
		return result;
	}

	private String buildMenuName(String kernelWord) {
		String result;
		StringBuilder sb = new StringBuilder();
		sb.append(PREFIX_MENU);
		sb.append(LINK_CHAR);
		sb.append(kernelWord);
		result = sb.toString();
		return result;
	}

	private String buildButtonName(String kernelWord, STATE state) {
		String result;
		StringBuilder sb = new StringBuilder();
		sb.append(PREFIX_BTN);
		sb.append(LINK_CHAR);
		sb.append(kernelWord);
		sb.append(LINK_CHAR);
		if (STATE.BTN_NORMAL == state) {
			sb.append(SUFFIX_BTN_DEFAULT);
		} else if (STATE.BTN_PRESSED == state) {
			sb.append(SUFFIX_BTN_PRESSED);
		} else if (STATE.BTN_DISABLED == state) {
			sb.append(SUFFIX_BTN_DISABLED);
		}// end if

		result = sb.toString();
		return result;
	}

	private static enum TYPE {
		BUTTON, MENU
	}

	private static enum STATE {
		BTN_NORMAL, BTN_PRESSED, BTN_DISABLED
	}

	public static String getDefaultDirPath() {
		return System.getProperty("user.dir").replace("\\", "/");
	}

	public static boolean isSupportPicType(String picFilePath) {
		for (int i = 0; i < SUPPORT_PIC_TYPE.length; i++) {
			if (picFilePath.endsWith(SUPPORT_PIC_TYPE[i])) {
				return true;
			}// end if
		}// end for
		return false;
	}

	public static String getExtentNameForPicture(String picFilePath) {
		for (int i = 0; i < SUPPORT_PIC_TYPE.length; i++) {
			if (picFilePath.endsWith(SUPPORT_PIC_TYPE[i])) {
				return SUPPORT_PIC_TYPE[i];
			}// end if
		}// end for
		return null;
	}

	/**
	 * Clean a specified directory.
	 * 
	 * @param dir
	 *            the directory to clean.
	 */
	public static void cleanDir(final File dir) {
		deleteDir(dir, false);
	}

	/**
	 * Clean a specified directory.
	 * 
	 * @param dir
	 *            the directory to clean.
	 * @param filter
	 *            the filter to determine which file or directory to delete.
	 */
	public static void cleanDir(final File dir, final FilenameFilter filter) {
		deleteDir(dir, false, filter);
	}

	/**
	 * Clean a specified directory.
	 * 
	 * @param dir
	 *            the directory to clean.
	 * @param filter
	 *            the filter to determine which file or directory to delete.
	 */
	public static void cleanDir(final File dir, final FileFilter filter) {
		deleteDir(dir, false, filter);
	}

	/**
	 * Delete a specified directory.
	 * 
	 * @param dir
	 *            the directory to clean.
	 */
	public static void deleteDir(final File dir) {
		deleteDir(dir, true);
	}

	/**
	 * Delete a specified directory.
	 * 
	 * @param dir
	 *            the directory to clean.
	 * @param filter
	 *            the filter to determine which file or directory to delete.
	 */
	public static void deleteDir(final File dir, final FileFilter filter) {
		deleteDir(dir, true, filter);
	}

	/**
	 * Delete a specified directory.
	 * 
	 * @param dir
	 *            the directory to clean.
	 * @param filter
	 *            the filter to determine which file or directory to delete.
	 */
	public static void deleteDir(final File dir, final FilenameFilter filter) {
		deleteDir(dir, true, filter);
	}

	/**
	 * Delete a specified directory.
	 * 
	 * @param dir
	 *            the directory to clean.
	 * @param removeDir
	 *            true to remove the {@code dir}.
	 */
	public static void deleteDir(final File dir, final boolean removeDir) {
		if (dir != null && dir.isDirectory()) {
			final File[] files = dir.listFiles();
			for (final File file : files) {
				if (file.isDirectory()) {
					deleteDir(file, removeDir);
				} else {
					file.delete();
				}
			}
			if (removeDir) {
				dir.delete();
			}
		}
	}

	/**
	 * Delete a specified directory.
	 * 
	 * @param dir
	 *            the directory to clean.
	 * @param removeDir
	 *            true to remove the {@code dir}.
	 * @param filter
	 *            the filter to determine which file or directory to delete.
	 */
	public static void deleteDir(final File dir, final boolean removeDir,
			final FileFilter filter) {
		if (dir != null && dir.isDirectory()) {
			final File[] files = dir.listFiles(filter);
			if (files != null) {
				for (final File file : files) {
					if (file.isDirectory()) {
						deleteDir(file, removeDir, filter);
					} else {
						file.delete();
					}
				}
			}
			if (removeDir) {
				dir.delete();
			}
		}
	}

	/**
	 * Delete a specified directory.
	 * 
	 * @param dir
	 *            the directory to clean.
	 * @param removeDir
	 *            true to remove the {@code dir}.
	 * @param filter
	 *            the filter to determine which file or directory to delete.
	 */
	public static void deleteDir(final File dir, final boolean removeDir,
			final FilenameFilter filter) {
		if (dir != null && dir.isDirectory()) {
			final File[] files = dir.listFiles(filter);
			if (files != null) {
				for (final File file : files) {
					if (file.isDirectory()) {
						deleteDir(file, removeDir, filter);
					} else {
						file.delete();
					}
				}
			}
			if (removeDir) {
				dir.delete();
			}
		}
	}

	/**
	 * 将目录中的文件全部以格式化字符串重命名
	 * 
	 * @param dir
	 * @param format
	 * @return
	 */
	public static boolean renameFilesInDir(final File dir, String format) {
		File[] files = dir.listFiles();
		boolean success = true;
		for (File file : files) {
			if (!renameFileWithFormat(file, format)) {
				success = false;
				break;
			}// end if
		}
		return success;
	}

	/**
	 * 将文件以格式化字符串重命名
	 * 
	 * @param file
	 * @param format
	 * @return
	 */
	public static boolean renameFileWithFormat(final File file, String format) {
		String newName = String.format(format, file.getName());
		return rename(file, newName);
	}

	/**
	 * 重命名文件
	 * 
	 * @param file
	 * @param newName
	 * @return
	 */
	public static boolean rename(final File file, String newName) {
		File dir = file.getParentFile();
		return rename(file, new File(dir, newName));
	}

	/**
	 * 重命名文件
	 * 
	 * @param srcFile
	 * @param destFile
	 * @return
	 */
	public static boolean rename(final File srcFile, File destFile) {
		return srcFile.renameTo(destFile);
	}

	/**
	 * Write the specified data to an specified file.
	 * 
	 * @param file
	 *            The file to write into.
	 * @param data
	 *            The data to write. May be null.
	 */
	public static final void writeDataToFile(final File file, byte[] data) {
		if (null == file) {
			throw new IllegalArgumentException("file may not be null.");
		}
		if (null == data) {
			data = new byte[0];
		}
		final File dir = file.getParentFile();
		if (dir != null && !dir.exists()) {
			dir.mkdirs();
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			fos.write(data);
		} catch (final FileNotFoundException e) {
			System.out.printf("Can not open file %s for writing.", file
					.toString());
			e.printStackTrace();
		} catch (final IOException e) {
			System.out.printf("Failed when writing data to file %s.", file
					.toString());
			e.printStackTrace();
		} finally {
			quietClose(fos);
		}
	}

	/**
	 * Write the specified data to an specified file.
	 * 
	 * @param file
	 *            The file to write into.
	 * @param stream
	 *            The stream to write.
	 * @return true if success, otherwise return false.
	 */
	public static final boolean writeDataToFile(final File file,
			final InputStream stream) {
		boolean success = true;

		if (null == file) {
			throw new IllegalArgumentException("file may not be null.");
		}
		if (null == stream) {
			throw new IllegalArgumentException("stream may not be null.");
		}
		final File dir = file.getParentFile();
		if (dir != null && !dir.exists()) {
			dir.mkdirs();
		}
		FileOutputStream fos = null;
		final byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		try {
			fos = new FileOutputStream(file);
			int bytesRead = stream.read(buffer);
			while (bytesRead > 0) {
				fos.write(buffer, 0, bytesRead);
				bytesRead = stream.read(buffer);
			}
		} catch (final FileNotFoundException e) {
			System.out.printf("Can not open file %s for writing.", file
					.toString());
			e.printStackTrace();
			success = false;
		} catch (final IOException e) {
			System.out.printf("Failed when writing data to file %s.", file
					.toString());
			e.printStackTrace();
			success = false;
		} finally {
			quietClose(fos);
		}
		return success;
	}

	/**
	 * Write the specified string to an specified file.
	 * 
	 * @param file
	 *            The file to write into.
	 * @param content
	 *            The string to write.
	 * @return true if success, otherwise return false.
	 */
	public static boolean writeStringToFile(final File file,
			final String content) {
		boolean success = true;
		if (null == file) {
			throw new IllegalArgumentException("file may not be null.");
		}// end if

		if (null == content) {
			throw new IllegalArgumentException("content may not be null.");
		}// end if

		final File dir = file.getParentFile();
		if (dir != null && !dir.exists()) {
			dir.mkdirs();
		}

		FileWriter writer;
		try {
			writer = new FileWriter(file);
			writer.write(content);
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
			success = false;
		}

		return success;
	}

	/**
	 * Append string to file. If file not exist , will try to create a new one.
	 * 
	 * @param file
	 * @param content
	 * @return
	 */
	public static boolean appendStringToFile(File file, String content) {
		boolean success = true;
		try {
			RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
			randomAccessFile.seek(randomAccessFile.length());
			randomAccessFile.write(content.getBytes());
			// randomAccessFile.writeChars(content);
			randomAccessFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			success = false;
		} catch (IOException e) {
			e.printStackTrace();
			success = false;
		}

		/*
		 * FileWriter writer = null; try { writer = new FileWriter(file);
		 * writer.append(content); writer.flush(); writer.close(); success =
		 * true; } catch (IOException e) { e.printStackTrace(); success = false;
		 * } finally { writer = null; }
		 */
		return success;
	}

	private static final int DEFAULT_BUFFER_SIZE = 8192;

	/**
	 * Write the specified data to an specified file.
	 * 
	 * @param file
	 *            The file to write into.
	 * @param data
	 *            The data to write. May be null.
	 */
	public static final void copyFile(final File srcFile, final File destFile) {
		if (null == srcFile) {
			throw new IllegalArgumentException("srcFile may not be null.");
		}
		if (null == destFile) {
			throw new IllegalArgumentException("destFile may not be null.");
		}
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(srcFile);
			fos = new FileOutputStream(destFile);
			final byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int bytesRead = fis.read(buffer);
			while (bytesRead > 0) {
				fos.write(buffer);
				bytesRead = fis.read(buffer);
			}
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			quietClose(fis);
			quietClose(fos);
		}
	}

	/**
	 * Copy the specified path file's data to another specified path file's
	 * data.
	 * 
	 * @param absoluteSrcFilePath
	 * @param absoluteDestFilePath
	 */
	public static final void copyFile(final String absoluteSrcFilePath,
			final String absoluteDestFilePath) {
		File srcFile = new File(absoluteSrcFilePath);
		File destFile = new File(absoluteDestFilePath);
		copyFile(srcFile, destFile);
	}

	/**
	 * Noice: the param InputStream of srcIs will not be closed in this method
	 * after finish copy.
	 * <p>
	 * So the inovker has responsibility to close InputStream of srcIs.
	 * 
	 * @param srcIs
	 * @param destFile
	 */
	public static final boolean copyFile(final InputStream srcIs,
			final File destFile) {
		if (null == srcIs) {
			return false;
		}
		if (!destFile.exists()) {
			try {
				destFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(destFile);
			final byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int bytesRead = srcIs.read(buffer);
			while (bytesRead > 0) {
				fos.write(buffer);
				bytesRead = srcIs.read(buffer);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Close an {@linkplain Closeable} quietly.
	 * 
	 * @param closeable
	 *            the {@linkplain Closeable} to close.
	 */
	public static final void quietClose(final Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (final IOException e) {
				// Ignore errors.
			}
		}
	}

	/**
	 * 
	 * @param keyword
	 * @param extendName
	 * @return
	 */
	private static final String PREFIX_SELECTOR_BTN_XML = "selector_btn_";

	/**
	 * 
	 * @return selector_btn_XXX.xml (Notice: has contain ".xml").
	 */
	public static String buildSelectorXMLFileName(String keyword) {
		return PREFIX_SELECTOR_BTN_XML + keyword + ".xml";
	}

	/**
	 * 
	 * @param targetDirPath
	 * @param keyword
	 * @return "E:\workspace\UXTool\drawable\selector_btn_XXX.xml".
	 */
	public static String buildSelectorXMLFilePath(String targetDirPath,
			String keyword) {
		return targetDirPath + File.separator + ANDROID_RES_DRAWABLE
				+ File.separator + buildSelectorXMLFileName(keyword);
	}

	/**
	 * 
	 * @param targetDirPath
	 * @param keyword
	 * @return File("E:\workspace\UXTool\drawable\selector_btn_XXX.xml").
	 */
	public static File buildSelectorXMLFile(String targetDirPath, String keyword) {
		String xmlFilePath = buildSelectorXMLFilePath(targetDirPath, keyword);
		File file = new File(xmlFilePath);
		return file;
	}

	/**
	 * <p>
	 * btn_%keyword%_%stateType%.%extendName%
	 * </p>
	 * <p>
	 * eg. "btn_tts_pressed.png".
	 * </p>
	 * 
	 * @param keyword
	 * @param stateType
	 * @param extendName
	 *            ".9.png" ".png" ".jpg"
	 * @return
	 */
	private static String buildPicName(String keyword, String stateType,
			String extendName) {
		// TODO param check
		return PREFIX_BTN + LINK_CHAR + keyword + LINK_CHAR + stateType
				+ extendName;
	}

	/**
	 * <p>
	 * btn_%keyword%_default%extendName%
	 * </p>
	 * <p>
	 * eg. "btn_tts_default.png".
	 * </p>
	 * 
	 * @param keyword
	 * @param extendName
	 *            ".9.png" ".png" ".jpg"
	 * @return
	 */
	public static String buildPicDefaultName(String keyword, String extendName) {
		return buildPicName(keyword, SUFFIX_BTN_DEFAULT, extendName);
	}

	/**
	 * <p>
	 * btn_%keyword%_pressed%extendName%
	 * </p>
	 * <p>
	 * eg. "btn_tts_pressed.png".
	 * </p>
	 * 
	 * @param keyword
	 * @param extendName
	 *            ".9.png" ".png" ".jpg"
	 * @return
	 */
	public static String buildPicPressedName(String keyword, String extendName) {
		return buildPicName(keyword, SUFFIX_BTN_PRESSED, extendName);
	}

	/**
	 * <p>
	 * btn_%keyword%_disabled%extendName%
	 * </p>
	 * <p>
	 * eg. "btn_tts_disabled.png".
	 * </p>
	 * 
	 * @param keyword
	 * @param extendName
	 *            ".9.png" ".png" ".jpg"
	 * @return
	 */
	public static String buildPicDisabledName(String keyword, String extendName) {
		return buildPicName(keyword, SUFFIX_BTN_DISABLED, extendName);
	}

	public static String buildSelectorXMLBeginToString() {
		return SELECTOR_HEAD_LINE_1 + '\n' + SELECTOR_HEAD_LINE_2;
	}

	public static byte[] buildSelectorXMLBeginToByte() {
		return buildSelectorXMLBeginToString().getBytes();
	}

	public static String buildSelectorXMLEndToString() {
		return SELECTOR_TAIL_LINE;
	}

	public static byte[] buildSelectorXMLEndToByte() {
		return buildSelectorXMLEndToString().getBytes();
	}

	/**
	 * 
	 * @param keyword
	 * @param extendName
	 * @return
	 */
	public static String buildBtnPressedStateToString(String keyword) {
		return SELECTOR_STATE_PRESSED_BEGIN + PREFIX_BTN + LINK_CHAR + keyword
				+ LINK_CHAR + SUFFIX_BTN_PRESSED + SELECTOR_STATE_PRESSED_END;
	}

	/**
	 * 
	 * @param keyword
	 * @param extendName
	 * @return
	 */
	public static byte[] buildBtnPressedStateToByte(String keyword) {
		String result = buildBtnPressedStateToString(keyword);
		return result.getBytes();
	}

	/**
	 * 
	 * @param keyword
	 * @param extendName
	 * @return
	 */
	public static String buildBtnDisabledStateToString(String keyword) {
		return SELECTOR_STATE_DISABLE_BEGIN + PREFIX_BTN + LINK_CHAR + keyword
				+ LINK_CHAR + SUFFIX_BTN_DISABLED + SELECTOR_STATE_DISABLE_END;
	}

	/**
	 * 
	 * @param keyword
	 * @param extendName
	 * @return
	 */
	public static byte[] buildBtnDisabledStateToByte(String keyword) {
		String result = buildBtnDisabledStateToString(keyword);
		return result.getBytes();
	}

	/**
	 * 
	 * @param keyword
	 * @param extendName
	 * @return
	 */
	public static String buildBtnDefaultStateToString(String keyword) {
		return SELECTOR_STATE_DEFAULT_BEGIN + PREFIX_BTN + LINK_CHAR + keyword
				+ LINK_CHAR + SUFFIX_BTN_DEFAULT + SELECTOR_STATE_DEFAULT_END;
	}

	/**
	 * 
	 * @param keyword
	 * @param extendName
	 * @return
	 */
	public static byte[] buildBtnDefaultStateToByte(String keyword) {
		String result = buildBtnDefaultStateToString(keyword);
		return result.getBytes();
	}

	public static boolean isDefaultBtnPicExist(String keyword, String extendName) {
		String filePath = buildPicDefaultName(keyword, extendName);
		return isFileExist(filePath, false);
	}

	public static boolean isDisabledBtnPicExist(String keyword,
			String extendName) {
		String filePath = buildPicDisabledName(keyword, extendName);
		return isFileExist(filePath, false);
	}

	public static boolean isFileExist(File file, boolean createItIfNoExist) {
		boolean result = true;
		result = file.exists();
		if (!result) {
			if (debug) {
				System.out.println("File NOT exist:\n\t" + file.getAbsolutePath());
			}// end if
			if (createItIfNoExist) {
				// TODO
			}// end if
		} else {
			if (debug) {
				System.out.println("File HAS exist:\n\t" + file.getAbsolutePath());
			}// end if
		}
		return result;
	}

	public static boolean isFileExist(String filePath, boolean createItIfNoExist) {
		boolean result = true;
		File file = new File(filePath);
		result = file.exists();
		if (!result && createItIfNoExist) {
			// TODO
		}// end if
		return result;
	}

	public static boolean ensureDir(String path) {
		boolean result = true;
		File file = new File(path);
		if (!file.exists()) {
			result = file.mkdirs();
		}// end if
		return result;
	}

	public static boolean isKeywordSafe(String keyword) {
		boolean result = true;
		char[] src = keyword.toCharArray();
		for (int i = 0; i < src.length; i++) {
			char temp = src[i];
			if (isCharSafe(temp)) {
				result = true;
				continue;
			} else {
				result = false;
				break;
			}
		}// end for
		return result;
	}

	public static boolean isCharSafe(char ch) {
		boolean result = true;
		// TODO
		if (ch >= 'a' && ch <= 'z') {
			result = true;
		} else if ('_' == ch) {
			result = true;
		} else if (ch >= '0' & ch <= '9') {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * Another way to open dir by code.
	 * <p>
	 * android平台未提供java.awt.Desktop包
	 * </p>
	 */
	@Deprecated
	public static void autoOpenDir(String dirPath) {
		/*
		 * try { java.awt.Desktop.getDesktop().open(new File(dirPath)); } catch
		 * (IOException e) { e.printStackTrace(); }
		 */
	}

	/**
	 * 
	 * @param keyword
	 * @param extendName
	 * @return
	 */
	public static void main(String args[]) {
		System.out.println("current path = " + getDefaultDirPath());
		System.out.println("------------------------------");
		System.out.println(SELECTOR_HEAD_LINE_1);
		System.out.println(SELECTOR_HEAD_LINE_2);
		System.out.println(SELECTOR_STATE_PRESSED_BEGIN + "btn_common_pressed"
				+ SELECTOR_STATE_PRESSED_END);
		System.out.println(SELECTOR_STATE_DISABLE_BEGIN + "btn_common_disabled"
				+ SELECTOR_STATE_DISABLE_END);
		System.out.println(SELECTOR_STATE_DEFAULT_BEGIN + "btn_common_default"
				+ SELECTOR_STATE_DEFAULT_END);
		System.out.println(SELECTOR_TAIL_LINE);

		appendStringToFile(new File("a.txt"), "show");
		appendStringToFile(new File("a.txt"), " me the money");
	}

}
