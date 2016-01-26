package com.yunq.gankio.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CheckResult;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.view.View;

import com.yunq.gankio.GankApp;
import com.yunq.gankio.R;
import com.yunq.gankio.data.entity.Girl;
import com.yunq.gankio.injection.component.DaggerMainActivityComponent;
import com.yunq.gankio.injection.module.ActivityModule;
import com.yunq.gankio.presenter.MainPresenter;
import com.yunq.gankio.presenter.view.IMainView;
import com.yunq.gankio.ui.BaseSwipeRefreshActivity;
import com.yunq.gankio.ui.adapter.MainListAdapter;
import com.yunq.gankio.util.DialogUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;


public class MainActivity extends BaseSwipeRefreshActivity implements IMainView<Girl>, MainListAdapter.IClickItem {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.rcv_index_content)
    RecyclerView mRcvIndexContent;

    private MainListAdapter mAdapter;
    private boolean mIsFirstTimeTouchBottom = true;
    private boolean mHasMoreData = true;

    @Inject
    MainPresenter mPresenter;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected int getMenuRes() {
        return R.menu.menu_main;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInjection();
        mPresenter.attachView(this);
        initRecycleView();

    }

    @Override
    protected void initInjection() {
        DaggerMainActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(GankApp.get(this).getComponent())
                .build()
                .inject(this);
    }

    private void initRecycleView() {
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRcvIndexContent.setLayoutManager(layoutManager);
        mAdapter = new MainListAdapter(this);
        mAdapter.setIClickItem(this);
        mRcvIndexContent.setAdapter(mAdapter);

        mRcvIndexContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isBottom = layoutManager.findLastCompletelyVisibleItemPositions(new int[2])[1]
                        >= mAdapter.getItemCount() - 4;
                if (!mSwipeRefreshLayout.isRefreshing() && isBottom && mHasMoreData) {
                    if (!mIsFirstTimeTouchBottom) {
                        showRefresh();
                        mPresenter.getDataMore(TAG);
                    } else {
                        mIsFirstTimeTouchBottom = false;
                    }
                }
            }
        });
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showRefresh();
            }
        }, 558);

        mPresenter.refillGirls(TAG);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_github_tending:
                String url = getString(R.string.url_github_trending);
                String title = getString(R.string.action_github_trending);
                WebActivity.gotoWebActivity(MainActivity.this, url, title);
                break;
            case R.id.action_about:
                DialogUtils.showCustomDialog(this, getSupportFragmentManager(), getString(R.string.action_about), "about.html", "about");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    @CheckResult
    protected boolean prepareRefresh() {
        if (mPresenter.shouldRefillGirls()) {
            mPresenter.resetCurrentPage();
            if (!isRefreshing()) {
                showRefresh();
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void fillData(List<Girl> data) {
        mAdapter.updateWithClear(data);
    }

    @Override
    public void appendMoreDataToView(List<Girl> data) {
        mAdapter.update(data);
    }

    @Override
    public void hasNoMoreData() {
        mHasMoreData = false;
        Snackbar.make(mRcvIndexContent, R.string.no_more_girls, Snackbar.LENGTH_LONG)
                .setAction(R.string.action_to_top, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mRcvIndexContent.getLayoutManager().smoothScrollToPosition(mRcvIndexContent, null, 0);
                    }
                }).show();
    }

    @Override
    public void showEmptyView() {
        Snackbar.make(mRcvIndexContent, R.string.empty_data_of_girls, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorView(Throwable throwable) {
        throwable.printStackTrace();

        final Snackbar errorSnack = Snackbar.make(mRcvIndexContent, R.string.error_index_load, Snackbar.LENGTH_INDEFINITE);
        errorSnack.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorSnack.dismiss();
                onRefreshStarted();
            }
        });

        errorSnack.show();
    }

    @Override
    protected void onRefreshStarted() {
        mPresenter.refillGirls(TAG);
    }


    @Override
    public void onClickPhoto(int position, View view) {
        Girl clickGirl = mAdapter.getGirl(position);
        if (clickGirl != null) {
            String[] array = clickGirl.desc.split(" ");
            String title = getString(R.string.app_name);
            if (array.length >= 2) {
                title = array[1];
            }
            GirlFaceActivity.gotoWatchGirlDetail(this, clickGirl.url, title);
        }
    }

    @Override
    public void onClickDesc(int position, View view) {
        Girl clickGirl = mAdapter.getGirl(position);
        if (clickGirl != null) {
            GirlDetailActivity.gotoGankActivity(this, clickGirl.publishedAt);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
