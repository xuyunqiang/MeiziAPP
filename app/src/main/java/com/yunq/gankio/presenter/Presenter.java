package com.yunq.gankio.presenter;

import com.yunq.gankio.presenter.view.IBaseView;

/**
 * Created by admin on 16/1/25.
 */
public interface Presenter<V extends IBaseView> {

    void attachView(V view);

    void detachView();
}
