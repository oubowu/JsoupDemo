package com.oubowu.jsoupdemo.http.service;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Oubowu on 2016/9/27 0027 17:41.
 */
public interface Services {

    /**
     * 获取最新的表情包
     *
     * @param page
     * @return
     */
    // http://www.zhuangbi.info/?page=2
    @GET(" ")
    Observable<String> getNewPhotoList(@Query("page") int page);


    /**
     * 获取最热的表情包
     *
     * @param page
     * @return
     */
    // http://www.zhuangbi.info/hot?page=2
    @GET("hot")
    Observable<String> getHotPhotoList(@Query("page") int page);

    /**
     * 查找表情包
     *
     * @param page
     * @param keywords
     * @return
     */
    // http://www.zhuangbi.info/search?mini=1&q=%E6%88%91
    @GET("search")
    Observable<String> searchPhoto(@Query("mini") int page, @Query("q") String keywords);

    /**
     * 获取用于安成表情包的图片
     *
     * @return
     */
    // www.zhuangbi.info/builder
    @GET("builder")
    Observable<String> getBuilderPhotoList();

    /**
     * 下载表情包压缩包
     *
     * @param fileUrl
     * @return
     */
    @Streaming // 声明@Streaming并不是意味着你需要观察一个Netflix文件。它意味着立刻传递字节码，而不需要把整个文件读进内存
    @GET
    Observable<ResponseBody> downloadPhotoZip(@Url String fileUrl);

}
