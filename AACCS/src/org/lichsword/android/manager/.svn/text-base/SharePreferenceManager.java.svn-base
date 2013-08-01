/***********************************************************************
 * 
 *     Copyright: 2011, BAINA Technologies Co. Ltd.
 *     Classname: SharePreferenceManager.java
 *     Author:    yuewang
 *     Description:    TODO
 *     History:
 *         1.  Date:   下午04:21:36
 *             Author:    yuewang
 *             Modifycation:    create the class.       
 *
 ***********************************************************************/

package org.lichsword.android.manager;

import java.io.File;

import org.lichsword.xml.dom.DOMParseManager;

/**
 * @author yuewang
 * 
 */
public class SharePreferenceManager {
	private static SharePreferenceManager sInstance;

	private DOMParseManager manager = null;

	private SharePreferenceManager() {
		super();
		manager = DOMParseManager.getInstance(new File(XML_FILE_NAME));
	}

	/**
	 * Key must not contain illegal char. Like ' '(space char).
	 * 
	 * @return
	 */
	public static SharePreferenceManager getInstance() {
		if (null == sInstance) {
			sInstance = new SharePreferenceManager();
		}// end if
		return sInstance;
	}

	private final String XML_FILE_NAME = "preference.xml";

	public String getString(String key, String defaultValue) {
		return manager.getString(key, defaultValue);

	}

	public boolean putString(String key, String value) {
		return manager.putString(key, value);
	}

	public int getInt(String key, int defaultValue) {
		return manager.getInt(key, defaultValue);
	}

	public boolean putInt(String key, int value) {
		return manager.putInt(key, value);
	}

	public long getLong(String key, long defaultValue) {
		return manager.getLong(key, defaultValue);
	}

	public boolean putLong(String key, long value) {
		return manager.putLong(key, value);
	}

	public boolean putBoolean(String key, boolean value) {
		return manager.putBoolean(key, value);
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		return manager.getBoolean(key, defaultValue);
	}

	public boolean putFloat(String key, float value) {
		return manager.putFloat(key, value);
	}

	public float getFloat(String key, float defaultValue) {
		return manager.getFloat(key, defaultValue);
	}

	public static void main(String args[]) {
		SharePreferenceManager manager = SharePreferenceManager.getInstance();
		manager.putBoolean("bSB", true);
		manager.putFloat("fSB", 3.1415926f);
		manager.putInt("iSB", 1234567890);
		manager.putLong("lSB", 123456789465446465l);
		manager.putString("You_SB", "Yes you are");
	}
}
