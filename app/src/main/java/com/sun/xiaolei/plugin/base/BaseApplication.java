package com.sun.xiaolei.plugin.base;

import android.app.Application;
import android.content.Context;

import com.didi.virtualapk.PluginManager;

import org.litepal.LitePal;

import sunxl8.myutils.Utils;

/**
 * Created by sunxl8 on 2017/7/31.
 */

public class BaseApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        PluginManager.getInstance(base).init();
        Utils.init(this);
        LitePal.initialize(this);
    }
}
