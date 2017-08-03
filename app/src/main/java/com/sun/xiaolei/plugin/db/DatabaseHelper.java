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

    public static void updatePluginPos(List<PluginModel> list) {
        for (int i = 0; i < list.size(); i++) {
            PluginModel model = list.get(i);
            model.setOrder(i);
            model.saveOrUpdate("id = ?", String.valueOf(model.getId()));
        }
    }
}
