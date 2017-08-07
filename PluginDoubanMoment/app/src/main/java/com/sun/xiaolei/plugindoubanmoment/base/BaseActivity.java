package com.sun.xiaolei.plugindoubanmoment.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;


/**
 * Created by sunxl8 on 2017/8/2.
 */

public abstract class BaseActivity extends RxAppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentViewId());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    protected abstract int setContentViewId();

    protected abstract void init();

}
