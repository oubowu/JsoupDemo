package com.oubowu.jsoupdemo.http.model;

import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.oubowu.jsoupdemo.http.entity.BuilderPhotoEntity;
import com.oubowu.jsoupdemo.http.entity.PhotoEntity;
import com.oubowu.jsoupdemo.http.entity.PhotoZipEntiity;
import com.oubowu.jsoupdemo.http.RetrofitManager;
import com.oubowu.jsoupdemo.http.entity.SearchPhotoEntitiy;
import com.oubowu.jsoupdemo.utils.FileUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Oubowu on 2016/9/28 0028 16:15.
 */
public class PhotoModel {

    public static Subscription downloadPhotoZip(final String fileUrl) {
        return RetrofitManager.getInstance().downloadPhotoZip(fileUrl).map(new Func1<ResponseBody, File>() {
            @Override
            public File call(ResponseBody responseBody) {
                try {
                    return FileUtils.saveFile(responseBody, Environment.getExternalStorageDirectory().getAbsolutePath(), "你大爷.zip");
                } catch (IOException e) {
                    Observable.error(e);
                }
                return null;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<File>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "PhotoModel-154行-onError(): " + e);
            }

            @Override
            public void onNext(File file) {
                Log.e("TAG", "PhotoModel-159行-onNext(): " + file.getName());
            }
        });
    }

    public static Subscription getBuilderPhotoList() {
        return RetrofitManager.getInstance().getBuilderPhotoList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG", "PhotoModel-39行-onError(): " + e);
                    }

                    @Override
                    public void onNext(String s) {

                        final Document document = Jsoup.parse(s);

                        final Element sectionRootElement = document.getElementById("image-tabs");
                        // 找到几个tab信息
                        final Elements sectionElements = sectionRootElement.select("a[aria-controls]");

                        // 找到tab对应的图片列表
                        final Elements tabContentElements = document.select("div[class=tab-content]");

                        Element element;
                        BuilderPhotoEntity entity;
                        Elements urlElements;
                        String section;
                        String sectionName;

                        List<BuilderPhotoEntity> photoEntityList = new ArrayList<>();

                        for (int i = 0; i < sectionElements.size(); i++) {
                            element = sectionElements.get(i);

                            section = element.attr("aria-controls");
                            sectionName = element.text();

                            // 通过section查找元素
                            urlElements = tabContentElements.select("div#" + section);
                            // 查出图片列表
                            urlElements = urlElements.select("img.builder-cover");
                            for (int j = 0; j < urlElements.size(); j++) {
                                element = urlElements.get(j);
                                entity = new BuilderPhotoEntity().setName(element.attr("alt")).setSection(section).setSectionName(sectionName)
                                        .setUrl(element.attr("src"));
                                photoEntityList.add(entity);
                            }
                        }

                    }
                });
    }

    public static Subscription searchPhoto(String keywords) {
        return RetrofitManager.getInstance().searchPhoto(keywords).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG", "PhotoModel-39行-onError(): " + e);
                    }

                    @Override
                    public void onNext(String s) {
                        try {
                            final List<SearchPhotoEntitiy> searchPhotoEntitiys = new Gson().fromJson(s, new TypeToken<List<SearchPhotoEntitiy>>() {
                            }.getType());
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public static Subscription getPhotoList(final int page, boolean isSortHot) {
        Observable<String> observable;
        if (isSortHot) {
            observable = RetrofitManager.getInstance().getHotPhotoList(page);
        } else {
            observable = RetrofitManager.getInstance().getNewPhotoList(page);
        }

        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "PhotoModel-125行-onError(): " + e);
            }

            @Override
            public void onNext(String s) {

                final Document document = Jsoup.parse(s);

                final Elements pagination = document.select("ul.pagination:has(a) a[href]");
                int maxPage;
                if (pagination.size() >= 3) {
                    try {
                        // 最大页数
                        maxPage = Integer.parseInt(pagination.get(pagination.size() - 2).text());
                        //                        Log.e("TAG", "PhotoModel-45行-onNext(): " + maxPage);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        maxPage = 100;
                    }
                }

                // 查找div标签,class为picture-list,含有子元素a,并且a元素含有data-picture-id属性
                final Elements elements = document.select("div.picture-list:has(a) a[data-picture-id]");

                List<PhotoEntity> photoEntities = new ArrayList<>(elements.size());
                Element element;
                Element child;
                PhotoEntity photoEntity;
                String title;
                int heat = 0;
                int width = 0;
                int height = 0;

                // 表情包压缩包下载
                final Elements zipElements = document.select("li[class=\"\"]:has(a) a:has(img)");
                log(zipElements);
                PhotoZipEntiity photoZipEntiity;
                for (int i = 0; i < zipElements.size(); i++) {
                    element = zipElements.get(i);
                    photoZipEntiity = new PhotoZipEntiity().setDownloadUrl(element.attr("href")).setIcon(element.child(0).attr("src")).setTitle(element.text());
                    //                    Log.e("TAG", photoZipEntiity.toString());

                }

                for (int i = 0; i < elements.size(); i++) {

                    element = elements.get(i);

                    child = element.child(0);

                    if (child != null) {
                        title = child.attr("title");
                        title = title.substring(title.indexOf(": ") + 2, title.length());
                        try {
                            heat = Integer.parseInt(title);
                            width = Integer.parseInt(child.attr("width"));
                            height = Integer.parseInt(child.attr("height"));
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            heat = 0;
                            width = 0;
                            height = 0;
                        }
                    }

                    photoEntity = new PhotoEntity().setId(element.attr("data-picture-id")).setName(element.attr("alt")).setUrl(element.attr("href"))
                            .setThumbnailUrl(child.attr("src")).setHeat(heat).setWidth(width).setHeight(height);

                    photoEntities.add(photoEntity);

                }

            }
        });

    }

    public static <T> void log(List<T> elements) {
        for (T e : elements) {
            Log.e("TAG", "PhotoModel-100行-log(): " + e.toString());
        }
    }

}
