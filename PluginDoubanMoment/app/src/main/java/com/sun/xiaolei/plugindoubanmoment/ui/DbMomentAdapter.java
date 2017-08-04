package com.sun.xiaolei.plugindoubanmoment.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.sun.xiaolei.plugindoubanmoment.R;
import com.sun.xiaolei.plugindoubanmoment.net.dto.ColumnBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunxl8 on 2017/3/6.
 */

public class DbMomentAdapter extends RecyclerView.Adapter<DbMomentAdapter.ViewHolder> {

    private Context mContext;
    private List<ColumnBean> mBeanList;

    public DbMomentAdapter(Context context, List<ColumnBean> beanList) {
        mContext = context;
        mBeanList = beanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.p_dm_item_main, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ColumnBean bean = mBeanList.get(position);
        Glide.with(mContext).load(bean.getIcon()).into(holder.ivIcon);
        holder.tvTitle.setText(bean.getName());
        holder.layoutItem.setOnClickListener(v -> ColumnPostActivity.startThisActivity(mContext, bean.getId()));
    }

    @Override
    public int getItemCount() {
        return mBeanList == null ? 0 : mBeanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        //        @BindView(R.id.layout_item_columns)
        LinearLayout layoutItem;
        //        @BindView(R.id.iv_item_columns)
        ImageView ivIcon;
        //        @BindView(R.id.tv_item_columns)
        TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            layoutItem = (LinearLayout) itemView.findViewById(R.id.layout_item_columns);
            ivIcon = (ImageView) itemView.findViewById(R.id.iv_item_columns);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_item_columns);
        }
    }
}
