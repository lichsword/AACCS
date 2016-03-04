package com.at.common.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import com.at.common.AtuLog;
import com.at.common.AtuText;


/**
 * A utility class to handle HTTP request/response.
 */
public class ATHttpClient {

    private static final int OK = 200;// OK: Success!
    private static final int NOT_MODIFIED = 304;// Not Modified: There was no
                                                // new data to return.
    private static final int BAD_REQUEST = 400;// Bad Request: The request was
                                               // invalid. An accompanying error
                                               // message will explain why. This
                                               // is the status code will be
                                               // returned during rate limiting.
    private static final int NOT_AUTHORIZED = 401;// Not Authorized:
                                                  // Authentication credentials
                                                  // were missing or incorrect.
    private static final int FORBIDDEN = 403;// Forbidden: The request is
                                             // understood, but it has been
                                             // refused. An accompanying error
                                             // message will explain why.
    private static final int NOT_FOUND = 404;// Not Found: The URI requested is
                                             // invalid or the resource
                                             // requested, such as a user, does
                                             // not exists.
    private static final int NOT_ACCEPTABLE = 406;// Not Acceptable: Returned by
                                                  // the Search API when an
                                                  // invalid format is specified
                                                  // in the request.
    private static final int INTERNAL_SERVER_ERROR = 500;// Internal Server
                                                         // Error: Something is
                                                         // broken. Please post
                                                         // to the group so the
                                                         // Twitter team can
                                                         // investigate.
    private static final int BAD_GATEWAY = 502;// Bad Gateway: Twitter is down
                                               // or being upgraded.
    private static final int SERVICE_UNAVAILABLE = 503;// Service Unavailable:
                                                       // The Twitter servers
                                                       // are up, but overloaded
                                                       // with requests. Try
                                                       // again later. The
                                                       // search and trend
                                                       // methods use this to
                                                       // indicate when you are
                                                       // being rate limited.

    private final static boolean DEBUG = false;
    // private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Macintosh;
    // " +
//            "U; Intel Mac OS X 10_5_5; en-us) AppleWebKit/525.18 (KHTML, " +
//            "like Gecko) Version/3.1.2 Safari/525.20.1";
    private static final String DEFAULT_USER_AGENT = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; EmbeddedWB 14.52 from: http://www.bsalsa.com/ EmbeddedWB 14.52; .NET CLR 1.1.4322; .NET CLR 2.0.50727; InfoPath.1; .NET CLR 1.0.3705; .NET CLR 3.0.04506.30)";
    private final String basic;
    private int retryCount = 0;
    private int retryIntervalMillis = 3 * 1000;
    private int connectionTimeout = 20000;
    private int readTimeout = 120000;
    private String originCookie;
    private String cookie;
    private boolean useCookie;
    private boolean saveCookie;
    private String userAgent;

