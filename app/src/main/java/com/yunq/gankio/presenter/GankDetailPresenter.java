package com.yunq.gankio.presenter;

import com.yunq.gankio.DataManager;
import com.yunq.gankio.data.entity.Gank;
import com.yunq.gankio.presenter.view.IGankDetailView;

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

    public void getData(String tag,Date date) {
        addSubscription(mDataManager.getGankData(tag,date)
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
                }));


    }

}
