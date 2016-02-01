package com.yunq.gankio.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yunq.gankio.GankApp;
import com.yunq.gankio.R;
import com.yunq.gankio.injection.component.DaggerGirlFaceActivityComponent;
import com.yunq.gankio.injection.module.ActivityModule;
import com.yunq.gankio.presenter.GirlFacePresenter;
import com.yunq.gankio.presenter.view.IGirlFaceView;
import com.yunq.gankio.ui.BaseActivity;
import com.yunq.gankio.util.ToastUtils;

import javax.inject.Inject;

import butterknife.Bind;
import uk.co.senab.photoview.PhotoViewAttacher;

public class GirlFaceActivity extends BaseActivity implements IGirlFaceView {

    private static final String EXTRA_BUNDLE_URL = "BUNDLE_URL";
    private static final String EXTRA_BUNDLE_TITLE = "BUNDLE_TITLE";

    @Bind(R.id.iv_girl_detail)
    ImageView mIvGrilDetail;

    PhotoViewAttacher mAttacher;

    @Inject
    GirlFacePresenter mPresenter;

    public static void gotoWatchGirlDetail(Context context, String url, String title) {
        Intent intent = new Intent(context, GirlFaceActivity.class);
        intent.putExtra(EXTRA_BUNDLE_URL, url);
        intent.putExtra(EXTRA_BUNDLE_TITLE, title);
        context.startActivity(intent);
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_girl_face;
    }

    @Override
    protected int getMenuRes() {
        return R.menu.menu_girl_detail;
    }


    @Override
    protected void initInjection() {
        DaggerGirlFaceActivityComponent.builder()
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
        mAttacher = new PhotoViewAttacher(mIvGrilDetail);

        String title = getIntent().getStringExtra(EXTRA_BUNDLE_TITLE);
        setTitle(title, true);

        String url = getIntent().getStringExtra(EXTRA_BUNDLE_URL);
         mPresenter.loadGirl(url, mIvGrilDetail);
    }

    @Override
    public void saveSuccess(String message) {
        ToastUtils.showShort(this, message);
    }

    @Override
    public void showFailInfo(String error) {
        ToastUtils.showShort(this, error);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
             mPresenter.saveFace(getIntent().getStringExtra(EXTRA_BUNDLE_URL));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Picasso.with(this).cancelRequest(mIvGrilDetail);
        mPresenter.detachView();
    }


}
