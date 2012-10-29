package geek.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.text.TextUtils;

/**
 * Log replacement for {@link android.util.Log}.
 * 
 * @author lichsword
 * 
 */
public final class Log {

	private static final String MESSAGE_TEMPLATE = "[%s]%s";
	private static final Object sSyncObject = new Object();
	private static final Pattern sNewLinePattern = Pattern
			.compile("\r|\r\n|\n");

	/**
	 * <p>
	 * Priority constant for enable all loggings.
	 * </p>
	 */
	public static final int ALL = -1;

	/**
	 * <p>
	 * Priority constant for {@link #println(int, String, String)} or
	 * {@link #setFilterLevel(int)} methods; use Log.v.
	 * </p>
	 */
	public static final int VERBOSE = 2;

	/**
	 * <p>
	 * Priority constant for the {@link #println(int, String, String)} or
	 * {@link #setFilterLevel(int)} method;
	 * </p>
	 * <p>
	 * use Log.d.
	 * </p>
	 */
	public static final int DEBUG = 3;

	/**
	 * <p>
	 * Priority constant for the {@link #println(int, String, String)} or
	 * {@link #setFilterLevel(int)} method;
	 * </p>
	 * <p>
	 * use Log.i.
	 * </p>
	 */
	public static final int INFO = 4;

	/**
	 * <p>
	 * Priority constant for the {@link #println(int, String, String)} or
	 * {@link #setFilterLevel(int)} method;
	 * </p>
	 * <p>
	 * use Log.w.
	 * </p>
	 */
	public static final int WARN = 5;

	/**
	 * <p>
	 * Priority constant for the {@link #println(int, String, String)} or
	 * {@link #setFilterLevel(int)} method;
	 * </p>
	 * <p>
	 * use Log.e.
	 * </p>
	 */
	public static final int ERROR = 6;

	/**
	 * <p>
	 * Priority constant for the {@link #println(int, String, String)} or
	 * {@link #setFilterLevel(int)} method.
	 * </p>
	 * <p>
	 * Priority to disable all loggings.
	 * </p>
	 */
	public static final int ASSERT = 7;

	/**
	 * <p>
	 * Priority constant for disable all loggings.
	 * </p>
	 */
	public static final int NONE = Integer.MAX_VALUE;

	/**
	 * <p>
	 * Filter level of logs. Only levels greater or equals this level will be
	 * output to LogCat.
	 * </p>
	 */
	private static int sFilterLevel = ALL;

	private static String sTag;

	/**
	 * Set the default tag for this application.
	 * 
	 * @param tag
	 *            he tag of the application.
	 */
	public static final void setApplicationTag(String tag) {
		sTag = tag;
	}

	/**
	 * Gets the default tag of the application.
	 * 
	 * @return The default tag of the application.
	 */
	public static final String getApplicationTag() {
		return sTag;
	}

	/**
	 * Retrieve a default tag for an class.
	 * 
	 * @param cls
	 *            The default tag for the specified class.
	 * @return The simple name of the class.
	 */
	public static final String getDefaultTag(Class<?> cls) {
		if (null == cls) {
			return "";
		}
		return cls.getSimpleName();
	}

	/**
	 * Sets the filter level of logs. Only levels greater or equals this level
	 * will be output to LogCat.
	 * 
	 * @param level
	 *            The filter level.
	 */
	public static final void setFilterLevel(int level) {
		synchronized (sSyncObject) {
			sFilterLevel = level;
		}
	}

	/**
	 * Gets the filter level of logs. Only levels greater or equals this level
	 * will be output to LogCat.
	 * 
	 * @return Current filter level.
	 */
	public static final int getFilterLevel() {
		return sFilterLevel;
	}

	/**
	 * Checks to see whether or not a log for the specified tag is loggable at
	 * the specified level.
	 * 
	 * The default level of any tag is set to INFO. This means that any level
	 * above and including INFO will be logged. Before you make any calls to a
	 * logging method you should check to see if your tag should be logged. You
	 * can change the default level by setting a system property: 'setprop
	 * log.tag.&lt;YOUR_LOG_TAG> &lt;LEVEL>' Where level is either VERBOSE,
	 * DEBUG, INFO, WARN, ERROR, ASSERT, or SUPPRESS. SUPRESS will turn off all
	 * logging for your tag. You can also create a local.prop file that with the
	 * following in it: 'log.tag.&lt;YOUR_LOG_TAG>=&lt;LEVEL>' and place that in
	 * /data/local.prop.
	 * 
	 * @param tag
	 *            The tag to check.
	 * @param level
	 *            The level to check.
	 * @return Whether or not that this is allowed to be logged.
	 * @throws IllegalArgumentException
	 *             is thrown if the tag.length() > 23.
	 */
	public static boolean isLoggable(String tag, int level) {
		return android.util.Log.isLoggable(tag, level);
	}

