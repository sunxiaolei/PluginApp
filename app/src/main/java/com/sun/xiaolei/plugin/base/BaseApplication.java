package com.sun.xiaolei.plugin.base;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.didi.virtualapk.PluginManager;

import org.litepal.LitePal;

import sunxl8.myutils.SPUtils;
import sunxl8.myutils.Utils;

/**
 * Created by sunxl8 on 2017/7/31.
 */

public class BaseApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        PluginManager.getInstance(base).init();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        LitePal.initialize(this);
        if (SPUtils.getInstance("SP_MAIN").getBoolean("mode", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
