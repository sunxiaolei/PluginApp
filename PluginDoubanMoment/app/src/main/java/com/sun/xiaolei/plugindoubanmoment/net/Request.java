package com.sun.xiaolei.plugindoubanmoment.net;

import com.sun.xiaolei.plugindoubanmoment.Constant;
import com.sun.xiaolei.plugindoubanmoment.net.dto.ColumnPostDto;
import com.sun.xiaolei.plugindoubanmoment.net.dto.ColumnsDto;

import io.reactivex.Flowable;

/**
 * Created by sunxl8 on 2017/8/4.
 */

public class Request {

    public static Flowable<ColumnsDto> getColumns() {
        return NetworkManager.getCommonClient(Constant.HOST_DOUBAN_MOMENT)
                .create(Api.class)
                .getColumns()
                .compose(SchedulersCompat.applyIoSchedulers());
    }

    public static Flowable<ColumnPostDto> getColumnPost(int id, int count) {
        return NetworkManager.getCommonClient(Constant.HOST_DOUBAN_MOMENT)
                .create(Api.class)
                .getColumnPost(id, count)
                .compose(SchedulersCompat.applyIoSchedulers());
    }
}
