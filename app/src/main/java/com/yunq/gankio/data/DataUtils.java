package com.yunq.gankio.data;

import com.yunq.gankio.data.parse.GankData;
import com.yunq.gankio.data.parse.PrettyGirlData;
import com.yunq.gankio.data.entity.Gank;
import com.yunq.gankio.data.entity.Girl;
import com.yunq.gankio.data.parse.休息视频Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 16/1/25.
 */
public class DataUtils {

    //MainActivity相关
    public static PrettyGirlData createGirlInfoWith休息视频(PrettyGirlData girlData, 休息视频Data data) {

        int restSize = data.results.size();

        for (int i = 0; i < girlData.results.size(); i++) {
            if (i <= restSize - 1) {
                Girl girl = girlData.results.get(i);
                girl.desc += " " + data.results.get(i).desc;
            } else {
                break;
            }
        }
        return girlData;
    }

    //GirlDetailActivity相关
    public static List<Gank> addAllResults(GankData.Result results) {
        List<Gank> mGankList = new ArrayList<>();
        if (results.androidList != null) mGankList.addAll(results.androidList);
        if (results.iOSList != null) mGankList.addAll(results.iOSList);
        if (results.appList != null) mGankList.addAll(results.appList);
        if (results.拓展资源List != null) mGankList.addAll(results.拓展资源List);
        if (results.瞎推荐List != null) mGankList.addAll(results.瞎推荐List);
        if (results.休息视频List != null) mGankList.addAll(0, results.休息视频List);
        return mGankList;
    }


}
