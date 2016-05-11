package com.gaicheyunxiu.gaiche.utils;

import android.app.Application;

import com.gaicheyunxiu.gaiche.model.MyCarEntity;

/**
 * Created by Administrator on 2016/5/11.
 */
public class MyApplication extends Application {

    private static MyApplication instance;

    private MyCarEntity carEntity;

    public static MyApplication getInstance() {
        return instance;
    }

    public void onCreate() {
        super.onCreate();
        instance=this;
    }

    public   MyCarEntity getCarEntity() {
        return carEntity;
    }

    public  void setCarEntity(MyCarEntity carEntity) {
        this.carEntity = carEntity;
    }
}
