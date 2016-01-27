package com.yunq.gankio;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.yunq.gankio.injection.component.ApplicationComponent;
import com.yunq.gankio.injection.component.DaggerApplicationComponent;
import com.yunq.gankio.injection.module.ApplicationModule;

import timber.log.Timber;


/**
 * Created by admin on 16/1/5.
 */
public class GankApp extends Application {

    private RefWatcher refWatcher;

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        refWatcher = LeakCanary.install(this);

        if (BuildConfig.DEBUG) {
            //警告在主线程中执行耗时操作
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());

            Timber.plant(new Timber.DebugTree());
        }

    }

    public static GankApp get(Context context) {
        return (GankApp) context.getApplicationContext();
    }

    public static RefWatcher getRefWatcher(Context context) {
        return ((GankApp) context.getApplicationContext()).refWatcher;
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }


}
