package com.yunq.gankio.presenter;

import com.yunq.gankio.DataManager;
import com.yunq.gankio.data.entity.Gank;
import com.yunq.gankio.presenter.view.IGankDetailView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by admin on 16/1/6.
 */
public class GankDetailPresenter extends BasePresenter<IGankDetailView> {

    private final DataManager mDataManager;

    @Inject
    public GankDetailPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    public void getData(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH) + 1;
//        int day = calendar.get(Calendar.DAY_OF_MONTH);

        mDataManager.getGankData(date)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Gank>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Gank> list) {
                        if (list.isEmpty()) {
                            mView.showEmptyView();
                        } else {
                            mView.fillData(list);
                        }
                        mView.getDataFinish();
                    }
                });


    }

}
