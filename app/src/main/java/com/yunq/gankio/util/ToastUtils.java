package com.yunq.gankio.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by admin on 16/1/5.
 */
public class ToastUtils {

    private ToastUtils(){

    }

    public static void showShort(Context context,int resId){
        Toast.makeText(context,resId,Toast.LENGTH_SHORT).show();
    }

    public static void showShort(Context context,String message){
        Toast.makeText( context,message,Toast.LENGTH_SHORT).show();
    }



}
