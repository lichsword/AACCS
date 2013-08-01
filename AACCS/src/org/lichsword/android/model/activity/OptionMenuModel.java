/***********************************************************************
 * 
 *     Copyright: 2011, BAINA Technologies Co. Ltd.
 *     Classname: OptionMenuModel.java
 *     Author:    yuewang
 *     Description:    TODO
 *     History:
 *         1.  Date:   下午10:17:16
 *             Author:    yuewang
 *             Modifycation:    create the class.       
 *
 ***********************************************************************/

package org.lichsword.android.model.activity;

import java.util.ArrayList;

/**
 * @author yuewang
 * 
 */
public class OptionMenuModel {

	public ArrayList<OptionMenuItem> optionMenuList = null;

	public class OptionMenuItem {

		public OptionMenuItem() {
			super();
			keyword = null;
			hasMenuIcon = true;
			iconImagePath = null;
		}

		private String keyword = null;

		private boolean hasMenuIcon = true;

		private String iconImagePath = null;

		public void setKeyword(String keyword) {
			this.keyword = keyword;
		}

		public String getKeyword() {
			return keyword;
		}

		public void setHasMenuIcon(boolean hasMenuIcon) {
			this.hasMenuIcon = hasMenuIcon;
		}

		public boolean isHasMenuIcon() {
			return hasMenuIcon;
		}

		public void setIconImagePath(String iconImagePath) {
			this.iconImagePath = iconImagePath;
		}

		public String getIconImagePath() {
			return iconImagePath;
		}

	}

	public boolean hasOptionMenuItem() {
		boolean result = false;
		if (null != optionMenuList && optionMenuList.size() > 0) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	public void setOptionMenuList(ArrayList<OptionMenuItem> optionMenuList) {
		this.optionMenuList = optionMenuList;
	}

	public ArrayList<OptionMenuItem> getOptionMenuList() {
		return optionMenuList;
	}
}
