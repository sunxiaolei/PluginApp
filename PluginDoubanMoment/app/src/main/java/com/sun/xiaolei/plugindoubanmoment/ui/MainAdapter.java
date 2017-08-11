package com.sun.xiaolei.plugindoubanmoment.ui;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sun.xiaolei.plugindoubanmoment.R;
import com.sun.xiaolei.plugindoubanmoment.net.dto.ColumnBean;

/**
 * Created by sunxl8 on 2017/8/4.
 */

public class MainAdapter extends BaseQuickAdapter<ColumnBean, BaseViewHolder> {

    private Context mContext;

    public MainAdapter(Context context) {
        super(R.layout.p2_item_main);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final ColumnBean item) {
        helper.setText(R.id.tv_item_columns, item.getName());
        Glide.with(mContext).load(item.getIcon()).into((ImageView) helper.getView(R.id.iv_item_columns));
        helper.setOnClickListener(R.id.layout_item_columns, v ->
                ColumnPostActivity.startThisActivity(mContext, item));
    }
}
