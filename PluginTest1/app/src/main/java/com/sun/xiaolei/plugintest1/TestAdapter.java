package com.sun.xiaolei.plugintest1;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import sunxl8.myutils.ToastUtils;

/**
 * Created by sunxl8 on 2017/3/6.
 */

public class TestAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context mContext;

    public TestAdapter(Context context, List<String> beanList) {
        super(R.layout.p1_item_test, beanList);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_item, item);
        helper.setOnClickListener(R.id.tv_item, v -> ToastUtils.shortShow("click"));
    }
}
