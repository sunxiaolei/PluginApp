package com.sun.xiaolei.plugin.db;

import com.sun.xiaolei.plugin.db.model.PluginModel;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by sunxl8 on 2017/8/2.
 */

public class DatabaseHelper {

    public static List<PluginModel> queryPluginList() {
        return DataSupport
                .order("order asc")
                .find(PluginModel.class);
    }

    public static void swopPlugin(PluginModel mFrom, PluginModel mTo) {
        int orderFrom = mFrom.getOrder();
        int orderTo = mTo.getOrder();
        mFrom.setOrder(orderTo);
        mTo.setOrder(orderFrom);
        mFrom.saveOrUpdate();
        mTo.saveOrUpdate();
    }
}
