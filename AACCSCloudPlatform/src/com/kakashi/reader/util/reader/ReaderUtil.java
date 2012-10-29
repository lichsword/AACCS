package com.kakashi.reader.util.reader;

import java.text.DateFormat;
import java.util.Date;

import org.apache.http.HttpVersion;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

public class ReaderUtil {

	private final static String TAG = ReaderUtil.class.getSimpleName();

	// public static final String GOOGLE_READER_LOGIN =
	// "https://www.google.com/accounts/ClientLogin";
	//
	// public static final String APP_NAME = "KakashiGoogleReader";
	//
	// public static final String GOOGLE_WEBSITE = "http://www.google.com";
	//
	// public static final String POST_KEY_EMAIL = "Email";
	//
	// public static final String POST_KEY_PASSWORD = "Passwd";
	//
	// public static final String POST_KEY_SERVICE = "service";
	//
	// public static final String DEFAULT_POST_SERVICE = "reader";
	//
	// public static final String POST_KEY_CONTINUE = "continue";
	//
	// public static final String DEFAULT_POST_CONTINUE =
	// "http://www.google.com";
	//
	// public static final String KEY_SID = "SID";
	//
	// public static final String URL_SUBSCRIPTION_LIST =
	// "http://www.google.com/reader/api/0/subscription/list";

	/**
	 * Gmail of account.
	 */
	public static final String EXTRA_GMAIL = "extra_gmail";

	/**
	 * Password match Gmail.
	 */
	public static final String EXTRA_PASSWORD = "extra_password";

	/**
	 * <p>
	 * Command : sign in google reader with gmail & password
	 * </p>
	 * <p>
	 * Extra : {@link #EXTRA_GMAIL}
	 * </p>
	 * <p>
	 * Extra : {@link #EXTRA_PASSWORD}
	 * </p>
	 */
	public static final String COMMAND_SIGN_IN = "com.kakashi.reader.command.SIGN_IN";

	/**
	 * Action : broadcast sign in start.
	 */
	public static final String ACTION_SIGN_IN_START = "com.kakashi.reader.action.SIGN_IN_START";

	/**
	 * Action : broadcast sign in success.
	 */
	public static final String ACTION_SIGN_IN_SUCCESS = "com.kakashi.reader.action.SIGN_IN_SUCCESS";

	/**
	 * Action : broadcast sign in failed.
	 */
	public static final String ACTION_SIGN_IN_FAILED = "com.kakashi.reader.action.SIGN_IN_FAILED";
	
	/**
	 * Action : broadcast sync subscription start.
	 */
	public static final String ACTION_SYNC_SUBSCRIPTION_START = "com.kakashi.reader.action.SYNC_SUBSCRIPTION_START";
	
	/**
	 * Action : broadcast sync subscription complete.
	 */
	public static final String ACTION_SYNC_SUBSCRIPTION_COMPLETE = "com.kakashi.reader.action.SYNC_SUBSCRIPTION_COMPLETE";

	/**
	 * Action : broadcast unread rss item has modified, maybe need refresh list.
	 */
	public static final String ACTION_UNREAD_RSS_MODIFIED = "com.kakashi.reader.action.UNREAD_RSS_MODIFIED";

	public static DefaultHttpClient createHttpClient() {
		HttpParams config = new BasicHttpParams();
		HttpProtocolParams.setVersion(config, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(config, HTTP.UTF_8);
		HttpProtocolParams.setUserAgent(config, TAG);

		final SchemeRegistry reg = new SchemeRegistry();
		reg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(),
				80));
		reg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(),
				443));

		final ThreadSafeClientConnManager manager = new ThreadSafeClientConnManager(
				config, reg);

		DefaultHttpClient client = new DefaultHttpClient(manager, config);
		client.getParams().setParameter("http.socket.timeout", 30 * 1000);
		return client;
	}

	public static String asString(Object value) {
		return asString(value, null);
	}

	public static String asString(Object value, String defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		try {
			return String.valueOf(value).trim();
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public static int asInt(Object value) {
		return asInt(value, 0);
	}

	public static int asInt(Object value, int defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(String.valueOf(value));
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public static long asLong(Object value) {
		return asLong(value, 0);
	}

	public static long asLong(Object value, long defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		try {
			return Long.parseLong(String.valueOf(value));
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}
	
	public static String formatTimeAgo(long time) {
        long diff = (System.currentTimeMillis() / 1000) - time;
        if (diff < (7 * 24 * 60 * 60)) {
            if (diff < (60 * 60)) {
                return (diff / 60) + " min ago";
            } else if (diff < (24 * 60 * 60)) {
                return (diff / 60 / 60) + " hours ago";
            } else {
                return (diff / 24 / 60 / 60) + " days ago";
            }
        }
        return DateFormat.getDateInstance().format(new Date(time * 1000));
    }
}
