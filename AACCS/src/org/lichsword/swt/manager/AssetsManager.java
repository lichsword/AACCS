/***********************************************************************
 * 
 *     Copyright: 2011, BAINA Technologies Co. Ltd.
 *     Classname: AssetsManager.java
 *     Author:    yuewang
 *     Description:    TODO
 *     History:
 *         1.  Date:   下午05:17:56
 *             Author:    yuewang
 *             Modifycation:    create the class.       
 *
 ***********************************************************************/

package org.lichsword.swt.manager;


/**
 * @author yuewang
 * 
 */
public class AssetsManager {
	private static AssetsManager sInstance;

	private AssetsManager() {
		super();
	}

	public static AssetsManager getInstance() {
		if (null == sInstance) {
			sInstance = new AssetsManager();
		}// end if
		return sInstance;
	}
}
