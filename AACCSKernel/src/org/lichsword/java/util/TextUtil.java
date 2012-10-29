/******************************************************************************
 *
 * Copyright 2012 Lichsword Studio, All right reserved.
 *
 * File name   : TextUtil.java
 * Create time : 2012-10-11
 * Author      : lichsword
 * Description : TODO
 *
 *****************************************************************************/
package org.lichsword.java.util;

public class TextUtil {
	/**
	 * Returns true if the string is null or 0-length.
	 * 
	 * @param str
	 *            the string to be examined
	 * @return true if str is null or zero length
	 */
	public static boolean isEmpty(CharSequence str) {
		if (str == null || str.length() == 0)
			return true;
		else
			return false;
	}
}
