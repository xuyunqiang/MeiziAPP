package com.yunq.gankio.util;

import android.content.Context;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by admin on 16/1/21.
 */
public class HttpUtils {
    /**
     * 生成离线缓存
     */
    public static Interceptor getCacheInterceptor(final Context context) {
        return new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request request = chain.request();

//                String tag = request.url().queryParameter("TAG");
//
//                HttpUrl.Builder urlBuilder = request.url().newBuilder()
//                        .removeAllQueryParameters("TAG");
//
//                request = request.newBuilder()
//                        .url(urlBuilder.build())
//                        .tag(tag)
//                        .build();

                if (!NetworkUtil.isNetworkConnected(context)) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
             //   Log.e("response", request.httpUrl().toString());
                Response response = chain.proceed(request);
                if (NetworkUtil.isNetworkConnected(context)) {
                //    Log.e("response", "connected");
                    int maxAge = 60 * 60;
                    response.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();
                } else {
                 //   Log.e("response", "not connected");
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .build();
                }
              //  Log.e("response", "response");
                return response;


            }
        };
    }
}
