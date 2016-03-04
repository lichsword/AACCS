/*
Copyright (c) 2007-2009, Yusuke Yamamoto
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
 * Neither the name of the Yusuke Yamamoto nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY Yusuke Yamamoto ``AS IS'' AND ANY
EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL Yusuke Yamamoto BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.at.common.http;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.at.common.AtuLog;

/**
 * A data class representing HTTP ATHttpResponse
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public class ATHttpResponse {
    private final static boolean DEBUG = false;

    private static ThreadLocal<DocumentBuilder> builders = new ThreadLocal<DocumentBuilder>() {
        @Override
        protected DocumentBuilder initialValue() {
            try {
                return DocumentBuilderFactory.newInstance().newDocumentBuilder();
            } catch (ParserConfigurationException ex) {
                throw new ExceptionInInitializerError(ex);
            }
        }
    };

    private int statusCode;
    private Document responseAsDocument = null;
    private String responseAsString = null;
    private InputStream is;
    private HttpURLConnection con;
    private boolean streamConsumed = false;
    private String encoding = null;
    private static Pattern META_CHARSET_PATTERN = Pattern.compile("<meta .* ?charset=([-_a-zA-Z0-9]*)\"");
    private static Pattern CONTENT_TYPE_CHARSET_PATTERN = Pattern.compile("charset=([-_a-zA-Z0-9]*)");

    public ATHttpResponse(HttpURLConnection con) throws IOException {
        this.con = con;
        this.statusCode = con.getResponseCode();
        if (statusCode == 200) {
            is = con.getInputStream();
        } else {
            is = con.getErrorStream();
        }
        if ("gzip".equalsIgnoreCase(con.getContentEncoding())) {
            // the response is gzipped
            is = new GZIPInputStream(is);
        }
        String contentType = con.getContentType();
        if (null != contentType) {
            Matcher matcher = CONTENT_TYPE_CHARSET_PATTERN.matcher(contentType);
            if (matcher.find()) {
                encoding = matcher.group(1);
            }
        }
    }

    // for test purpose
    /* package */ ATHttpResponse(String content) {
        this.responseAsString = content;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseHeader(String name) {
        return con.getHeaderField(name);
    }

    /**
     * Returns the response stream.<br>
     * This method cannot be called after calling asString() or asDcoument()<br>
     * It is suggested to call disconnect() after consuming the stream.
     * <p/>
     * Disconnects the internal HttpURLConnection silently.
     *
     * @return
     * @see #disconnect()
     */
    public InputStream asStream() {
        if (streamConsumed) {
            throw new IllegalStateException("Stream has already been consumed.");
        }
        return is;
    }

    /**
     * Returns the response body as string.<br>
     * Disconnects the internal HttpURLConnection silently.
     *
     * @return response body
     * @throws Exception
     */
    public String asString() throws Exception {
        if (null == responseAsString) {
            InputStream stream = asStream();
            String currentEncoding;
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[8192];
            int size = -1;
            while ((size = stream.read(buffer)) != -1) {
                byteStream.write(buffer, 0, size);
            }
            stream.close();
            con.disconnect();
            byte[] data = byteStream.toByteArray();
            if (encoding != null) {
                this.responseAsString = new String(data, encoding);
                currentEncoding = encoding;
            } else {
                this.responseAsString = new String(data, "utf-8");
                currentEncoding = "utf-8";
                encoding = getEncoding(this.responseAsString);
                if (!currentEncoding.equalsIgnoreCase(encoding)) {
                    this.responseAsString = new String(data, encoding);
                }
            }
            log(responseAsString);
            streamConsumed = true;
        }
        return responseAsString;
    }

    /**
     * Returns the response body as bytes.<br>
     * Disconnects the internal HttpURLConnection silently.
     *
     * @return response body
     * @throws Exception
     */
    public byte[] asBytes() throws Exception {
        InputStream stream = asStream();
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int size = -1;
        while ((size = stream.read(buffer)) != -1) {
            byteStream.write(buffer, 0, size);
        }
        stream.close();
        con.disconnect();
        byte[] data = byteStream.toByteArray();
        return data;
    }

    public static String getEncoding(String html) {
        Matcher matcher = META_CHARSET_PATTERN.matcher(html);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "utf-8";
    }

    /**
     * Returns the response body as org.w3c.dom.Document.<br>
     * Disconnects the internal HttpURLConnection silently.
     *
     * @return response body as org.w3c.dom.Document
     * @throws Exception
     */
    public Document asDocument() throws Exception {
        if (null == responseAsDocument) {
            // it should be faster to read the inputstream directly.
            // but makes it difficult to troubleshoot
            this.responseAsDocument = builders.get().parse(new ByteArrayInputStream(asString().getBytes("UTF-8")));
        }
        return responseAsDocument;
    }

    /**
     * Returns the response body as
     * com.mgeek.android.twitter.org.json.JSONObject.<br>
     * Disconnects the internal HttpURLConnection silently.
     *
     * @return response body as com.mgeek.android.twitter.org.json.JSONObject
     * @throws Exception
     */
    public JSONObject asJSONObject() throws Exception {
        return (JSONObject) JSONObject.parse(asString());
    }

    /**
     * Returns the response body as
     * com.mgeek.android.twitter.org.json.JSONArray.<br>
     * Disconnects the internal HttpURLConnection silently.
     *
     * @return response body as com.mgeek.android.twitter.org.json.JSONArray
     * @throws Exception
     */
    public JSONArray asJSONArray() throws Exception {
    	return (JSONArray) JSONArray.parse(asString());
    }

    public InputStreamReader asReader() {
        try {
            return new InputStreamReader(is, "UTF-8");
        } catch (java.io.UnsupportedEncodingException uee) {
            return new InputStreamReader(is);
        }
    }

    public void disconnect() {
        con.disconnect();
    }

    @Override
    public String toString() {
        if (null != responseAsString) {
            return responseAsString;
        }
        return "ATHttpResponse{" + "statusCode=" + statusCode + ", response=" + responseAsDocument + ", responseString='" + responseAsString + '\'' + ", is=" + is + ", con=" + con + '}';
    }

    private void log(String message) {
        if (DEBUG) {
            AtuLog.d("ATHttpResponse", message);
        }
    }

    private void log(String message, String message2) {
        if (DEBUG) {
            log(message + message2);
        }
    }
}
