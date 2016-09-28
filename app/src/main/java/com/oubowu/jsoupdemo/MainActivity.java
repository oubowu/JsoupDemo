package com.oubowu.jsoupdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.oubowu.jsoupdemo.http.model.PhotoModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        PhotoModel.getPhotoList(1,true);
//        PhotoModel.searchPhoto("我");

//        PhotoModel.getBuilderPhotoList();

        PhotoModel.downloadPhotoZip("http://zhuangbi.idagou.com/downloads/alu-small.zip");

    }



}
