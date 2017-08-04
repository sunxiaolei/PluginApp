package com.sun.xiaolei.plugindoubanmoment.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sun.xiaolei.plugindoubanmoment.R;
import com.sun.xiaolei.plugindoubanmoment.base.BaseActivity;
import com.sun.xiaolei.plugindoubanmoment.net.Request;
import com.trello.rxlifecycle2.android.ActivityEvent;

import butterknife.BindView;
import sunxl8.myutils.ToastUtils;

public class MainActivity extends BaseActivity {

    @BindView(R.id.rv_main)
    RecyclerView rvMain;

    //    private MainAdapter mAdapter;
    private DbMomentAdapter mAdapter;

    @Override
    protected int setContentViewId() {
        return R.layout.p_dm_activity_main;
    }

    @Override
    protected void init() {
        rvMain.setLayoutManager(new LinearLayoutManager(this));
//        mAdapter = new MainAdapter(this);
        getColumns();
    }

    private void getColumns() {
        Request.getColumns()
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(dto -> {
                    mAdapter = new DbMomentAdapter(this, dto.getColumns());
                    rvMain.setAdapter(mAdapter);
//                    mAdapter.setNewData(dto.getColumns());
                }, throwable -> {
                    ToastUtils.shortShow(throwable.getMessage());
                });
    }
}
