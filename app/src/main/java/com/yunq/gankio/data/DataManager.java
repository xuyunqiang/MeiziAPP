package com.yunq.gankio.data;

import com.yunq.gankio.core.MeiziService;
import com.yunq.gankio.data.entity.Gank;
import com.yunq.gankio.data.entity.Girl;
import com.yunq.gankio.data.parse.GankData;
import com.yunq.gankio.data.parse.PrettyGirlData;
import com.yunq.gankio.data.parse.休息视频Data;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Model层数据管理类，所有的数据变换操作全部放到这个类中，
 * 但随着数据操作的增多，这个类会变得越来越臃肿，
 * 我们可以可以将每组相关的数据操作抽取出来放到另一个类中去管理
 */
@Singleton
public class DataManager {
    private final OkHttpClient mClient;

    private final MeiziService mMeiziService;

    @Inject
    public DataManager(OkHttpClient client) {
        mClient = client;
        mMeiziService = MeiziService.Creator.newGudongService(client);
    }


    public void cancelRequest() {
       mClient.dispatcher().cancelAll();
    }

    /**
     * MainActivity中获取所有Girls
     */
    public Observable<List<Girl>> getGirls(int pageSize, int currentPage) {
        return Observable.zip(mMeiziService.getPrettyGirlData(pageSize, currentPage),
                mMeiziService.get休息视频Data(pageSize, currentPage),
                new Func2<PrettyGirlData, 休息视频Data, PrettyGirlData>() {
                    @Override
                    public PrettyGirlData call(PrettyGirlData prettyGirlData, 休息视频Data 休息视频Data) {
                        return DataUtils.createGirlInfoWith休息视频(prettyGirlData, 休息视频Data);
                    }
                })
                .map(new Func1<PrettyGirlData, List<Girl>>() {
                    @Override
                    public List<Girl> call(PrettyGirlData girlData) {
                        return girlData.results;
                    }
                })
                .flatMap(new Func1<List<Girl>, Observable<Girl>>() {
                    @Override
                    public Observable<Girl> call(List<Girl> girls) {
                        return Observable.from(girls);
                    }
                })
                .toSortedList(new Func2<Girl, Girl, Integer>() {
                    @Override
                    public Integer call(Girl girl, Girl girl2) {
                        return girl2.publishedAt.compareTo(girl.publishedAt);
                    }
                });
    }

    /**
     * GankDetailActivity
     */
    public Observable<List<Gank>> getGankData(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return mMeiziService.getGankData(year, month, day)
                .map(new Func1<GankData, GankData.Result>() {
                    @Override
                    public GankData.Result call(GankData gankData) {
                        return gankData.results;
                    }
                })
                .map(new Func1<GankData.Result, List<Gank>>() {
                    @Override
                    public List<Gank> call(GankData.Result result) {
                        return DataUtils.addAllResults(result);
                    }
                });
    }

}
