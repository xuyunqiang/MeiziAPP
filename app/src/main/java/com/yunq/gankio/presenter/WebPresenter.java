package com.yunq.gankio.presenter;

import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yunq.gankio.presenter.view.IWebView;

import javax.inject.Inject;

/**
 * Created by admin on 16/1/6.
 */
public class WebPresenter extends BasePresenter<IWebView> {

    @Inject
    public WebPresenter() {

    }

    public void setUpWebView(WebView webView) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        webView.setWebViewClient(new LoveClient());
    }

    public void loadUrl(WebView webView, String url) {
        webView.loadUrl(url);
    }

    private class LoveClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (TextUtils.isEmpty(url)) {
                return true;
            }
            if (Uri.parse(url).getHost().equals("github.com")) {
                return false;
            }
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mView.showRefresh();
        }

        @Override
        public void onPageCommitVisible(WebView view, String url) {
            super.onPageCommitVisible(view, url);
            mView.hideRefresh();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mView.hideRefresh();
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            mView.hideRefresh();
            mView.showLoadErrorMessage(error.toString());
        }
    }
}
