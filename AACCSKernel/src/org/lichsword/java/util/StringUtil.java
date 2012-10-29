/******************************************************************************
 *
 * Copyright 2012 Lichsword Studio, All right reserved.
 *
 * File name   : StringUtil.java
 * Create time : 2012-10-11
 * Author      : lichsword
 * Description : TODO
 *
 *****************************************************************************/
package org.lichsword.java.util;

import java.io.IOException;
import java.io.Reader;

public class StringUtil {

	/**
	 * Replace "\t", "\r", " ", "\x0D","\x0A" to none, and return back.
	 * 
	 * @param string
	 * @return
	 */
	public static String filterEmptyChars(String string) {
		String result = string;
		String result1 = result.replaceAll(" ", "");
		String result2 = result1.replaceAll("\t", "");
		String result3 = result2.replaceAll("\n", "");
		return result3;
	}

	private static final int DEFAULT_BUFFER_SIZE = 1024;

	@Deprecated
	public static String readAsString(Reader reader) {
		return readAsString(reader, new char[DEFAULT_BUFFER_SIZE]);
	}

	@Deprecated
	public static String readAsString(Reader reader, final char[] buffer) {
		if (null != reader) {
			if (null == buffer) {
				throw new NullPointerException("param char[] buffer is null");
			}// end if
			int read = 0;
			StringBuilder sbBuilder = new StringBuilder();
			try {
				while ((read = reader.read(buffer)) > 0) {
					sbBuilder.append(buffer, 0, read);
				}// end while
				return sbBuilder.toString();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		} else {
			throw new NullPointerException("param reader is null");
		}
	}
}
