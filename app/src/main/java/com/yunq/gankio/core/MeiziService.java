package com.yunq.gankio.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.yunq.gankio.data.parse.GankData;
import com.yunq.gankio.data.parse.PrettyGirlData;
import com.yunq.gankio.data.parse.休息视频Data;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by admin on 16/1/25.
 */
public interface MeiziService {

    /**
     * 数据主机地址
     */
    String HOST = "http://gank.avosapps.com/api/";

    @GET("data/福利/{pageSize}/{page}")
    Observable<PrettyGirlData> getPrettyGirlData(@Path("pageSize") int pageSize, @Path("page") int page,@Query("TAG") String tag);

    @GET("data/休息视频/{pageSize}/{page}")
    Observable<休息视频Data> get休息视频Data(@Path("pageSize") int pageSize, @Path("page") int page,@Query("TAG") String tag);

    @GET("day/{year}/{month}/{day}")
    Observable<GankData> getGankData(@Path("year") int year, @Path("month") int month, @Path("day") int day,@Query("TAG") String tag);

    class Creator {
        public static MeiziService newGudongService(OkHttpClient client) {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").serializeNulls().create();

            Retrofit apiAdapter = new Retrofit.Builder()
                    .baseUrl(HOST)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build();

            return apiAdapter.create(MeiziService.class);
        }
    }

}
