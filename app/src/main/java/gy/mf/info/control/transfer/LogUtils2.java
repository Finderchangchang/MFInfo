package gy.mf.info.control.transfer;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by bing.ma on 2017/4/20.
 */

public class LogUtils2 {
    public static final String tag = "12345";
    public static boolean isShow = true;

    public static void i(String string) {
        if (isShow) {

            Log.i(tag, string);
        }
    }

    public static void i(String tag, String string) {
        if (isShow) {

            Log.i(tag, string);
//            OkGo.get(Urls.URL_METHOD)     // 请求方式和请求url
//                    .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
//                    .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
//                    .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
//                    .execute(new StringCallback() {
//                        @Override
//                        public void onSuccess(String s, Call call, Response response) {
//                            // s 即为所需要的结果
//                            TotalModelMA typeModelTotalModelMA = new Gson().fromJson(s, TotalModelMA.class);
//                        }
//
//                        @Override
//                        public void onError(Call call, Response response, Exception e) {
//                            super.onError(call, response, e);
//                        }
//                    });
        }
    }

    void getOptionData2(ArrayList<TotalModelMA.TypeModel.Type> type_list) {
        for (TotalModelMA.TypeModel.Type type : type_list) {

        }
    }
}
