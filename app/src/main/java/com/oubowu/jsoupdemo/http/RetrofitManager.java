package com.oubowu.jsoupdemo.http;

import android.support.annotation.NonNull;

import com.oubowu.jsoupdemo.http.service.Services;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;

/**
 * Created by Oubowu on 2016/9/27 0027 17:40.
 */
public class RetrofitManager {

    public static final String HOST = "http://www.zhuangbi.info/";
    private final Services mServices;

    private RetrofitManager() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder().retryOnConnectionFailure(true).connectTimeout(30, TimeUnit.SECONDS).build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(HOST).client(okHttpClient).addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();

        mServices = retrofit.create(Services.class);

    }

    public static final RetrofitManager getInstance() {
        return RetrofitManagerHolder.INSTANCE;
    }

    public Observable<String> getNewPhotoList(@NonNull int page) {
        return mServices.getNewPhotoList(page);
    }

    public Observable<String> getHotPhotoList(@NonNull int page) {
        return mServices.getHotPhotoList(page);
    }

    public Observable<String> searchPhoto(String keywords) {
        return mServices.searchPhoto(1, keywords);
    }

    public Observable<String> getBuilderPhotoList() {
        return mServices.getBuilderPhotoList();
    }

    public Observable<ResponseBody> downloadPhotoZip(@NonNull String fileUrl){
        return mServices.downloadPhotoZip(fileUrl);
    }

    private static class RetrofitManagerHolder {
        public static final RetrofitManager INSTANCE = new RetrofitManager();
    }

}
