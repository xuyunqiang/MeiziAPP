package com.yunq.gankio.injection.module;

import android.content.Context;

import com.yunq.gankio.injection.Activity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by admin on 16/1/25.
 */
@Module
public class ActivityModule {

    private final Context mContext;

    public ActivityModule(Context context) {
        mContext = context;
    }

    @Provides
    @Activity
    Context provideContext() {
        return mContext;
    }
}
