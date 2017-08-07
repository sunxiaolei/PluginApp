package com.sun.xiaolei.plugintest1;

import android.app.Application;

import sunxl8.myutils.Utils;

/**
 * Created by sunxl8 on 2017/8/7.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
