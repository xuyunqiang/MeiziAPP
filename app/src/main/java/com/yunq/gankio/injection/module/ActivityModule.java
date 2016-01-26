package com.yunq.gankio.injection.module;

import dagger.Module;
import dagger.Provides;

/**
 * Created by admin on 16/1/25.
 */
@Module
public class ActivityModule {

    private android.app.Activity mActivity;

    public ActivityModule(android.app.Activity activity) {
        mActivity = activity;
    }

    @Provides
    android.app.Activity provideActivity() {
        return mActivity;
    }

//    @Provides
//    //@ActivityContext
//    Context provideContext() {
//        return mActivity;
//    }
}
