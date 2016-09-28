package com.oubowu.jsoupdemo.http.entity;

/**
 * Created by Oubowu on 2016/9/29 0:38.
 */
public class BuilderPhotoEntity {

    public String section;
    public String sectionName;
    public String url;
    public String name;

    public BuilderPhotoEntity setSection(String section) {
        this.section = section;
        return this;
    }

    public BuilderPhotoEntity setSectionName(String sectionName) {
        this.sectionName = sectionName;
        return this;
    }

    public BuilderPhotoEntity setUrl(String url) {
        this.url = url;
        return this;
    }

    public BuilderPhotoEntity setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "BuilderPhotoEntity{" +
                "section='" + section + '\'' +
                ", sectionName='" + sectionName + '\'' +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
