package com.yunq.gankio.util;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.yunq.gankio.ui.fragment.CustomWebViewDialog;

/**
 * Created by admin on 16/1/5.
 */
public class DialogUtils {


    public static void showCustomDialog(Context context, FragmentManager fragmentManager, String dialogTitle, String htmlFileName, String tag) {
        int accentColor = AndroidUtils.getAccentColor(context);
//        CustomDialogPresenter.create(dialogTitle, htmlFileName, accentColor)
//                .show(fragmentManager, tag);
        CustomWebViewDialog dialog = new CustomWebViewDialog();
        Bundle args = new Bundle();
        args.putString(CustomWebViewDialog.EXTRA_DIALOG_TITLE, dialogTitle);
        args.putString(CustomWebViewDialog.EXTRA_HTML_FILE_NAME, htmlFileName);
        args.putInt(CustomWebViewDialog.EXTRA_ACCENT_COLOR, accentColor);
        dialog.setArguments(args);

        dialog.show(fragmentManager, tag);
    }


}
