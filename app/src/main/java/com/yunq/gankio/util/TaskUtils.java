package com.yunq.gankio.util;

import android.os.AsyncTask;
import android.os.Build;

/**
 * Created by admin on 16/1/5.
 */
public class TaskUtils {

    @SafeVarargs
    public static <Params, Progress, Result> void executeAsyncTask(
            AsyncTask<Params, Progress, Result> task, Params... params) {
        if (Build.VERSION.SDK_INT >= 11) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        } else {
            task.execute(params);
        }

    }
}
