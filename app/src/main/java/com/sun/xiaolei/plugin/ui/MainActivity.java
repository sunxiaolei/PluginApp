package com.sun.xiaolei.plugin.ui;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.didi.virtualapk.PluginManager;
import com.jakewharton.rxbinding2.view.RxView;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;
import com.sun.xiaolei.plugin.Constant;
import com.sun.xiaolei.plugin.utils.AssetsUtils;
import com.sun.xiaolei.plugin.R;
import com.sun.xiaolei.plugin.base.BaseActivity;
import com.sun.xiaolei.plugin.db.DatabaseHelper;
import com.sun.xiaolei.plugin.db.model.PluginModel;
import com.sun.xiaolei.plugin.utils.SchedulersCompat;
import com.sun.xiaolei.plugin.utils.SnackBarUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.android.ActivityEvent;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Flowable;
import sunxl8.myutils.SPUtils;

import static android.os.Environment.getExternalStorageDirectory;
import static com.sun.xiaolei.plugin.Constant.PLUGIN_PATH;

/**
 * Created by sunxl8 on 2017/8/2.
 */

public class MainActivity extends BaseActivity {

    @BindView(R.id.drawer_main)
    FlowingDrawer mDrawer;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.rv_main)
    RecyclerView rvMain;
    @BindView(R.id.switch_mode)
    SwitchCompat switchMode;

    private MainAdapter mAdapter;

    private MaterialDialog mPermissionDialog;

    private boolean isPluginLoad = false;

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

        mAdapter.enableDragItem(itemTouchHelper, R.id.layout_item_main, true);
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

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            new RxPermissions(this)
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(aBoolean -> {
                        if (aBoolean) {
                            PluginModel item = (PluginModel) adapter.getItem(position);
                            if (isPluginLoad) {
                                startPlugin(item);
                            } else {
                                loadAndStart(item);
                            }
                        } else {
                            mPermissionDialog.show();
                        }
                    });
        });

        switchMode.setChecked(SPUtils.getInstance(Constant.SP_NAME).getBoolean(Constant.SP_KEY_MODE, false));
        switchMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            SPUtils.getInstance(Constant.SP_NAME).put(Constant.SP_KEY_MODE, isChecked);
            recreate();
        });

        mPermissionDialog = new MaterialDialog.Builder(this)
                .content("跳转到设置去授予必需权限")
                .positiveText(R.string.sure)
                .negativeText(R.string.cancel)
                .onPositive((dialog, which) -> {
                    Intent localIntent = new Intent();
                    localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                    localIntent.setData(Uri.fromParts("package", getPackageName(), null));
                    startActivity(localIntent);
                })
                .build();

        getPluginList();

        new RxPermissions(this)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        copyPlugin(mAdapter.getData());
                    } else {
                        mPermissionDialog.show();
                    }
                });

    }

    private void loadAndStart(PluginModel item) {
        copyPlugin(mAdapter.getData(), item);
    }

    private void startPlugin(PluginModel item) {
        Intent intent = new Intent();
        intent.setClassName(item.getPkgName(), item.getPkgName() + ".ui.MainActivity");
        intent.putExtra("data", "Data from main");
        startActivity(intent);
    }

    /**
     * 获取插件列表
     */
    private void getPluginList() {
        List<PluginModel> test = DatabaseHelper.queryPluginList();
        if (test.size() == 0) {
            PluginModel pTest = new PluginModel();
            pTest.setName("Test");
            pTest.setOrder(1);
            pTest.setPkgName("com.sun.xiaolei.plugintest1");
            pTest.setIcRes(R.mipmap.ic_launcher);
            test.add(pTest);
            PluginModel pDoubanMoment = new PluginModel();
            pDoubanMoment.setName("豆瓣一刻");
            pDoubanMoment.setOrder(2);
            pDoubanMoment.setPkgName("com.sun.xiaolei.plugindoubanmoment");
            pDoubanMoment.setIcRes(R.drawable.ic_plugin_dbmoment);
            test.add(pDoubanMoment);
            DataSupport.saveAll(test);
        }
        mAdapter.setNewData(test);
    }

    private void copyPlugin(List<PluginModel> plugins) {
        AssetsUtils.getInstance(this).copyAssetsToSD("plugins", PLUGIN_PATH).setFileOperateCallback(new AssetsUtils.FileOperateCallback() {
            @Override
            public void onSuccess() {
                loadPlugins(plugins);
                isPluginLoad = true;

            }

            @Override
            public void onFailed(String error) {
                Toast.makeText(MainActivity.this, "copy failed: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void copyPlugin(List<PluginModel> plugins, PluginModel plugin) {
        AssetsUtils.getInstance(this).copyAssetsToSD("plugins", PLUGIN_PATH).setFileOperateCallback(new AssetsUtils.FileOperateCallback() {
            @Override
            public void onSuccess() {
                loadPlugins(plugins);
                isPluginLoad = true;
                startPlugin(plugin);
            }

            @Override
            public void onFailed(String error) {
                Toast.makeText(MainActivity.this, "copy failed: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadPlugins(List<PluginModel> plugins){
        for (int i = 0; i < plugins.size(); i++) {
            File apk = new File(getExternalStorageDirectory(), PLUGIN_PATH + plugins.get(i).getPkgName() + ".apk");
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
    }

    private boolean exitApp = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (exitApp) {
                finish();
            } else {
                SnackBarUtils.showShort(getWindow().getDecorView(), "再按一次退出应用程序", "退出", v -> finish());
                exitApp = true;
                Flowable.timer(2000, TimeUnit.MILLISECONDS)
                        .compose(SchedulersCompat.applyIoSchedulers())
                        .subscribe(aLong -> exitApp = false);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
