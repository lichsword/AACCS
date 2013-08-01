/***********************************************************************
 * 
 *     Copyright: 2011, BAINA Technologies Co. Ltd.
 *     Classname: AppVersionManager.java
 *     Author:    yuewang
 *     Description:    TODO
 *     History:
 *         1.  Date:   下午09:54:06
 *             Author:    yuewang
 *             Modifycation:    create the class.       
 *
 ***********************************************************************/

package org.lichsword.android.manager;

/**
 * @author yuewang
 * 
 */
public class AppVersionManager {

	private static AppVersionManager sInstance;

	private AppVersionManager() {
		super();
	}

	/**
	 * Key must not contain illegal char. Like ' '(space char).
	 * 
	 * @return
	 */
	public static AppVersionManager getInstance() {
		if (null == sInstance) {
			sInstance = new AppVersionManager();
		}// end if
		return sInstance;
	}

	private final String FRAME_TITLE = "Android Auto Code Complete System";
	private final String VERSION_NAME = "1.0.1";
	private final int VERSION_CODE = 14;

	public String getAppName() {
		return FRAME_TITLE + " v" + getVersionName();
	}

	public String getVersionName() {
		return VERSION_NAME;
	}

	public int getVersionCode() {
		return VERSION_CODE;
	}
}
