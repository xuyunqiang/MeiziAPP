package com.yunq.gankio.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.yunq.gankio.GankApp;
import com.yunq.gankio.R;
import com.yunq.gankio.data.entity.Gank;
import com.yunq.gankio.injection.component.DaggerGankDetailActivityComponent;
import com.yunq.gankio.injection.module.ActivityModule;
import com.yunq.gankio.presenter.GankDetailPresenter;
import com.yunq.gankio.presenter.view.IGankDetailView;
import com.yunq.gankio.ui.BaseSwipeRefreshActivity;
import com.yunq.gankio.ui.adapter.GankListAdapter;
import com.yunq.gankio.util.DateUtils;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class GirlDetailActivity extends BaseSwipeRefreshActivity implements IGankDetailView<Gank> {
    private static final String TAG = GirlDetailActivity.class.getSimpleName();
    private static final String EXTRA_BUNDLE_DATE = "BUNDLE_DATE";

    @Bind(R.id.rv_gank)
    RecyclerView mRvGank;

    GankListAdapter mAdapter;

    Date mDate;

    @Inject
    GankDetailPresenter mPresenter;

    public static void gotoGankActivity(Context context, Date date) {
        Intent intent = new Intent(context, GirlDetailActivity.class);
        intent.putExtra(EXTRA_BUNDLE_DATE, date);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_gank;
    }

    @Override
    protected int getMenuRes() {
        return R.menu.menu_gank;
    }

    @Override
    protected void initInjection() {
        DaggerGankDetailActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(GankApp.get(this).getComponent())
                .build()
                .inject(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInjection();
        mPresenter.attachView(this);
        mDate = (Date) getIntent().getSerializableExtra(EXTRA_BUNDLE_DATE);
        setTitle(DateUtils.toDate(mDate), true);

        initRecycleView();
        initData();

    }

    private void initRecycleView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvGank.setLayoutManager(layoutManager);
        mAdapter = new GankListAdapter();
        mRvGank.setAdapter(mAdapter);
    }

    private void initData() {
        mPresenter.getData(mDate);
    }

    @Override
    protected void onRefreshStarted() {
        initData();

    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void showErrorView(Throwable throwable) {
        throwable.printStackTrace();
    }


    @Override
    public void fillData(List<Gank> data) {
        mAdapter.updateWithClear(data);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onBackPressed() {
//        if (isRefreshing()) {
//            GankApp.get(this).getComponent().dataManager().cancelRequest();
//            hideRefresh();
//            return;
//        }
        super.onBackPressed();
    }
}
