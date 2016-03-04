package com.at.common;

/**
 * 日志管理工具类
 * <p/>
 * 从低到高: VERBOSE --- DEBUG --- INFO --- WARN --- ERROR
 * <p/>
 * Created by lichsword on 15/6/10.
 */
public class AtuLog {

	/**
	 * 显示冗余或更高级别日志。
	 */
	public static final int VERBOSE = 1;
	/**
	 * 显示调试或更高级别日志。
	 */
	public static final int DEBUG = 2;
	/**
	 * 显示信息或更高级别日志。
	 */
	public static final int INFO = 3;
	/**
	 * 显示错误或更高级别日志。
	 */
	public static final int WARN = 4;
	/**
	 * 显示错误或更高级别日志。
	 */
	public static final int ERROR = 5;
	/**
	 * 任何日志都不输出，相当于关闭日志。
	 */
	public static final int NOTHING = 6;

	/**
	 *
	 */
	public static int LEVEL = VERBOSE;

	public static void v(String tag, String msg) {
		if (LEVEL <= VERBOSE && !AtuText.isEmpty(msg)) {
			System.out.println(String.format("[%s]%s:%s", "VERBOSE", tag, msg));
		}
	}

	public static void d(String tag, String msg) {
		if (LEVEL <= DEBUG && !AtuText.isEmpty(msg)) {
			System.out.println(String.format("[%s]%s:%s", "DEBUG", tag, msg));
		}
	}

	public static void i(String tag, String msg) {
		if (LEVEL <= INFO && !AtuText.isEmpty(msg)) {
			System.out.println(String.format("[%s]%s:%s", "INFO", tag, msg));
		}
	}

	public static void w(String tag, String msg) {
		if (LEVEL <= WARN && !AtuText.isEmpty(msg)) {
			System.out.println(String.format("[%s]%s:%s", "WARNING", tag, msg));
		}
	}

	public static void e(String tag, String msg) {
		if (LEVEL <= ERROR && !AtuText.isEmpty(msg)) {
			System.out.println(String.format("[%s]%s:%s", "ERROR", tag, msg));
		}
	}

}
