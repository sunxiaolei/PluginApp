package com.sun.xiaolei.plugindoubanmoment.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.sun.xiaolei.plugindoubanmoment.R;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.wang.avi.indicators.BallBeatIndicator;

import sunxl8.library.swipeback.SwipeBackActivityHelper;
import sunxl8.library.swipeback.SwipeBackLayout;
import sunxl8.library.swipeback.Utils;
import xiaolei.library.LoadingDialog;


/**
 * Created by sunxl8 on 2017/8/2.
 */

public abstract class BaseActivity extends RxAppCompatActivity {

    private LoadingDialog mLoadingDialog;

    protected abstract int setContentViewId();

    protected abstract void init();

    private SwipeBackActivityHelper mHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mHelper = new SwipeBackActivityHelper(this);
        this.mHelper.onActivityCreate();
        setContentView(setContentViewId());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.mHelper.onPostCreate();
    }

    public View findViewById(int id) {
        View v = super.findViewById(id);
        return v == null && this.mHelper != null ? this.mHelper.findViewById(id) : v;
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return this.mHelper.getSwipeBackLayout();
    }

    public void setSwipeBackEnable(boolean enable) {
        this.getSwipeBackLayout().setEnableGesture(enable);
    }

    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        this.getSwipeBackLayout().scrollToFinishActivity();
    }

    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog.Builder(this)
                    .setIndicator(new BallBeatIndicator())
                    .setIndicatorColor(getResources().getColor(R.color.colorAccent))
                    .create();
        }
        mLoadingDialog.show();
    }

    public void dismissDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

}
