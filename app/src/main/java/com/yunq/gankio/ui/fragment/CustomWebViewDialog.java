package com.yunq.gankio.ui.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;

import com.yunq.gankio.GankApp;
import com.yunq.gankio.R;
import com.yunq.gankio.injection.component.DaggerFragmentComponent;
import com.yunq.gankio.injection.module.FragmentModule;
import com.yunq.gankio.presenter.CustomDialogPresenter;
import com.yunq.gankio.presenter.view.ICustomDialog;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomWebViewDialog extends DialogFragment implements ICustomDialog {

    public static final String EXTRA_DIALOG_TITLE = "DIALOG_TITLE";
    public static final String EXTRA_HTML_FILE_NAME = "HTML_FILE_NAME";
    public static final String EXTRA_ACCENT_COLOR = "ACCENT_COLOR";

    @Inject
    CustomDialogPresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInjection();
    }

    private void initInjection() {
        DaggerFragmentComponent.builder()
                .fragmentModule(new FragmentModule(this))
                .applicationComponent(GankApp.get(this.getActivity()).getComponent())
                .build()
                .inject(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View customView;
        try {
            customView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_webview, null);
        } catch (InflateException e) {
            throw new IllegalStateException("This device does not support Web Views.");
        }

        return mPresenter.makeDialog(this, customView);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
