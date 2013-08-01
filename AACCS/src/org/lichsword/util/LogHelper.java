package org.lichsword.util;

import java.util.ArrayList;
import java.util.List;

public class LogHelper {

    private static String TAG = LogHelper.class.getSimpleName();

    // TODO 发布前必须手动关闭
    private static boolean isNeedLog = false;

    // TODO 发布前必须手动关闭
    private static boolean enableTag = false;

    // TODO
    private static List<String> tags = new ArrayList<String>();
    static {

        // tags.add(CoreApplication.TAG);
        // tags.add(ImageUtil.TAG);
        // tags.add(BaseListActivity.TAG);
        // tags.add(BaseListLoadTask.TAG);
        // tags.add(BaseDetailMediaActivity.TAG);
        // tags.add(HTML5WebActivity.TAG);
        // tags.add(HTML5WebView.TAG);
        // tags.add(ImageManager.TAG);
        // tags.add(Movie.TAG);
        // tags.add(HorizontalScrollListView.TAG);
        // tags.add(EpisodeDetailActivity.TAG);
        // tags.add(GridPagerAdapter.TAG);
        // tags.add(GridViewFragment.TAG);
        // tags.add(DetailFunnyActivity.TAG);
        // tags.add(BaseCategoryListActivity.TAG);
        // tags.add(WebClientService.TAG);
        // tags.add(HomeActivity.TAG);
        // tags.add(HttpClient.TAG);
        // tags.add(JsonClientService.TAG);
        // tags.add(SettingActivity.TAG);
        // tags.add(HomeChannelMovieHorizontalScrollListView.TAG);
        // tags.add(UMengTrackHost_backup.TAG);
        // tags.add(UMengTrackHost.TAG);
    }

    public static void i(String msg) {
        i(TAG, msg);
    }

    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void w(String msg) {
        w(TAG, msg);
    }

    public static void i(String tag, String msg) {
        if (enableTag) {
            if (isNeedLog && tags.contains(tag)) {
                System.out.println(tag + msg);
            }
        } else {
            if (isNeedLog) {
                System.out.println(tag + msg);
            }
        }
    }

    public static void e(String tag, String msg) {
        if (enableTag) {
            if (isNeedLog && tags.contains(tag)) {
                System.out.println(tag + msg);
            }
        } else {
            if (isNeedLog) {
                System.out.println(tag + msg);
            }
        }
    }

    public static void d(String tag, String msg) {
        if (enableTag) {
            if (isNeedLog && tags.contains(tag)) {
                System.out.println(tag + msg);
            }
        } else {
            if (isNeedLog) {
                System.out.println(tag + msg);
            }
        }
    }

    public static void w(String tag, String msg) {
        if (enableTag) {
            if (isNeedLog && tags.contains(tag)) {
                System.out.println(tag + msg);
            }
        } else {
            if (isNeedLog) {
                System.out.println(tag + msg);
            }
        }
    }

    public static void v(String tag, String msg) {
        if (enableTag) {

            if (isNeedLog && tags.contains(tag)) {
                System.out.println(tag + msg);
            }
        } else {
            if (isNeedLog) {
                System.out.println(tag + msg);
            }
        }
    }
}
