/***********************************************************************
 * 
 *     Copyright: 2011, BAINA Technologies Co. Ltd.
 *     Classname: PhoneStyleManager.java
 *     Author:    yuewang
 *     Description:    TODO
 *     History:
 *         1.  Date:   下午05:25:50
 *             Author:    yuewang
 *             Modifycation:    create the class.       
 *
 ***********************************************************************/

package org.lichsword.java.tool.design.android.manager;

import org.lichsword.java.tool.design.android.model.phone.PhoneSnapshot;
import org.lichsword.java.tool.design.android.model.phone.milestone.PhoneSnapshotMilestone;

/**
 * @author yuewang
 * 
 */
public class PhoneStyleManager {
	private static PhoneStyleManager sInstance;

	private PhoneStyleManager() {
		super();
	}

	public static PhoneStyleManager getInstance() {
		if (null == sInstance) {
			sInstance = new PhoneStyleManager();
		}// end if
		return sInstance;
	}

	/**
	 * default value is milestone.
	 */
	private PhoneSnapshot phoneSnapshot = new PhoneSnapshotMilestone();

	/**
	 * 
	 * @param phoneSnapshot
	 */
	public void setPhoneSnapshot(PhoneSnapshot phoneSnapshot) {
		this.phoneSnapshot = phoneSnapshot;
	}

	/**
	 * Default value is milestone.
	 * 
	 * @return
	 */
	public PhoneSnapshot getPhoneSnapshot() {
		return phoneSnapshot;
	}
	
}
