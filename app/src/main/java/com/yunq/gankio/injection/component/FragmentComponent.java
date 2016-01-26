package com.yunq.gankio.injection.component;

import android.content.Context;

import com.yunq.gankio.injection.Fragment;
import com.yunq.gankio.injection.module.FragmentModule;
import com.yunq.gankio.ui.fragment.CustomWebViewDialog;

import dagger.Component;

/**
 * Created by admin on 16/1/25.
 */
@Fragment
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(CustomWebViewDialog dialog);

    Context context();
}
