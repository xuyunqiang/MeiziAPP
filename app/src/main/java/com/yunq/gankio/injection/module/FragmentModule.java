package com.yunq.gankio.injection.module;

import android.content.Context;
import android.support.v4.app.Fragment;

import dagger.Module;
import dagger.Provides;
/**
 * Created by admin on 16/1/25.
 */
@Module
public class FragmentModule {
    private final Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }

    @Provides
    @com.yunq.gankio.injection.Fragment
    Context provideContext() {
        return mFragment.getActivity();
    }
}
