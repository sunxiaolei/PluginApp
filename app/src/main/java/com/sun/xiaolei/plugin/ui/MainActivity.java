package com.sun.xiaolei.plugin.ui;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.sun.xiaolei.plugin.R;
import com.sun.xiaolei.plugin.base.BaseActivity;
import com.sun.xiaolei.plugin.db.DatabaseHelper;
import com.sun.xiaolei.plugin.db.model.PluginModel;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;

/**
 * Created by sunxl8 on 2017/8/2.
 */

public class MainActivity extends BaseActivity {

    @BindView(R.id.rv_main)
    RecyclerView rvMain;

    private MainAdapter mAdapter;

    @Override
    protected int setContentViewId() {
        return R.layout.activity_main;
    }

    int from;

    @Override
    protected void init() {
        rvMain.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter = new MainAdapter();
        rvMain.setAdapter(mAdapter);

        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(rvMain);

        mAdapter.enableDragItem(itemTouchHelper, R.id.tv_item_main, true);
        mAdapter.setOnItemDragListener(new OnItemDragListener() {

            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
                from = pos;
            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {

            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
                DatabaseHelper.updatePluginPos(mAdapter.getData());
            }
        });

        getPluginList();

    }

    private void getPluginList() {
        List<PluginModel> test = DatabaseHelper.queryPluginList();
        if (test.size() == 0) {
            for (int i = 0; i < 8; i++) {
                PluginModel model = new PluginModel();
                model.setName("==" + i + "==");
                model.setOrder(i);
                test.add(model);
            }
            DataSupport.saveAll(test);
            DatabaseHelper.queryPluginList();
        }
        mAdapter.setNewData(test);
    }
}
