package org.lichsword.service.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lichsword.service.WebClientService;
import org.lichsword.util.LogHelper;

public class JsonClientService extends WebClientService {

    public static final String TAG = JsonClientService.class.getSimpleName();

    protected JSONObject retrieveJson(String url) throws JSONException {
        String content = retrieveJsonString(url);
        JSONObject object = new JSONObject(content);
        return object;
    }

    protected JSONArray retrieveJsonArray(String url) throws JSONException {
        String content = retrieveJsonString(url);
        JSONArray array = new JSONArray(content);
        return array;
    }

    private String retrieveJsonString(String url) {
        LogHelper.w(TAG, "load url = " + url);
        String content = null;
        JsonCacheCenter center = JsonCacheCenter.getInstance();
        if (center.hasCached(url)) {
            content = center.getCacheValue(url);
        } else {
            content = retrieveContent(url);
            center.saveCacheValue(url, content);
        }
        LogHelper.w(TAG, "load content = " + content);
        return content;
    }
}