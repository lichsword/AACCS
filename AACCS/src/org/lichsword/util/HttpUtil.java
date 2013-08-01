/******************************************************************************
 *
 * Copyright 2012 Lichsword Studio, All right reserved.
 *
 * File name   : HttpUtil.java
 * Create time : 2012-10-11
 * Author      : lichsword
 * Description : TODO
 *
 *****************************************************************************/
package org.lichsword.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtil {

	public static URL getURL(String url) {
		URL myURL = null;
		try {
			myURL = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return myURL;
	}

	public static InputStream getInputStream(String url) {
		URL myURL = null;
		InputStream inputStream = null;
		try {
			myURL = new URL(url);
			inputStream = myURL.openStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputStream;
	}

	public static String getSourceCode(String url) {
		StringBuilder sb = new StringBuilder();

		URL myUrl = getURL(url);
		HttpURLConnection connection;
		BufferedReader reader;
		try {
			connection = (HttpURLConnection) myUrl.openConnection();
			reader = new BufferedReader(new InputStreamReader(connection
					.getInputStream()));
			String line;
			while (null != (line = reader.readLine())) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}
}
