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

import com.at.common.http.ATHttpClient;
import com.at.common.http.ATHttpResponse;

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
		ATHttpClient client = new ATHttpClient();
		ATHttpResponse response;
		try {
			response = client.get(url);
			return response.asString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "{}";
	}
}
