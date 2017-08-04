package com.sun.xiaolei.plugindoubanmoment.ui;

import android.content.Context;
import android.content.Intent;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.sun.xiaolei.plugindoubanmoment.R;
import com.sun.xiaolei.plugindoubanmoment.base.BaseActivity;
import com.sun.xiaolei.plugindoubanmoment.net.dto.PostsBean;

import butterknife.BindView;
import sunxl8.myutils.NetworkUtils;

/**
 * Created by sunxl8 on 2017/8/4.
 */

public class ArticleActivity extends BaseActivity {

    @BindView(R.id.web_moment_detail)
    WebView mWebView;

    private PostsBean bean;

    @Override
    protected int setContentViewId() {
        return R.layout.p_dm_activity_article;
    }

    @Override
    protected void init() {
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
    }

    public static void startThisActivity(Context context, PostsBean bean) {
        Intent intent = new Intent(context, ArticleActivity.class);
        intent.putExtra("bean", bean);
        context.startActivity(intent);
    }
}
