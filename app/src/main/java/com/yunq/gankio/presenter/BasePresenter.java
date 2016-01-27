package com.yunq.gankio.presenter;

import com.yunq.gankio.presenter.view.IBaseView;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by admin on 16/1/5.
 */
public class BasePresenter<T extends IBaseView> implements Presenter<T> {

    protected T mView;

    private CompositeSubscription mSubscriptions;

    @Override
    public void attachView(T view) {
        mView = view;
    }

    @Override
    public void detachView() {
        if (mSubscriptions != null && !mSubscriptions.isUnsubscribed()) {
            mSubscriptions.unsubscribe();
        }
     //   mView = null;
    }

    public T getView() {
        return mView;
    }

    public CompositeSubscription getSubscriptions() {
        if (mSubscriptions == null) {
            mSubscriptions = new CompositeSubscription();
        }
        return mSubscriptions;
    }

    public void addSubscription(Subscription subscription) {
        getSubscriptions().add(subscription);
    }



}
