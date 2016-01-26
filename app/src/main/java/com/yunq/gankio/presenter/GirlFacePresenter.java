package com.yunq.gankio.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;
import com.yunq.gankio.DataManager;
import com.yunq.gankio.R;
import com.yunq.gankio.presenter.view.IGirlFaceView;
import com.yunq.gankio.util.TaskUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.inject.Inject;

/**
 * Created by admin on 16/1/6.
 */
public class GirlFacePresenter extends BasePresenter<IGirlFaceView> {

    private final DataManager mDataManager;
    private Context mContext;

    @Inject
    public GirlFacePresenter(Context context,DataManager dataManager){
        mContext =  context;
        mDataManager = dataManager;
    }
    public void loadGirl(String url, ImageView imageView) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(mContext).load(url).into(imageView);
        }
    }

    public void saveFace(String url) {
        if (!TextUtils.isEmpty(url)) {
            String fileName = url.substring(url.lastIndexOf("/") + 1);
            saveImageToSdCard(mContext, url, fileName);
        }
    }

    private void saveImageToSdCard(final Context context, final String url, final String fileName) {
        TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, Boolean>() {
            @Override
            protected Boolean doInBackground(Object... params) {
                Bitmap bmp = null;
                try {
                    bmp = Picasso.with(context).load(url).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (bmp == null) {
                    return false;
                }

                //保存图片
                File appDir = new File(getSDPath(), "Meizhi");
                if (!appDir.exists()) {
                    if (appDir.mkdirs()) {
                        Logger.i("create suc");
                    } else {
                        Logger.i("create failed");
                    }
                }

                File file = new File(appDir, fileName);
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(file);
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }


                //把文件插入到系统图库
                try {
                    MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                //通知图库更新
                Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath()));
                context.sendBroadcast(scannerIntent);

                return true;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                String msg;
                if (result) {
                    File appDir = new File(Environment.getExternalStorageDirectory(), "Meizhi");
                    if (!appDir.exists()) {
                        appDir.mkdir();
                    }
                    msg = String.format(context.getString(R.string.picture_has_save_to),
                            appDir.getAbsolutePath());
                    mView.saveSuccess(msg);
                } else {
                    msg = context.getString(R.string.picture_save_fail);
                    mView.showFailInfo(msg);
                }
            }
        });
    }

    public String getSDPath() {
        return Environment.getExternalStorageDirectory().toString();
    }
}