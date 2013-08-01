/******************************************************************************
 *
 * Copyright 2012, Yitao Technologies Co. Ltd.
 *
 * File name : WebClientService.java
 * Create time : 2012-12-31
 * Author : wangyue.wy
 * Description : TODO
 * History:
 * 1. Date: 2012-12-31
 * Author: wangyue.wy
 * Modification: Create class.
 *****************************************************************************/
package org.lichsword.service;

import org.lichsword.service.json.JsonCacheCenter;
import org.lichsword.util.HttpUtil;
import org.lichsword.util.LogHelper;

/**
 * @author wangyue.wy
 * 
 */
public class WebClientService {

    public static final String TAG = WebClientService.class.getSimpleName();

    protected String retrieveContent(String url) {
        String result;
        JsonCacheCenter center = JsonCacheCenter.getInstance();
        if (center.hasCached(url)) {
            result = center.getCacheValue(url);
        } else {
            result = HttpUtil.getSourceCode(url);
            center.saveCacheValue(url, result);
        }

        LogHelper.w(TAG, "url = " + url);
        LogHelper.d(TAG, "result = " + result);

        return result;
    }

}
