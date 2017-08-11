package com.sun.xiaolei.plugindoubanmoment.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sun.xiaolei.plugindoubanmoment.R;
import com.sun.xiaolei.plugindoubanmoment.base.BaseActivity;
import com.sun.xiaolei.plugindoubanmoment.net.dto.PostsBean;

import sunxl8.myutils.NetworkUtils;

/**
 * Created by sunxl8 on 2017/8/4.
 */

public class ArticleActivity extends BaseActivity {

    private WebView mWebView;

    private PostsBean bean;

    @Override
    protected int setContentViewId() {
        return R.layout.p_dm_activity_article;
    }

    @Override
    protected void init() {
        mWebView = (WebView) findViewById(R.id.web_moment_detail);
        bean = (PostsBean) getIntent().getSerializableExtra("bean");

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        if (NetworkUtils.isConnected()) {
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        String cacheDirPath = getFilesDir().getAbsolutePath() + "/netcache";
        settings.setAppCachePath(cacheDirPath);

        mWebView.canGoBack();
        mWebView.canGoForward();

        mWebView.loadUrl(bean.getUrl());
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showLoading();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dismissDialog();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                dismissDialog();
            }
        });

    }

    public static void startThisActivity(Context context, PostsBean bean) {
        Intent intent = new Intent(context, ArticleActivity.class);
        intent.putExtra("bean", bean);
        context.startActivity(intent);
    }
}
