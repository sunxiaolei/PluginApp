package com.sun.xiaolei.plugin.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;

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
        helper.setText(R.id.tv_item_main, item.getName() + "--order:" + item.getOrder());
        helper.setOnClickListener(R.id.tv_item_main, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName(item.getPkgName(),
                        item.getPkgName() + ".MainActivity");
                intent.putExtra("data", "Data from main");
                mContext.startActivity(intent);
            }
        });
    }
}
