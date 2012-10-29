/******************************************************************************
 *
 * Copyright 2012 Lichsword Studio, All right reserved.
 *
 * File name   : RegularUtil.java
 * Create time : 2012-10-11
 * Author      : lichsword
 * Description : TODO
 *
 *****************************************************************************/
package org.lichsword.java.util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularUtil {

	/**
	 * Get string which matched pattern. return null if not matched.
	 * 
	 * @param pattern
	 * @param string
	 * @return
	 */
	public static String getMatchedObject(String pattern, String string) {
		return getMatchedObject(pattern, string, 0);
	}

	/**
	 * 
	 * @param pattern
	 * @param string
	 * @param groupIndex
	 * @return
	 */
	public static String getMatchedObject(String pattern, String string,
			int groupIndex) {
		Pattern myPattern = Pattern.compile(pattern);
		Matcher matcher = myPattern.matcher(string);
		if (matcher.find()) {
			return matcher.group(groupIndex);
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param pattern
	 * @param string
	 * @return
	 */
	public static ArrayList<String> getMatchedArray(String pattern,
			String string) {
		ArrayList<String> result = null;
		Pattern myPattern = Pattern.compile(pattern);
		Matcher matcher = myPattern.matcher(string);
		while (matcher.find()) {
			if (null == result) {
				result = new ArrayList<String>();
			}// end if
			result.add(matcher.group());
		}

		if (null != result && result.size() > 0) {
			return result;
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param pattern
	 * @param string
	 * @param groupIndex
	 * @return
	 */
	public static ArrayList<String> getMatchedArray(String pattern,
			String string, int groupIndex) {
		ArrayList<String> result = null;
		Pattern myPattern = Pattern.compile(pattern);
		Matcher matcher = myPattern.matcher(string);
		while (matcher.find()) {
			if (null == result) {
				result = new ArrayList<String>();
			}// end if
			result.add(matcher.group(groupIndex));
		}

		if (null != result && result.size() > 0) {
			return result;
		} else {
			return null;
		}
	}
}
