package com.sun.xiaolei.plugin.base;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.didi.virtualapk.PluginManager;
import com.sun.xiaolei.plugin.Constant;

import org.litepal.LitePal;

import sunxl8.myutils.SPUtils;
import sunxl8.myutils.Utils;

/**
 * Created by sunxl8 on 2017/7/31.
 */

public class BaseApplication extends Application {

    public static Context mContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mContext = base;
        PluginManager.getInstance(base).init();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        LitePal.initialize(this);
        if (SPUtils.getInstance(Constant.SP_NAME).getBoolean(Constant.SP_KEY_MODE, false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
