package com.yunq.gankio.presenter.view;

import com.yunq.gankio.data.entity.Soul;

import java.util.List;

/**
 * Created by admin on 16/1/6.
 */
public interface IGankDetailView<T extends Soul> extends ISwipeRefreshView {

    void fillData(List<T> data);
}
