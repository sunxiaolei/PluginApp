package com.sun.xiaolei.plugindoubanmoment.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sun.xiaolei.plugindoubanmoment.R;
import com.sun.xiaolei.plugindoubanmoment.base.BaseActivity;
import com.sun.xiaolei.plugindoubanmoment.net.Request;
import com.trello.rxlifecycle2.android.ActivityEvent;

import sunxl8.myutils.ToastUtils;

public class MainActivity extends BaseActivity {

    private RecyclerView rvMain;

    private MainAdapter mAdapter;

    @Override
    protected int setContentViewId() {
        return R.layout.p2_activity_main;
    }

    @Override
    protected void init() {
        tvTitle.setText("豆瓣一刻");
        rvMain = (RecyclerView) findViewById(R.id.rv_main);
        rvMain.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MainAdapter(this);
        rvMain.setAdapter(mAdapter);
        getColumns();
    }

    private void getColumns() {
        showLoading();
        Request.getColumns()
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(dto -> {
                            dismissDialog();
                            mAdapter.setNewData(dto.getColumns());
                        },
                        throwable -> {
                            dismissDialog();
                            ToastUtils.shortShow(throwable.getMessage());
                        });
    }
}