	/**
	 * Send a {@link #VERBOSE} log message.
	 * 
	 * @param tag
	 *            Used to identify the source of a log message. It usually
	 *            identifies the class or activity where the log call occurs.
	 * @param msg
	 *            The message you would like logged.
	 */
	public static int v(String tag, String msg) {
		return println(VERBOSE, tag, msg);
	}

	/**
	 * Send a {@link #VERBOSE} log message.
	 * 
	 * @param tag
	 *            Used to identify the source of a log message. It usually
	 *            identifies the class or activity where the log call occurs.
	 * @param format
	 *            The format of the message you would like logged.
	 * @param args
	 *            The arguments used to format the message.
	 */
	public static int v(String tag, String format, Object... args) {
		final String msg = String.format(format, args);
		return println(VERBOSE, tag, msg);
	}

	/**
	 * Send a {@link #VERBOSE} log message and log the exception.
	 * 
	 * @param tag
	 *            Used to identify the source of a log message. It usually
	 *            identifies the class or activity where the log call occurs.
	 * @param msg
	 *            The message you would like logged.
	 * @param tr
	 *            An exception to log
	 */
	public static int v(String tag, String msg, Throwable tr) {
		return println(VERBOSE, tag, msg + '\n' + getStackTraceString(tr));
	}

	/**
	 * Send a {@link #DEBUG} log message.
	 * 
	 * @param tag
	 *            Used to identify the source of a log message. It usually
	 *            identifies the class or activity where the log call occurs.
	 * @param msg
	 *            The message you would like logged.
	 */
	public static int d(String tag, String msg) {
		return println(DEBUG, tag, msg);
	}

	/**
	 * Send a {@link #DEBUG} log message.
	 * 
	 * @param tag
	 *            Used to identify the source of a log message. It usually
	 *            identifies the class or activity where the log call occurs.
	 * @param format
	 *            The format of the message you would like logged.
	 * @param args
	 *            The arguments used to format the message.
	 */
	public static int d(String tag, String format, Object... args) {
		final String msg = String.format(format, args);
		return println(DEBUG, tag, msg);
	}

	/**
	 * Send a {@link #DEBUG} log message and log the exception.
	 * 
	 * @param tag
	 *            Used to identify the source of a log message. It usually
	 *            identifies the class or activity where the log call occurs.
	 * @param msg
	 *            The message you would like logged.
	 * @param tr
	 *            An exception to log
	 */
	public static int d(String tag, String msg, Throwable tr) {
		return println(DEBUG, tag, msg + '\n' + getStackTraceString(tr));
	}

	/**
	 * Send an {@link #INFO} log message.
	 * 
	 * @param tag
	 *            Used to identify the source of a log message. It usually
	 *            identifies the class or activity where the log call occurs.
	 * @param msg
	 *            The message you would like logged.
	 */
	public static int i(String tag, String msg) {
		return println(INFO, tag, msg);
	}

	/**
	 * Send an {@link #INFO} log message.
	 * 
	 * @param tag
	 *            Used to identify the source of a log message. It usually
	 *            identifies the class or activity where the log call occurs.
	 * @param format
	 *            The format of the message you would like logged.
	 * @param args
	 *            The arguments used to format the message.
	 */
	public static int i(String tag, String format, Object... args) {
		final String msg = String.format(format, args);
		return println(INFO, tag, msg);
	}

	/**
	 * Send a {@link #INFO} log message and log the exception.
	 * 
	 * @param tag
	 *            Used to identify the source of a log message. It usually
	 *            identifies the class or activity where the log call occurs.
	 * @param msg
	 *            The message you would like logged.
	 * @param tr
	 *            An exception to log
	 */
	public static int i(String tag, String msg, Throwable tr) {
		return println(INFO, tag, msg + '\n' + getStackTraceString(tr));
	}

	/**
	 * Send a {@link #WARN} log message.
	 * 
	 * @param tag
	 *            Used to identify the source of a log message. It usually
	 *            identifies the class or activity where the log call occurs.
	 * @param msg
	 *            The message you would like logged.
	 */
	public static int w(String tag, String msg) {
		return println(WARN, tag, msg);
	}

	/**
	 * Send a {@link #WARN} log message.
	 * 
	 * @param tag
	 *            Used to identify the source of a log message. It usually
	 *            identifies the class or activity where the log call occurs.
	 * @param format
	 *            The format of the message you would like logged.
	 * @param args
	 *            The arguments used to format the message.
	 */
	public static int w(String tag, String format, Object... args) {
		final String msg = String.format(format, args);
		return println(WARN, tag, msg);
	}

	/**
	 * Send a {@link #WARN} log message and log the exception.
	 * 
	 * @param tag
	 *            Used to identify the source of a log message. It usually
	 *            identifies the class or activity where the log call occurs.
	 * @param msg
	 *            The message you would like logged.
	 * @param tr
	 *            An exception to log
	 */
	public static int w(String tag, String msg, Throwable tr) {
		return println(WARN, tag, msg + '\n' + getStackTraceString(tr));
	}

