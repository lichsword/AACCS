/***********************************************************************
 * 
 *     Copyright: 2011, BAINA Technologies Co. Ltd.
 *     Classname: PhoneSnapshot.java
 *     Author:    yuewang
 *     Description:    TODO
 *     History:
 *         1.  Date:   下午05:20:10
 *             Author:    yuewang
 *             Modifycation:    create the class.       
 *
 ***********************************************************************/

package org.lichsword.java.tool.design.android.model.phone;

import java.awt.Image;

/**
 * @author yuewang
 * 
 */
public abstract class PhoneSnapshot {

	/**
	 * Get the skin image of phone.
	 * 
	 * @return
	 */
	public abstract Image getPhoneSkinImage();

	/**
	 * Get the screen width of phone.
	 * 
	 * @return
	 */
	public abstract int getPhoneScreenWidth();

	/**
	 * Get the screen height of phone.
	 * 
	 * @return
	 */
	public abstract int getPhoneScreenHeight();

	/**
	 * Is exist hard keyboard of phone.
	 * 
	 * @return true if exist, otherwise return false.
	 */
	public abstract boolean hasHardKeyboard();

}
