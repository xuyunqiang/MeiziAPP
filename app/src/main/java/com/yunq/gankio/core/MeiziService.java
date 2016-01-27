package com.yunq.gankio.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yunq.gankio.data.parse.GankData;
import com.yunq.gankio.data.parse.PrettyGirlData;
import com.yunq.gankio.data.parse.休息视频Data;

import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
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
    Observable<PrettyGirlData> getPrettyGirlData(@Path("pageSize") int pageSize, @Path("page") int page);

    @GET("data/休息视频/{pageSize}/{page}")
    Observable<休息视频Data> get休息视频Data(@Path("pageSize") int pageSize, @Path("page") int page);

    @GET("day/{year}/{month}/{day}")
    Observable<GankData> getGankData(@Path("year") int year, @Path("month") int month, @Path("day") int day);

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
