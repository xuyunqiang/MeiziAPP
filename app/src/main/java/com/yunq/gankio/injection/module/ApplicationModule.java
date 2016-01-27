package com.yunq.gankio.injection.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
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
    OkHttpClient provideOkHttpClient(){
        return new OkHttpClient();
    }


}
