package com.yunq.gankio.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import com.yunq.gankio.R;

/**
 * Created by admin on 16/1/5.
 */
public class AndroidUtils {

    public static void copyToClipBoard(Context context, String text, String success) {
        ClipData clipData = ClipData.newPlainText("meizhi copy", text);
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(clipData);
        ToastUtils.showShort(context,success);
    }

    /**
     * 获取主题强调色
     *
     * @param context
     * @return
     */
    public static int getAccentColor(Context context) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }
}