    public ATHttpClient() {
        this.basic = null;
        setUserAgent(null);
        saveCookie = false;
        useCookie = false;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    /**
     * Sets a specified timeout value, in milliseconds, to be used when opening
     * a communications link to the resource referenced by this URLConnection.
     *
     * @param connectionTimeout
     *            - an int that specifies the connect timeout value in
     *            milliseconds
     */
    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    /**
     * Sets the read timeout to a specified timeout, in milliseconds. System
     * property -Dtwitter4j.http.readTimeout overrides this attribute.
     *
     * @param readTimeout
     *            - an int that specifies the timeout value to be used in
     *            milliseconds
     */
    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public void setRetryCount(int retryCount) {
        if (retryCount >= 0) {
            this.retryCount = retryCount;
        } else {
            throw new IllegalArgumentException("RetryCount cannot be negative.");
        }
    }

    public void setUserAgent(String ua) {
        if (AtuText.isEmpty(ua)) {
            userAgent = DEFAULT_USER_AGENT;
        } else {
            userAgent = ua;
        }
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setRetryIntervalSecs(int retryIntervalSecs) {
        if (retryIntervalSecs >= 0) {
            this.retryIntervalMillis = retryIntervalSecs * 1000;
        } else {
            throw new IllegalArgumentException("RetryInterval cannot be negative.");
        }
    }

    public boolean isUseCookie() {
        return useCookie;
    }

    public void setUseCookie(boolean useCookie) {
        this.useCookie = useCookie;
    }

    public boolean isSaveCookie() {
        return saveCookie;
    }

    public void setSaveCookie(boolean saveCookie) {
        this.saveCookie = saveCookie;
    }

    public ATHttpResponse post(String url) throws Exception {
        return post(url, new ATHttpParameter[0], null);
    }

    public ATHttpResponse post(String url, ATHttpParameter... postParams) throws Exception {
        return post(url, postParams, null);
    }

    public ATHttpResponse post(String url, ATHttpParameter[] postParams, ATHttpParameter[] heads) throws Exception {
        return httpRequest(url, postParams, heads);
    }

    public ATHttpResponse get(String url) throws Exception {
        return get(url, null, null);
    }

    public ATHttpResponse get(String url, ATHttpParameter... queryParams) throws Exception {
        return get(url, queryParams, null);
    }

    public ATHttpResponse get(String url, ATHttpParameter[] queryParams, ATHttpParameter[] heads) throws Exception {
        if (queryParams != null && queryParams.length > 0) {
            String query = encodeParameters(queryParams);
            url += "?" + query;
        }
        return httpRequest(url, null, heads);
    }

    protected ATHttpResponse httpRequest(String url, ATHttpParameter[] postParams, ATHttpParameter[] heads) throws Exception {
        int retriedCount;
        int retry = retryCount + 1;
        ATHttpResponse res = null;
        for (retriedCount = 0; retriedCount < retry; retriedCount++) {
            int responseCode = -1;
            try {
                HttpURLConnection con = null;
                OutputStream osw = null;
                try {
                    con = getConnection(url);
                    con.setDoInput(true);
                    if (useCookie) {
                        setCookie(con);
                    }
                    setHeaders(url, heads, con);
                    if (null != postParams) {
                        con.setRequestMethod("POST");
                        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        con.setDoOutput(true);
                        String postParam = encodeParameters(postParams);
//                        log("Post Params: ", postParam);
                        byte[] bytes = postParam.getBytes("UTF-8");

                        con.setRequestProperty("Content-Length", Integer.toString(bytes.length));

                        osw = con.getOutputStream();
                        osw.write(bytes);
                        osw.flush();
                        osw.close();
                    } else {
                        con.setRequestMethod("GET");
                    }
                    res = new ATHttpResponse(con);
                    responseCode = con.getResponseCode();
                    if (DEBUG) {
                        log("ATHttpResponse: ");
                        Map<String, List<String>> responseHeaders = con.getHeaderFields();
                        for (String key : responseHeaders.keySet()) {
                            List<String> values = responseHeaders.get(key);
                            for (String value : values) {
                                if (null != key) {
                                    log(key + ": " + value);
                                } else {
                                    log(value);
                                }
                            }
                        }
                    }
                    if (responseCode != OK) {
                        if (retriedCount == retryCount) {
                            log("reach retiredCount, stop retry download" + retriedCount);
                        }
//                        if (responseCode < INTERNAL_SERVER_ERROR) {
//                            throw new Exception(getCause(responseCode));
//                        }
                        // will retry if the status code is
                        // INTERNAL_SERVER_ERROR
                    } else {
                        if (saveCookie) {
                            saveCookie(con);
                        }
                        break;
                    }
                } finally {
                    try {
                        osw.close();
                    } catch (Exception ignore) {
                    }
                }
            } catch (Exception exception) {
                // connection timeout or read timeout
                if (retriedCount == retryCount) {
                    throw new Exception(exception.getMessage(), exception);
                }
            }
            try {
//                log("Sleeping " + retryIntervalMillis +" millisecs for next retry.");
                Thread.sleep(retryIntervalMillis);
            } catch (InterruptedException ignore) {
                // nothing to do
            }
        }
        return res;
    }

    public static String encodeParameters(ATHttpParameter[] postParams) {
        StringBuffer buf = new StringBuffer();
        for (ATHttpParameter ATHttpParameter : postParams) {
            if (buf.length() > 0) {
                buf.append("&");
            }
            buf.append(URLEncoder.encode(ATHttpParameter.name)).append("=").append(URLEncoder.encode(ATHttpParameter.value));
        }
        return buf.toString();

    }

    /**
     * sets HTTP headers
     *
     * @param url
     * @param heads
     * @param connection
     *            HttpURLConnection
     */
    private void setHeaders(String url, ATHttpParameter[] heads, HttpURLConnection connection) {

        connection.setRequestProperty("User-Agent", userAgent);
        if (heads != null) {
            for (ATHttpParameter head : heads) {
                connection.setRequestProperty(head.getName(), head.getValue());
                log(head.getName() + ": " + head.getValue());
            }
        }
    }

    private HttpURLConnection getConnection(String url) throws IOException {
        HttpURLConnection con = null;
        con = (HttpURLConnection) new URL(url).openConnection();
        if (connectionTimeout > 0) {
            con.setConnectTimeout(connectionTimeout);
        }
        if (readTimeout > 0) {
            con.setReadTimeout(readTimeout);
        }
        return con;
    }

    private static void log(String message) {
        if (DEBUG) {
            AtuLog.d("MgeekHttpClient", message);
        }
    }

    private static void log(String message1, String message2) {
        if (DEBUG) {
            AtuLog.d("MgeekHttpClient", message1 + "\n" + message2);
        }
    }

    private static String getCause(int statusCode) {
        String cause = null;
        // http://apiwiki.twitter.com/HTTP-ATHttpResponse-Codes-and-Errors
        switch (statusCode) {
        case NOT_MODIFIED:
            cause = "NOT_MODIFIED";
            break;
        case BAD_REQUEST:
            cause = "BAD_REQUEST";
            break;
        case NOT_AUTHORIZED:
            cause = "NOT_AUTHORIZED";
            break;
        case FORBIDDEN:
            cause = "FORBIDDEN";
            break;
        case NOT_FOUND:
            cause = "NOT_FOUND";
            break;
        case NOT_ACCEPTABLE:
            cause = "NOT_ACCEPTABLE";
            break;
        case INTERNAL_SERVER_ERROR:
            cause = "INTERNAL_SERVER_ERROR";
            break;
        case BAD_GATEWAY:
            cause = "BAD_GATEWAY";
            break;
        case SERVICE_UNAVAILABLE:
            cause = "SERVICE_UNAVAILABLE";
            break;
        default:
            cause = "";
        }
        return statusCode + ":" + cause;
    }

    public void clearCookie() {
        cookie = null;
        originCookie = null;
    }

    public String getCookie() {
        return cookie;
    }

    public String getOriginCookie() {
        return originCookie;
    }

    private void saveCookie(HttpURLConnection httpURLConnection) {
        Map<String, List<String>> heads = httpURLConnection.getHeaderFields();
        if (heads == null)
            return;
        StringBuilder setCookie = new StringBuilder();
        List<String> list = heads.get("set-cookie");
        if (list == null) {
            return;
        }
        originCookie = list.toString();
        originCookie = originCookie.substring(1, originCookie.length() - 1);
        for (String name : list) {
            int index = name.indexOf(";");
            if (index != -1) {
                setCookie.append(name.substring(0, index + 1));
            } else {
                setCookie.append(name).append(";");
            }
        }
        if (setCookie.length() > 0) {
            cookie = setCookie.toString();
        }
    }

    private void setCookie(HttpURLConnection httpURLConnection) {
        if (cookie != null) {
            httpURLConnection.setRequestProperty("Cookie", cookie);
        }
    }
}
