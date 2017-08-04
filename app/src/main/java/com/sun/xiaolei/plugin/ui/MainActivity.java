package com.sun.xiaolei.plugin.ui;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.didi.virtualapk.PluginManager;
import com.jakewharton.rxbinding2.view.RxView;
import com.sun.xiaolei.plugin.utils.AssetsUtils;
import com.sun.xiaolei.plugin.R;
import com.sun.xiaolei.plugin.base.BaseActivity;
import com.sun.xiaolei.plugin.db.DatabaseHelper;
import com.sun.xiaolei.plugin.db.model.PluginModel;
import com.trello.rxlifecycle2.android.ActivityEvent;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.List;

import butterknife.BindView;

import static android.os.Environment.getExternalStorageDirectory;
import static com.sun.xiaolei.plugin.Constant.PLUGIN_PATH;

/**
 * Created by sunxl8 on 2017/8/2.
 */

public class MainActivity extends BaseActivity {

    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.rv_main)
    RecyclerView rvMain;

    private MainAdapter mAdapter;

    @Override
    protected int setContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        //添加插件
        RxView.clicks(ivAdd)
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(o -> {

                });
        rvMain.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter = new MainAdapter(this);
        rvMain.setAdapter(mAdapter);

        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(rvMain);

        mAdapter.enableDragItem(itemTouchHelper, R.id.tv_item_main, true);
        mAdapter.setOnItemDragListener(new OnItemDragListener() {

            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {

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

    /**
     * 获取插件列表
     */
    private void getPluginList() {
        List<PluginModel> test = DatabaseHelper.queryPluginList();
        if (test.size() == 0) {
            PluginModel pDoubanMoment = new PluginModel();
            pDoubanMoment.setName("豆瓣一刻");
            pDoubanMoment.setOrder(1);
            pDoubanMoment.setPkgName("com.sun.xiaolei.plugindoubanmoment");
            pDoubanMoment.setIcRes(R.drawable.ic_plugin_dbmoment);
            test.add(pDoubanMoment);
            DataSupport.saveAll(test);
            DatabaseHelper.queryPluginList();
        }
        for (int i = 0; i < test.size(); i++) {
            loadPlugin(test.get(i));
        }
        mAdapter.setNewData(test);
    }

    /**
     * 复制并加载
     *
     * @param plugin
     */
    private void loadPlugin(PluginModel plugin) {
        AssetsUtils.getInstance(this).copyAssetsToSD("plugins", PLUGIN_PATH).setFileOperateCallback(new AssetsUtils.FileOperateCallback() {
            @Override
            public void onSuccess() {
                File apk = new File(getExternalStorageDirectory(), PLUGIN_PATH + plugin.getPkgName() + ".apk");
                if (apk.exists()) {
                    try {
                        PluginManager.getInstance(MainActivity.this).loadPlugin(apk);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "no found plugin!!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(String error) {
                Toast.makeText(MainActivity.this, "copy failed: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
