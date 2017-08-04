package com.sun.xiaolei.plugindoubanmoment.net;

import com.orhanobut.logger.Logger;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sun on 2017/6/22.
 */

public class NetworkManager {

    public static final int HTTP_CONNECT_TIMEOUT = 5000;
    public static final int HTTP_WRITE_TIMEOUT = 10000;
    public static final int HTTP_READ_TIMEOUT = 10000;

    private static Retrofit commonClient;

    private static String lastUrl;

    public static Retrofit getCommonClient(String baseUrl) {
        if (commonClient == null || !baseUrl.equals(lastUrl)) {
            commonClient = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(getHttpClient())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            lastUrl = baseUrl;
        }
        return commonClient;
    }

    private static OkHttpClient getHttpClient() {

        Interceptor interceptor = chain -> {
            Request request = chain.request();
            Logger.d("Request==>" + request.toString());
            long t1 = System.nanoTime();
            Response response = chain.proceed(chain.request());
            long t2 = System.nanoTime();
            Logger.d(String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers(), response.code()));
            okhttp3.MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            Logger.d("Response==>" + content);
            return response.newBuilder()
                    .body(okhttp3.ResponseBody.create(mediaType, content))
                    .build();
        };

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(HTTP_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor);

        return builder.build();
    }

}
