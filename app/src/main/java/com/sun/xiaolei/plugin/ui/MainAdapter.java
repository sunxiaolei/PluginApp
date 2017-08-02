package com.sun.xiaolei.plugin.ui;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sun.xiaolei.plugin.R;
import com.sun.xiaolei.plugin.db.model.PluginModel;

/**
 * Created by sunxl8 on 2017/8/2.
 */

public class MainAdapter extends BaseItemDraggableAdapter<PluginModel, BaseViewHolder> {


    public MainAdapter() {
        super(R.layout.item_main, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, PluginModel item) {
        helper.setText(R.id.tv_item_main, item.getName());
    }
}
