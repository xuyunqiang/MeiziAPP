package com.yunq.gankio;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.yunq.gankio.injection.component.ApplicationComponent;
import com.yunq.gankio.injection.component.DaggerApplicationComponent;
import com.yunq.gankio.injection.module.ApplicationModule;


/**
 * Created by admin on 16/1/5.
 */
public class GankApp extends Application {
    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        //只有在调试模式下 才启用日志输出
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());

            Logger.init("Gank").hideThreadInfo().setMethodCount(0);
        } else {
            Logger.init("Gank").setLogLevel(LogLevel.NONE);
        }

    }

    public static GankApp get(Context context) {
        return (GankApp) context.getApplicationContext();
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
