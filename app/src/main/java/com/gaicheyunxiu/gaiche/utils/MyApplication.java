package com.gaicheyunxiu.gaiche.utils;

import android.app.Application;
import android.os.Handler;
import android.os.Message;

import com.baidu.location.BDLocation;
import com.gaicheyunxiu.gaiche.model.MyCarEntity;

/**
 * Created by Administrator on 2016/5/11.
 */
public class MyApplication extends Application {

    private static MyApplication instance;

    private MyCarEntity carEntity;

    private CityEntity cityEntity;

    private BDLocation bdLocation;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            bdLocation = (BDLocation) msg.obj;
            String city=bdLocation.getCity();
            cityEntity= CityDBUtils.getCityIdFromName(MyApplication.this, city);
            cityEntity.locallongitude=bdLocation.getLongitude();
            cityEntity.locallatitude=bdLocation.getLatitude();
        }
    };

    public static MyApplication getInstance() {
        return instance;
    }

    public void onCreate() {
        super.onCreate();
        instance = this;
        LocalUtils localUtils = new LocalUtils(this, handler);
        localUtils.startLocation();
    }

    public MyCarEntity getCarEntity() {
        return carEntity;
    }

    public void setCarEntity(MyCarEntity carEntity) {

        this.carEntity = carEntity;
    }

    public CityEntity getCityEntity() {
        if (cityEntity==null){
            cityEntity=CityDBUtils.getDefaultCity(this);
        }
        return cityEntity;
    }

    public BDLocation getBdLocation() {
        return bdLocation;
    }

    public void setCityEntity(CityEntity cityEntity) {
        this.cityEntity = cityEntity;
    }
}