	/*
	 * Send a {@link #WARN} log message and log the exception.
	 * 
	 * @param tag Used to identify the source of a log message. It usually
	 * identifies the class or activity where the log call occurs.
	 * 
	 * @param tr An exception to log
	 */
	public static int w(String tag, Throwable tr) {
		return println(WARN, tag, getStackTraceString(tr));
	}

	/**
	 * Send an {@link #ERROR} log message.
	 * 
	 * @param tag
	 *            Used to identify the source of a log message. It usually
	 *            identifies the class or activity where the log call occurs.
	 * @param format
	 *            The format of the message you would like logged.
	 * @param args
	 *            The arguments used to format the message.
	 */
	public static int e(String tag, String format, Object... args) {
		final String msg = String.format(format, args);
		return println(ERROR, tag, msg);
	}

	/**
	 * Send an {@link #ERROR} log message.
	 * 
	 * @param tag
	 *            Used to identify the source of a log message. It usually
	 *            identifies the class or activity where the log call occurs.
	 * @param msg
	 *            The message you would like logged.
	 */
	public static int e(String tag, String msg) {
		return println(ERROR, tag, msg);
	}

	/**
	 * Send a {@link #ERROR} log message and log the exception.
	 * 
	 * @param tag
	 *            Used to identify the source of a log message. It usually
	 *            identifies the class or activity where the log call occurs.
	 * @param msg
	 *            The message you would like logged.
	 * @param tr
	 *            An exception to log
	 */
	public static int e(String tag, String msg, Throwable throwable) {
		final int r = println(ERROR, tag, msg + '\n'
				+ getStackTraceString(throwable));
		return r;
	}

	/**
	 * Handy function to get a loggable stack trace from a Throwable
	 * 
	 * @param tr
	 *            An exception to log
	 */
	public static String getStackTraceString(final Throwable throwable) {
		if (throwable == null) {
			return "";
		}
		final StringWriter stringWriter = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(stringWriter);
		throwable.printStackTrace(printWriter);
		return stringWriter.toString();
	}

	/**
	 * Low-level logging call.
	 * 
	 * @param priority
	 *            The priority/type of this log message
	 * @param tag
	 *            Used to identify the source of a log message. It usually
	 *            identifies the class or activity where the log call occurs.
	 * @param msg
	 *            The message you would like logged.
	 * @return The number of bytes written.
	 */
	private static int println(int priority, String tag, String msg) {
		if (priority < sFilterLevel || TextUtils.isEmpty(msg)) {
			return 0;
		}
		final String[] messageLines = sNewLinePattern.split(msg);
		final String logTag;
		final String format;
		if (TextUtils.isEmpty(sTag) || sTag.equals(tag)) {
			logTag = tag;
			format = null;
		} else if (TextUtils.isEmpty(tag) && !TextUtils.isEmpty(sTag)) {
			logTag = sTag;
			format = null;
		} else {
			logTag = sTag;
			format = MESSAGE_TEMPLATE;
		}
		int bytesWritten = 0;
		if (TextUtils.isEmpty(format)) {
			for (final String message : messageLines) {
				bytesWritten += android.util.Log.println(priority, logTag,
						message);
			}
		} else {
			for (String message : messageLines) {
				message = String.format(MESSAGE_TEMPLATE, tag, message);
				bytesWritten += android.util.Log.println(priority, logTag,
						message);
			}
		}
		return bytesWritten;
	}

	private Log() {

	}

	private static final String ERROR_TEXT = "err.txt";

	private static boolean mEnableErrorReporting = false;

	public static void enableErrorReporting(boolean enable) {
		mEnableErrorReporting = enable;
	}

	private static boolean isEnableErrorReporting() {
		return mEnableErrorReporting;
	}

	public static File getErrorFile(Context c) {
		return new File(c.getCacheDir(), ERROR_TEXT);
	}

	public static String getVersionName(Context c) {
		try {
			PackageInfo info = c.getPackageManager().getPackageInfo(
					c.getPackageName(), 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			return "version error: " + e;
		}
	}

	public static void logError(Context c, Throwable e) {
		if (isEnableErrorReporting()) {
			return;
		}
		File f = getErrorFile(c);
		String v = getVersionName(c);
		logError(f, v, e);
	}

	private static final byte[] logErrorLock = new byte[0];

	private static void logError(File f, String v, Throwable e) {
		synchronized (logErrorLock) {
			PrintWriter out = null;
			try {
				out = new PrintWriter(new FileWriter(f, true));
				out.println("[ERROR]");
				out.print("DATE: ");
				out.println(new java.util.Date());
				out.print("DEVICE: ");
				out.println(Build.DEVICE);
				out.print("MODEL: ");
				out.println(Build.MODEL);
				out.print("SDK: ");
				out.println(Build.VERSION.SDK);
				out.print("VERSION: ");
				out.println(v);
				out.flush();
				if (e != null) {
					e.printStackTrace(out);
				}
			} catch (IOException ie) {
				ie.printStackTrace();
			} finally {
				if (out != null) {
					out.close();
				}
			}
		}
	}
}
