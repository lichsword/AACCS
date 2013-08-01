/***********************************************************************
 * 
 *     Copyright: 2011, BAINA Technologies Co. Ltd.
 *     Classname: OptionMenuManager.java
 *     Author:    yuewang
 *     Description:    TODO
 *     History:
 *         1.  Date:   下午11:33:10
 *             Author:    yuewang
 *             Modifycation:    create the class.       
 *
 ***********************************************************************/

package org.lichsword.android.manager;

import java.util.HashMap;
import java.util.logging.Logger;

import org.lichsword.codestore.android.menu.OptionsMenuCodestore;

/**
 * @author yuewang
 * 
 */
public class OptionMenuManager {

	@SuppressWarnings("unused")
	private final String TAG = OptionMenuManager.class.getSimpleName();

	private static OptionMenuManager sInstance;

	private OptionsMenuCodestore mOptionsMenuCodestore = null;

	private OptionMenuManager() {
		super();
		mOptionsMenuCodestore = new OptionsMenuCodestore();
	}

	public static OptionMenuManager getInstance() {
		if (null == sInstance) {
			sInstance = new OptionMenuManager();
		}// end if
		return sInstance;
	}

	public void setCurrentMenuXMLFileKeyword(String menuXMLFileKeyword) {
		this.mCurrentMenuXMLFileKeyword = menuXMLFileKeyword;
	}

	public String getCurrentMenuXMLFileKeyword() {
		return mCurrentMenuXMLFileKeyword;
	}

	public String getCurrentMenuXMLFileName() {
		if (null == mCurrentMenuXMLFileKeyword) {
			System.out
					.println("mCurrentMenuXMLFileKeyword should not be null...ERROR!!!");
		}// end if
		return mOptionsMenuCodestore
				.buildMenuXMLFileName(mCurrentMenuXMLFileKeyword);
	}

	private String mCurrentMenuXMLFileKeyword = null;

	/**
	 * <p>
	 * key : keyword of menu file keyword
	 * </p>
	 * <p>
	 * value : hashmap of MenuItemsMap which match menu file(keyword)
	 * </p>
	 */
	private HashMap<String, HashMap<String, String>> mAllOptionMenuXMlFileMap = new HashMap<String, HashMap<String, String>>();

	/**
	 * <p>
	 * key : keyword of menu item
	 * </p>
	 * <p>
	 * value : the menu item code (menu id/ menu icon/ menu title)
	 * </p>
	 */
	private HashMap<String, String> mCurrentMenuItemsMap = new HashMap<String, String>();

	// 这里有一个技术难点，就是，可以由多个menu item 组成一个menu_XX.xml文件，单独完成一个menu item的xml
	// 字符串输出，这是很容易的，但是，要动态增、删、查、改 menu_XX.xml文件中的众多menu item，则需要一些额外的方法进行处理。今天就到这里吧，明天早点来，接着写。

}
