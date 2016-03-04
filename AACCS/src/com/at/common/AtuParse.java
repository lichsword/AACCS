package com.at.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AtuParse {

	public static long safeParseLong(String data, long def) {
		long result = def;

		try {
			result = Long.parseLong(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static int safeParseInt(String data, int def) {
		int result = def;

		try {
			result = Integer.parseInt(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static boolean isName(String input) {
		return match(input, "[a-zA-Z0-9_]+");
	}

	public static boolean isEmail(String input) {
		return match(input, "^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+");
	}
	
	public static boolean isPassword(String input){
		return match(input, "[a-zA-Z0-9!@#$*]+");
	}

	private static boolean match(String input, String regular) {
		Pattern pattern = Pattern.compile(regular);
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}
}
