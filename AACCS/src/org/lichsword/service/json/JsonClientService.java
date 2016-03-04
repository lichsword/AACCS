package org.lichsword.service.json;

import org.lichsword.service.WebClientService;
import org.lichsword.util.LogHelper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsonClientService extends WebClientService {

	public static final String TAG = JsonClientService.class.getSimpleName();

	private static JsonClientService sInstance;

	private JsonClientService() {
		// to do
	}

	public static JsonClientService getInstance() {
		if (null == sInstance) {
			sInstance = new JsonClientService();
		} // end if
		return sInstance;
	}

	public JSONObject retrieveJsonObject(String url) {
		String content = retrieveJsonString(url);
		JSONObject object = JSONObject.parseObject(content);
		return object;
	}

	public JSONArray retrieveJsonArray(String url) {
		String content = retrieveJsonString(url);
		JSONArray array = JSONArray.parseArray(content);
		return array;
	}

	public String retrieveJsonString(String url) {
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