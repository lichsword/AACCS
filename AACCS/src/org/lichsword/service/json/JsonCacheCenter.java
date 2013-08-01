package org.lichsword.service.json;

import java.util.WeakHashMap;

import org.lichsword.util.TextUtils;

/**
 * @author wangyue.wy
 * 
 */
public class JsonCacheCenter {

    public static final String TAG = JsonCacheCenter.class.getSimpleName();

    private JsonCacheCenter() {
        super();
    }

    private static JsonCacheCenter sIntance;

    public static JsonCacheCenter getInstance() {
        if (null == sIntance) {
            sIntance = new JsonCacheCenter();
        }// end if
        return sIntance;
    }

    private final WeakHashMap<Integer, String> cache = new WeakHashMap<Integer, String>();

    public boolean hasCached(String url) {
        if (TextUtils.isEmpty(url)) {
            throw new IllegalArgumentException("parameter url is null");
        } else {
            return hasCached(url.hashCode());
        }
    }

    public boolean hasCached(int key) {
        String value = cache.get(key);
        return !TextUtils.isEmpty(value);
    }

    public String getCacheValue(String url) {
        return getCacheValue(url.hashCode());
    }

    public String getCacheValue(int key) {
        return cache.get(key);
    }

    public void saveCacheValue(String url, String value) {
        String preValue = cache.put(url.hashCode(), value);
        // LogHelper.v(TAG, "previous value=" + preValue);
    }

}
