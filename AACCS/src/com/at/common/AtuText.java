package com.at.common;

/**
 * @author lichs_000
 * 
 */
public class AtuText {

	public static boolean isEmpty(String string) {
		return (null == string || string.equals(""));
	}

	public static String htmlEncode(String str) {
		return str.replace("\"", "&quot;").replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
	}

	public static String htmlDecode(String str) {
		return str.replace("&quot;", "\"").replace("&amp;", "&").replace("&lt;", "<").replace("&gt;", ">");
	}
}
