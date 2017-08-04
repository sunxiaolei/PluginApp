package com.sun.xiaolei.plugindoubanmoment.net;

import com.sun.xiaolei.plugindoubanmoment.net.dto.ColumnPostDto;
import com.sun.xiaolei.plugindoubanmoment.net.dto.ColumnsDto;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by sunxl8 on 2017/8/4.
 */

public interface Api {

    @GET("columns")
    Flowable<ColumnsDto> getColumns();

    @GET("column/{columnId}/posts")
    Flowable<ColumnPostDto> getColumnPost(@Path("columnId") int id, @Query("count") int count);
}
