package com.yunq.gankio.presenter.view;

import com.yunq.gankio.data.entity.Soul;

import java.util.List;

/**
 * Created by admin on 16/1/5.
 */
public interface IMainView<T extends Soul> extends ISwipeRefreshView {

    void fillData(List<T> data);

    void appendMoreDataToView(List<T> data);

    void hasNoMoreData();


}
