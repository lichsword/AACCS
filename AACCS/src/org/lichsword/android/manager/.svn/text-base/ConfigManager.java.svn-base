/***********************************************************************
 * 
 *     Copyright: 2011, BAINA Technologies Co. Ltd.
 *     Classname: ConfigManager.java
 *     Author:    yuewang
 *     Description:    TODO
 *     History:
 *         1.  Date:   下午09:11:57
 *             Author:    yuewang
 *             Modifycation:    create the class.       
 *
 ***********************************************************************/

package org.lichsword.android.manager;

/**
 * @author yuewang
 * 
 */
public class ConfigManager {
	private static ConfigManager sInstance;

	private SharePreferenceManager manager = null;

	private ConfigManager() {
		super();
		manager = SharePreferenceManager.getInstance();
	}

	/**
	 * Key must not contain illegal char. Like ' '(space char).
	 * 
	 * @return
	 */
	public static ConfigManager getInstance() {
		if (null == sInstance) {
			sInstance = new ConfigManager();
		}// end if
		return sInstance;
	}

	private final String KEY_WIDNOW_X = "keyWindowX";
	private final String KEY_WIDNOW_Y = "keyWindowY";
	private final String KEY_WIDNOW_WIDTH = "keyWindowWidth";
	private final String KEY_WIDNOW_HEIGHT = "keyWindowHeight";
	private final String KEY_WIDNOW_TAB_INDEX = "keyTabIndex";

	public boolean setWindowX(int x) {
		return manager.putInt(KEY_WIDNOW_X, x);
	}

	public int getWindowX(int defaultValue) {
		return manager.getInt(KEY_WIDNOW_X, defaultValue);
	}

	public boolean setWindowY(int y) {
		return manager.putInt(KEY_WIDNOW_Y, y);
	}

	public int getWindowY(int defaultValue) {
		return manager.getInt(KEY_WIDNOW_Y, defaultValue);
	}

	public static int DEFAULT_WINDOW_WIDTH = 1200;

	public boolean setWindowWidth(int Width) {
		return manager.putInt(KEY_WIDNOW_WIDTH, Width);
	}

	public int getWindowWidth(int defaultValue) {
		return manager.getInt(KEY_WIDNOW_WIDTH, defaultValue);
	}

	public static int DEFAULT_WINDOW_HEIGHT = 1000;

	public boolean setWindowHeight(int Height) {
		return manager.putInt(KEY_WIDNOW_HEIGHT, Height);
	}

	public int getWindowHeight(int defaultValue) {
		return manager.getInt(KEY_WIDNOW_HEIGHT, defaultValue);
	}

	public boolean setWindowTabIndex(int tabIndex) {
		return manager.putInt(KEY_WIDNOW_TAB_INDEX, tabIndex);
	}

	public int getWindowTabIndex(int defaultValue) {
		return manager.getInt(KEY_WIDNOW_TAB_INDEX, defaultValue);
	}
}
