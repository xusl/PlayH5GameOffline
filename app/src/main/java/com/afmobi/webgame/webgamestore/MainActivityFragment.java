package com.afmobi.webgame.webgamestore;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private final static String TAG = "MainActivityFragment";

//    java.lang.IllegalArgumentException: Can only use lower 16 bits for requestCode
// 	at android.support.v4.app.BaseFragmentActivityGingerbread.checkForValidRequestCode(BaseFragmentActivityGingerbread.java:91)

    private final static int REQUEST_PERMISSION_SUCCESS = 1;
    private WebView mGameWebView;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        requestExternalStoragePermissions();
        View contentView = inflater.inflate(R.layout.fragment_main, container, false);
        mGameWebView = (WebView) contentView.findViewById(R.id.main_webview);
        mGameWebView.requestFocus();
        mGameWebView.setVerticalScrollBarEnabled(false);
        mGameWebView.setHorizontalScrollBarEnabled(false);

        mGameWebView.setWebViewClient(new WebViewClientImpl(getActivity().getApplicationContext()));
        mGameWebView.setWebChromeClient(new WebChromeClient());

        WebSettings gameWebSettings = mGameWebView.getSettings();
        Log.e(TAG, "cache mode" + String.valueOf(gameWebSettings.getCacheMode()));
        gameWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        gameWebSettings.setJavaScriptEnabled(true); //加上这句话才能使用javascript方法
        gameWebSettings.setDomStorageEnabled(true);

        gameWebSettings.setDatabaseEnabled(true);
        gameWebSettings.setAppCacheEnabled(true);

//        String cacheDirPath = getFilesDir().getAbsolutePath()
//                + APP_CACHE_DIRNAME;
//        Log.i("cachePath", cacheDirPath);
//        // 设置数据库缓存路径
//        mWebView.getSettings().setDatabasePath(cacheDirPath); // API 19 deprecated
//        mWebView.getSettings().setAppCachePath(cacheDirPath);
//
//        Log.i("databasepath", mWebView.getSettings().getDatabasePath());

        gameWebSettings.setAllowFileAccess(true);
        gameWebSettings.setAllowFileAccessFromFileURLs(true);
        gameWebSettings.setAllowContentAccess(true);

        gameWebSettings.setUseWideViewPort(true);
        gameWebSettings.setLoadWithOverviewMode(true);
        gameWebSettings.setBuiltInZoomControls(true);
        gameWebSettings.setSavePassword(false);
        gameWebSettings.setSaveFormData(false);
        gameWebSettings.setSupportZoom(false);

        return contentView;
    }

    private boolean canAccessExternalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    getContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    private void requestExternalStoragePermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (!canAccessExternalStorage())
                requestPermissions(new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_PERMISSION_SUCCESS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mGameWebView.loadUrl("file:///mnt/sdcard/99BallsEvo/index.html");
    }

    @Override
    public void onStart() {
        super.onStart();

        if (!canAccessExternalStorage())
            return;

        // 加载assets目录下的html页面
//         mGameWebView.loadUrl("file:///android_asset/99BallsEvo/index.html");
//        mGameWebView.loadDataWithBaseURL("file:///mnt/sdcard/99BallsEvo/", "", "text/html", "utf-8", null);
//        Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)
//        Environment.getExternalStorageDirectory().getPath();
        mGameWebView.loadUrl("file:///mnt/sdcard/99BallsEvo/index.html");
//        String cacheDirPath = getActivity().getFilesDir().getAbsolutePath() + APP_CACHE_DIRNAME;
//        mGameWebView.loadUrl("http://176.34.96.157:8500/BRICKZ/");
    }
}
