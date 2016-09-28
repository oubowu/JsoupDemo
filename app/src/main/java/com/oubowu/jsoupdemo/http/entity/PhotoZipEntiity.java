package com.oubowu.jsoupdemo.http.entity;

/**
 * Created by Oubowu on 2016/9/29 1:23.
 */
public class PhotoZipEntiity {

    public String downloadUrl;
    public String icon;
    public String title;

    public PhotoZipEntiity setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
        return this;
    }

    public PhotoZipEntiity setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public PhotoZipEntiity setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public String toString() {
        return "PhotoZipEntiity{" +
                "downloadUrl='" + downloadUrl + '\'' +
                ", icon='" + icon + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
