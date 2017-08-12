package com.sun.xiaolei.plugin.ui;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sun.xiaolei.plugin.R;
import com.sun.xiaolei.plugin.db.model.PluginModel;

/**
 * Created by sunxl8 on 2017/8/2.
 */

public class MainAdapter extends BaseItemDraggableAdapter<PluginModel, BaseViewHolder> {


    private Context mContext;

    public MainAdapter(Context context) {
        super(R.layout.item_main, null);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, PluginModel item) {
        helper.setText(R.id.tv_item_main, item.getName());
        if (item.getIcRes() != 0) {
            Glide.with(mContext).load(item.getIcRes()).into((ImageView) helper.getView(R.id.iv_item_main));
        } else {
            Glide.with(mContext).load(item.getIcUrl()).into((ImageView) helper.getView(R.id.iv_item_main));
        }
        helper.addOnClickListener(R.id.layout_item_main);
    }
}
