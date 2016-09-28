package com.oubowu.jsoupdemo.http.entity;

/**
 * Created by Oubowu on 2016/9/28 0028 15:23.
 */
public class PhotoEntity {

    public String id;
    public String name;
    public String url;
    /**
     * thumbnailUrl将原图放大了，有点模糊
     */
    public String thumbnailUrl;
    public int width;
    public int height;
    /**
     * 热度，需要截取出来(title="这个群完了 热度: 7")
     */
    public int heat;

    @Override
    public String toString() {
        return "PhotoEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", heat=" + heat +
                '}';
    }

    public PhotoEntity setId(String id) {
        this.id = id;
        return this;
    }

    public PhotoEntity setName(String name) {
        this.name = name;
        return this;
    }

    public PhotoEntity setUrl(String url) {
        this.url = url;
        return this;
    }

    public PhotoEntity setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
        return this;
    }

    public PhotoEntity setWidth(int width) {
        this.width = width;
        return this;
    }

    public PhotoEntity setHeight(int height) {
        this.height = height;
        return this;
    }

    public PhotoEntity setHeat(int heat) {
        this.heat = heat;
        return this;
    }
}
