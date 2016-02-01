package com.yunq.gankio.injection.module;

import android.app.Application;

import com.yunq.gankio.util.HttpUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by admin on 16/1/25.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

//    @Provides
//   // @ApplicationContext
//    Context provideContext() {
//        return mApplication;
//    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cache(new Cache(mApplication.getCacheDir(), 10 * 1024 * 1024));
        builder.addInterceptor(HttpUtils.getCacheInterceptor(mApplication));
        return builder.build();
    }


}
