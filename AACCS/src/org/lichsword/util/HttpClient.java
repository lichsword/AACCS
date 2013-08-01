package org.lichsword.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.BasicHttpProcessor;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpClient extends DefaultHttpClient {

    public static final String TAG = HttpClient.class.getSimpleName();

    private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.8) Gecko/20100202 Firefox/3.5.8 GTB6";

    /** Set if HTTP requests are blocked from being executed on this thread */
    private static final ThreadLocal<Boolean> sThreadBlocked = new ThreadLocal<Boolean>();

    /** Interceptor throws an exception if the executing thread is blocked */
    private static final HttpRequestInterceptor sThreadCheckInterceptor = new HttpRequestInterceptor() {
        @Override
        public void process(final HttpRequest request, final HttpContext context) {
            if (sThreadBlocked.get() != null && sThreadBlocked.get()) {
                throw new RuntimeException("This thread forbids HTTP requests");
            }
        }
    };

    private boolean mCookieEnable = false;

    public boolean isCookieEnable() {
        return mCookieEnable;
    }

    /**
     * Change the SSL host name verifier to allowing all hosts.
     */
    public void enableAllowAllVerifier() {
        final ClientConnectionManager connectionManager = getConnectionManager();
        final SchemeRegistry registry = connectionManager.getSchemeRegistry();
        final Scheme httpsScheme = registry.getScheme("https");
        if (httpsScheme != null) {
            final SocketFactory socketFactory = httpsScheme.getSocketFactory();
            if (socketFactory instanceof SSLSocketFactory) {
                ((SSLSocketFactory) socketFactory)
                        .setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            }
        }
    }

    /**
     * Replace the HTTPS schema socket factory with the default
     * {@linkplain SSLSocketFactory}, thus the {@linkplain HttpClient} will use
     * the default security settings.
     */
    public void enableSSLSecurity() {
        final ClientConnectionManager connectionManager = getConnectionManager();
        final SchemeRegistry registry = connectionManager.getSchemeRegistry();
        if (registry != null) {
            final Scheme httpsScheme = registry.getScheme("https");
            if (httpsScheme != null) {
                final SocketFactory socketFactory = httpsScheme
                        .getSocketFactory();
                if (socketFactory instanceof SSLSocketFactory) {
                    return;
                }
                registry.unregister("https");
            }
            registry.register(new Scheme("https", SSLSocketFactory
                    .getSocketFactory(), 443));
        }
    }

    /**
     * Replace the HTTPS schema socket factory with
     * {@linkplain DummySSLSocketFactory}, thus the {@linkplain HttpClient} will
     * trust all SSL links.
     */
    public void disableSSLSecurity() {
        final ClientConnectionManager connectionManager = getConnectionManager();
        if (connectionManager != null) {
            final SchemeRegistry registry = connectionManager
                    .getSchemeRegistry();
            if (registry != null) {
                final Scheme httpsScheme = registry.getScheme("https");
                if (httpsScheme != null) {
                    final SocketFactory socketFactory = httpsScheme
                            .getSocketFactory();
                    // if (socketFactory instanceof DummySSLSocketFactory) {
                    // return;
                    // }
                    registry.unregister("https");
                }
                // registry.register(new Scheme("https",
                // new DummySSLSocketFactory(), 443));
            }
        }

    }

    /**
     * Change the SSL host name verifier to browser compatible settings. This is
     * the default.
     */
    public void enableBrowserCompatibleVerifier() {
        final ClientConnectionManager connectionManager = getConnectionManager();
        final SchemeRegistry registry = connectionManager.getSchemeRegistry();
        final Scheme httpsScheme = registry.getScheme("https");
        if (httpsScheme != null) {
            final SocketFactory socketFactory = httpsScheme.getSocketFactory();
            if (socketFactory instanceof SSLSocketFactory) {
                ((SSLSocketFactory) socketFactory)
                        .setHostnameVerifier(SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            }
        }
    }

    /**
     * Change the SSL host name verifier to strict settings.
     */
    public void enableStrictVerifier() {
        final ClientConnectionManager connectionManager = getConnectionManager();
        final SchemeRegistry registry = connectionManager.getSchemeRegistry();
        final Scheme httpsScheme = registry.getScheme("https");
        if (httpsScheme != null) {
            final SocketFactory socketFactory = httpsScheme.getSocketFactory();
            if (socketFactory instanceof SSLSocketFactory) {
                ((SSLSocketFactory) socketFactory)
                        .setHostnameVerifier(SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
            }
        }
    }

    public void setCookieEnable(final boolean cookieEnable) {
        mCookieEnable = cookieEnable;
    }

    /**
     * Retrieve the adsContent of the URL as string.
     * 
     * @param url
     *            The URL to retrieve.
     * @return The adsContent of the given URL, or null if error occurred.
     */
    public static String getUrlAsString(final String url) {
        try {
            final HttpGet get = new HttpGet(url);
            final HttpClient httpClient = HttpClient.newInstance();
            final HttpResponse response = httpClient.execute(get);
            final int statusCode = response.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK == statusCode) {
                // get response String
                return EntityUtils.toString(response.getEntity());
            } else {
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieve the adsContent of the URL as string.
     * 
     * @param url
     *            The URL to retrieve.
     * @param defaultCharset
     * 
     * @return The adsContent of the given URL, or null if error occurred.
     */
    public static String getUrlAsString(final String url, String defaultCharset) {
        try {
            final HttpGet get = new HttpGet(url);
            final HttpClient httpClient = HttpClient.newInstance();
            final HttpResponse response = httpClient.execute(get);
            final int statusCode = response.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK == statusCode) {
                // get response String
                return EntityUtils.toString(response.getEntity(),
                        defaultCharset);
            } else {
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieve the adsContent of the URL as {@linkplain JSONObject}.
     * 
     * @param url
     *            The URL to retrieve.
     * @return The adsContent of the given URL, or null if error occurred.
     */
    public static JSONObject getUrlAsJSONObject(final String url) {
        final String json = getUrlAsString(url);
        if (json != null) {
            try {
                return new JSONObject(json);
            } catch (final JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Retrieve the adsContent of the URL as {@linkplain JSONArray}.
     * 
     * @param url
     *            The URL to retrieve.
     * @return The adsContent of the given URL, or null if error occurred.
     */
    public static JSONArray getUrlAsJSONArray(final String url) {
        final String json = getUrlAsString(url);
        if (json != null) {
            try {
                return new JSONArray(json);
            } catch (final JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Download specified url directly and save it to local path.
     * 
     * @param url
     *            the url of the file.
     * @param saveToFile
     *            the file to save to.
     * @return true if success, false otherise.
     */
    public static boolean downloadFile(final String url, final File saveToFile,
            IFileProcess callback) {
        if (TextUtils.isEmpty(url)) {
            return true;
        }
        if (null == saveToFile) {
            throw new IllegalArgumentException("saveToFile may not be null.");
        }
        if (saveToFile.exists()) {
            saveToFile.delete();
        }
        FileOutputStream fos = null;
        try {
            final HttpGet get = new HttpGet(url);
            final HttpClient httpClient = HttpClient.newInstance();
            final HttpResponse response = httpClient.execute(get);
            final int statusCode = response.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK == statusCode) {
                // get response String
                fos = new FileOutputStream(saveToFile);
                response.getEntity().writeTo(fos);
                return true;
            } else {
            }
        } catch (final Exception e) {
            // e.printStackTrace();
        } finally {
            quietClose(fos);
        }
        return false;

    }

    public interface IFileProcess {
        public void onProcess(long processed, long total);
    }

    /**
     * Decode {@linkplain InputStream} from entity.
     * 
     * @param entity
     *            the entity to decode.
     * @return the {@linkplain InputStream} decoded from the entity.
     */
    public static InputStream decodeEntityAsStream(final HttpEntity entity) {
        InputStream result = null;
        if (entity != null) {
            try {
                result = entity.getContent();
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Create a new HttpClient with reasonable defaults (which you can update).
     * 
     * @param userAgent
     *            to report in your HTTP requests.
     * @param ignoreSSLSecurity
     *            if true, SSL will trust all certificates, otherwise SSL will
     *            check certificates.
     * @return HttpClient for you to use for all your requests.
     */
    public static HttpClient newInstance(String userAgent,
            final boolean ignoreSSLSecurity) {
        final HttpParams params = new BasicHttpParams();

        // Turn off stale checking. Our connections break all the time anyway,
        // and it's not worth it to pay the penalty of checking every time.
        HttpConnectionParams.setStaleCheckingEnabled(params, false);

        // Default connection and socket timeout of 10 seconds. Tweak to taste.
        HttpConnectionParams.setConnectionTimeout(params, 6 * 1000);
        HttpConnectionParams.setSoTimeout(params, 15 * 1000);
        HttpConnectionParams.setSocketBufferSize(params, 8192);

        // Don't handle redirects -- return them to the caller. Our code
        // often wants to re-POST after a redirect, which we must do ourselves.
        HttpClientParams.setRedirecting(params, true);

        // Set the specified user agent and register standard protocols.
        if (TextUtils.isEmpty(userAgent)) {
            userAgent = DEFAULT_USER_AGENT;
        }
        HttpProtocolParams.setUserAgent(params, userAgent);
        final SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory
                .getSocketFactory(), 80));
        // schemeRegistry.register(new Scheme("https",
        // ignoreSSLSecurity ? new DummySSLSocketFactory()
        // : SSLSocketFactory.getSocketFactory(), 443));

        // final ClientConnectionManager manager =
        // new ThreadSafeClientConnManager(params, schemeRegistry);

        // We use a factory method to modify superclass initialization
        // parameters without the funny call-a-static-method dance.

        // return new HttpClient(manager, params);

        return new HttpClient(params);
    }

    /**
     * Create a new HttpClient with reasonable defaults (which you can update).
     * 
     * @return HttpClient for you to use for all your requests.
     */
    public static HttpClient newInstance() {
        return newInstance(null, false);
    }

    /**
     * Create a new HttpClient with reasonable defaults (which you can update).
     * 
     * @param ignoreSSLSecurity
     *            if true, SSL will trust all certificates, otherwise SSL will
     *            check certificates.
     * @return HttpClient for you to use for all your requests.
     */
    public static HttpClient newInstance(final boolean ignoreSSLSecurity) {
        return newInstance(null, ignoreSSLSecurity);
    }

    /**
     * Create a new HttpClient with reasonable defaults (which you can update).
     * 
     * @param userAgent
     *            to report in your HTTP requests.
     * @return HttpClient for you to use for all your requests.
     */
    public static HttpClient newInstance(final String userAgent) {
        return newInstance(userAgent, false);
    }

    private HttpClient(final HttpParams params) {
        super(params);
    }

    private HttpClient(final ClientConnectionManager conman,
            final HttpParams params) {
        super(conman, params);
    }

    @Override
    protected BasicHttpProcessor createHttpProcessor() {
        // Add interceptor to prevent making requests from main thread.
        final BasicHttpProcessor processor = super.createHttpProcessor();
        processor.addRequestInterceptor(sThreadCheckInterceptor);

        return processor;
    }

    @Override
    protected HttpContext createHttpContext() {
        // Same as DefaultHttpClient.createHttpContext() minus the
        // cookie store.
        final HttpContext context = new BasicHttpContext();
        context.setAttribute(ClientContext.AUTHSCHEME_REGISTRY,
                getAuthSchemes());
        context.setAttribute(ClientContext.COOKIESPEC_REGISTRY,
                getCookieSpecs());
        context.setAttribute(ClientContext.CREDS_PROVIDER,
                getCredentialsProvider());
        if (mCookieEnable) {
            context.setAttribute(ClientContext.COOKIE_STORE, getCookieStore());
        }
        return context;
    }

    /**
     * Close an {@linkplain Closeable} quietly.
     * 
     * @param closeable
     *            the {@linkplain Closeable} to close.
     */
    public static final void quietClose(final Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (final IOException e) {
                // Ignore errors.
            }
        }
    }

    /**
     * Block this thread from executing HTTP requests. Used to guard against
     * HTTP requests blocking the main application thread.
     * 
     * @param blocked
     *            if HTTP requests run on this thread should be denied
     */
    public static void setThreadBlocked(final boolean blocked) {
        sThreadBlocked.set(blocked);
    }

    /**
     * Get a String for "Set-Cookie" head from a cookie
     * 
     * @param cookie
     * @return
     */
    public static String getSetCookieString(final Cookie cookie) {
        String setCookie = null;
        if (null != cookie) {
            final StringBuilder builder = new StringBuilder();
            builder.append(cookie.getName());
            builder.append("=");
            builder.append(cookie.getValue());

            final Date expires = cookie.getExpiryDate();
            if (null != expires) {
                builder.append(";expires=");
                builder.append(expires.toGMTString());
            }
            builder.append(";path=");
            builder.append(cookie.getPath());
            builder.append(";domain=");
            builder.append(cookie.getDomain());
            if (cookie.isSecure()) {
                builder.append(";Secure");
            }
            setCookie = builder.toString();
        }
        return setCookie;
    }

    /**
     * <p>
     * Post name value pair to url.
     * </p>
     * <p>
     * e.g. url="www.google.com", pairs=
     * "[v_name=, o_id=19, s=post method test, c=123456, qc=, rurl=, content=]"
     * </p>
     * 
     * @param url
     * @param pairs
     */
    public static HttpResponse postContent(final String url,
            List<NameValuePair> pairs) {
        LogHelper.d(TAG, "host url = " + url);
        LogHelper.d(TAG, "post param = " + pairs);

        HttpPost post = new HttpPost(url);
        final HttpClient httpClient = HttpClient.newInstance();
        try {
            // must certain encoding in construct of UrlEncodedFormEntity.
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(pairs,
                    HTTP.UTF_8);
            post.setEntity(formEntity);
            // Normal Mode.
            HttpResponse response = httpClient.execute(post);
            if (null != response) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (HttpStatus.SC_OK == statusCode) {
                    // do nothing
                }
            }// end if
            return response;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String postContentWithResponseContent(final String url,
            List<NameValuePair> pairs) {
        LogHelper.d(TAG, "host url = " + url);
        LogHelper.d(TAG, "post param = " + pairs);

        String result = null;
        HttpPost post = new HttpPost(url);
        final HttpClient httpClient = HttpClient.newInstance();
        try {
            // must certain encoding in construct of UrlEncodedFormEntity.
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(pairs,
                    HTTP.UTF_8);
            post.setEntity(formEntity);

            // Normal Mode.
            HttpResponse response = httpClient.execute(post);
            if (null != response) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (HttpStatus.SC_OK == statusCode) {
                    // do nothing

                    InputStream inputStream = response.getEntity().getContent();

                    result = FileUtil.readInputStream(inputStream);
                }
            }// end if
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            result = null;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            result = null;
        } catch (IOException e) {
            e.printStackTrace();
            result = null;
        }
        return result;
    }

}
