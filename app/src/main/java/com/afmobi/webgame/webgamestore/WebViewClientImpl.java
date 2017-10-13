package com.afmobi.webgame.webgamestore;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static android.content.ContentValues.TAG;

/**
 * Created by louis on 17-10-12.
 */

public class WebViewClientImpl extends WebViewClient {
    private final static String TAG = "WebViewClient";

    private Context activity = null;
    private UrlCache urlCache = null;

    public WebViewClientImpl(Context activity) {
        this.activity = activity;
        this.urlCache = new UrlCache(activity);
        this.urlCache.register("http://www.csdn.net/", "cache",
                "text/html", "UTF-8", 5 * UrlCache.ONE_MINUTE);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if(url.indexOf("csdn.net") > -1 ) return false;

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        activity.startActivity(intent);
        return true;
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        Log.d(TAG,"webResourceResponse ==== "+ url);
        return this.urlCache.load(url);
    }

}
