package com.yunq.gankio.ui;

import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.v4.widget.SwipeRefreshLayout;

import com.yunq.gankio.R;
import com.yunq.gankio.presenter.view.ISwipeRefreshView;

import butterknife.Bind;

/**
 * Created by admin on 16/1/5.
 */
public abstract class BaseSwipeRefreshActivity extends BaseActivity implements ISwipeRefreshView {

    @Bind(R.id.swipe_refresh_layout)
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSwipeLayout();
    }

    private void initSwipeLayout() {
        mSwipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (prepareRefresh()) {
                    onRefreshStarted();
                } else {
                    hideRefresh();
                }
            }
        });
    }

    protected boolean prepareRefresh() {
        return true;
    }

    protected abstract void onRefreshStarted();

    @Override
    public void hideRefresh() {
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }

    @Override
    public void showRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @CheckResult
    protected boolean isRefreshing() {
        return mSwipeRefreshLayout.isRefreshing();
    }

    @Override
    public void getDataFinish() {
        hideRefresh();
    }
}
