package com.yunq.gankio.presenter.view;

/**
 * Created by admin on 16/1/5.
 */
public interface ISwipeRefreshView extends IBaseView {

    void getDataFinish();

    void showEmptyView();

    void showErrorView(Throwable throwable);

    void showRefresh();

    void hideRefresh();
}
