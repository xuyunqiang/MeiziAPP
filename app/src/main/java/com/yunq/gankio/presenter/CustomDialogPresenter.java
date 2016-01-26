package com.yunq.gankio.presenter;


import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.webkit.WebView;

import com.yunq.gankio.R;
import com.yunq.gankio.presenter.view.ICustomDialog;
import com.yunq.gankio.ui.fragment.CustomWebViewDialog;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.inject.Inject;

/**
 * Created by admin on 16/1/6.
 */
public class CustomDialogPresenter extends BasePresenter<ICustomDialog> {


    private static final String KEY_UTF_8 = "UTF-8";

    private Context mContext;

    @Inject
    public CustomDialogPresenter(Context context) {
        mContext = context;
    }

    public AlertDialog makeDialog(Fragment fragment, View customView) {

        String dialogTitle = fragment.getArguments().getString(CustomWebViewDialog.EXTRA_DIALOG_TITLE);
        String htmlFileName = fragment.getArguments().getString(CustomWebViewDialog.EXTRA_HTML_FILE_NAME);
        int accentColor = fragment.getArguments().getInt(CustomWebViewDialog.EXTRA_ACCENT_COLOR);

        final WebView webView = (WebView) customView.findViewById(R.id.webView);
        webView.getSettings().setDefaultTextEncodingName(KEY_UTF_8);
        loadData(webView, htmlFileName, accentColor);

        AlertDialog dialog = new AlertDialog.Builder(mContext)
                .setTitle(dialogTitle)
                .setView(customView)
                .setPositiveButton(android.R.string.ok, null)
                .show();
        return dialog;
    }

    private void loadData(WebView webView, String htmlFileName, int accentColor) {
        try {
            StringBuilder buf = new StringBuilder();
            InputStream json = mContext.getAssets().open(htmlFileName);
            BufferedReader in = new BufferedReader(new InputStreamReader(json, KEY_UTF_8));
            String str;
            while ((str = in.readLine()) != null)
                buf.append(str);
            in.close();

            String formatLodString = buf.toString()
                    .replace("{style-placeholder}", "body { background-color: #ffffff; color: #000; }")
                    .replace("{link-color}", colorToHex(shiftColor(accentColor, true)))
                    .replace("{link-color-active}", colorToHex(accentColor));
            webView.loadDataWithBaseURL(null, formatLodString, "text/html", KEY_UTF_8, null);
        } catch (Throwable e) {
            webView.loadData("<h1>Unable to load</h1><p>" + e.getLocalizedMessage() + "</p>", "text/html", KEY_UTF_8);
        }
    }

    private String colorToHex(int color) {
        return Integer.toHexString(color).substring(2);
    }

    private int shiftColor(int color, boolean up) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= (up ? 1.1f : 0.9f); // value component
        return Color.HSVToColor(hsv);
    }
}
