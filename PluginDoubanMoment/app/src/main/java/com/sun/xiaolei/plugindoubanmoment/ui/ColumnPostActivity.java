package com.sun.xiaolei.plugindoubanmoment.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sun.xiaolei.plugindoubanmoment.R;
import com.sun.xiaolei.plugindoubanmoment.base.BaseActivity;
import com.sun.xiaolei.plugindoubanmoment.net.Request;
import com.trello.rxlifecycle2.android.ActivityEvent;

import sunxl8.myutils.ToastUtils;

/**
 * Created by sunxl8 on 2017/8/4.
 */

public class ColumnPostActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private ColumnAdapter mAdapter;

    @Override
    protected int setContentViewId() {
        return R.layout.p_dm_activity_column;
    }

    @Override
    protected void init() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_columnpost);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ColumnAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        getColumnPost(getIntent().getIntExtra("columnId", 0));
    }

    public void getColumnPost(int id) {
        Request.getColumnPost(id, 10)
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(dto -> mAdapter.setNewData(dto.getPosts()),
                        throwable -> ToastUtils.shortShow(throwable.getMessage()));

    }

    public static void startThisActivity(Context context, int columnId) {
        Intent intent = new Intent(context, ColumnPostActivity.class);
        intent.putExtra("columnId", columnId);
        context.startActivity(intent);
    }
}
