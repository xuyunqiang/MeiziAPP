package com.yunq.gankio.presenter;

import com.yunq.gankio.data.DataManager;
import com.yunq.gankio.data.entity.Girl;
import com.yunq.gankio.presenter.view.IMainView;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by admin on 16/1/5.
 */
public class MainPresenter extends BasePresenter<IMainView> {

    private final DataManager mDataManager;

    private int mCurrentPage = 1;

    private static final int PAGE_SIZE = 10;


    //    public MainPresenter(Context context, IMainView view) {
//        super(context, view);
//    }
    @Inject
    public MainPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(IMainView view) {
        super.attachView(view);
    }

    public void resetCurrentPage() {
        mCurrentPage = 1;
    }

    public boolean shouldRefillGirls() {
        return mCurrentPage <= 2;
    }

    public void refillGirls(String tag) {

        addSubscription( mDataManager.getGirls(tag,PAGE_SIZE, mCurrentPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Girl>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showErrorView(e);
                        mView.getDataFinish();
                    }

                    @Override
                    public void onNext(List<Girl> girls) {
                        if (girls.isEmpty()) {
                            mView.showEmptyView();
                        } else if (girls.size() < PAGE_SIZE) {
                            mView.fillData(girls);
                            mView.hasNoMoreData();
                        } else if (girls.size() == PAGE_SIZE) {
                            mView.fillData(girls);
                            mCurrentPage++;
                        }

                        mView.getDataFinish();
                    }
                }));
    }

    public void getDataMore(String tag) {

        Subscription subscription = mDataManager.getGirls(tag,PAGE_SIZE, mCurrentPage)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                        //可以在接收事件之前，在主线程中执行初始化代码
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {

                    }
                })
                        //异步保存或者与本地数据库比对
                .doOnNext(new Action1<List<Girl>>() {
                    @Override
                    public void call(List<Girl> girls) {

                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Girl>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showErrorView(e);
                        mView.getDataFinish();

                    }

                    @Override
                    public void onNext(List<Girl> girls) {
                        if (girls.isEmpty()) {
                            mView.hasNoMoreData();
                        } else if (girls.size() < PAGE_SIZE) {
                            mView.appendMoreDataToView(girls);
                            mView.hasNoMoreData();
                        } else if (girls.size() == PAGE_SIZE) {
                            mView.appendMoreDataToView(girls);
                            mCurrentPage++;
                        }

                        mView.getDataFinish();
                    }
                });

        addSubscription(subscription);

    }

//    private PrettyGirlData createGirlInfoWith休息视频(PrettyGirlData girlData, 休息视频Data data) {
//
//        int restSize = data.results.size();
//
//        for (int i = 0; i < girlData.results.size(); i++) {
//            if (i <= restSize - 1) {
//                Girl girl = girlData.results.get(i);
//                girl.desc += " " + data.results.get(i).desc;
//            } else {
//                break;
//            }
//        }
//        return girlData;
//    }

}
