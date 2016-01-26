package com.yunq.gankio.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import com.yunq.gankio.GankApp;
import com.yunq.gankio.R;
import com.yunq.gankio.injection.component.DaggerWebActivityComponent;
import com.yunq.gankio.injection.module.ActivityModule;
import com.yunq.gankio.presenter.WebPresenter;
import com.yunq.gankio.presenter.view.IWebView;
import com.yunq.gankio.ui.BaseSwipeRefreshActivity;
import com.yunq.gankio.util.AndroidUtils;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WebActivity extends BaseSwipeRefreshActivity implements IWebView {

    private static final String EXTRA_URL = "URL";
    private static final String EXTRA_TITLE = "TITLE";

    public static void gotoWebActivity(Context context, String url, String title) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(EXTRA_URL, url);
        intent.putExtra(EXTRA_TITLE, title);
        context.startActivity(intent);
    }

    @Bind(R.id.wb_content)
    WebView mWebView;

    @Inject
    WebPresenter mPresenter;

    @Override
    protected int getLayout() {
        return R.layout.activity_web;
    }

    @Override
    protected int getMenuRes() {
        return R.menu.menu_web;
    }

    @Override
    protected void initInjection() {
        DaggerWebActivityComponent.builder()
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
        String url = getIntent().getStringExtra(EXTRA_URL);
        String title = getIntent().getStringExtra(EXTRA_TITLE);

        if (!TextUtils.isEmpty(title)) {
            setTitle(title, true);
        }

        mPresenter.setUpWebView(mWebView);
        mPresenter.loadUrl(mWebView, url);
    }

    @Override
    protected void onRefreshStarted() {
        refresh();
    }

    private void refresh() {
        mWebView.reload();
    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void showErrorView(Throwable throwable) {
        throwable.printStackTrace();
    }


    @Override
    public void showLoadErrorMessage(String message) {
        Snackbar.make(mWebView, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        if (mWebView != null) {
            mWebView.destroy();
        }
        mPresenter.detachView();
        ButterKnife.unbind(this);
    }

    @Override
    protected void onPause() {
        if (mWebView != null) {
            mWebView.onPause();
        }
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_copy_url:
                String copyDone = getString(R.string.toast_copy_done);
                AndroidUtils.copyToClipBoard(this, mWebView.getUrl(), copyDone);
                return true;
            case R.id.action_open_url:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(mWebView.getUrl());
                intent.setData(uri);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(WebActivity.this, R.string.toast_open_fail, Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
