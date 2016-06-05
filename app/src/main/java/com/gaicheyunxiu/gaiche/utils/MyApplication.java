package com.gaicheyunxiu.gaiche.utils;

import android.app.Application;
import android.os.Handler;
import android.os.Message;

import com.baidu.location.BDLocation;
import com.gaicheyunxiu.gaiche.model.MyCarEntity;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.socialize.PlatformConfig;

import java.io.IOException;

/**
 * Created by Administrator on 2016/5/11.
 */
public class MyApplication extends Application {

    private static MyApplication instance;

    private MyCarEntity carEntity;

    private CityEntity cityEntity;

    private BDLocation bdLocation;

    private String weixinmoney;

    private int payFlag=0;//0代表微信支付  1代表微信充值


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


    public String getWeixinmoney() {
        return weixinmoney;
    }

    public void setWeixinmoney(String weixinmoney) {
        this.weixinmoney = weixinmoney;
    }

    public int getPayFlag() {
        return payFlag;
    }

    public void setPayFlag(int payFlag) {
        this.payFlag = payFlag;
    }

    public void onCreate() {
        super.onCreate();
        instance = this;

        try {
            FileTool.copyAssetFileToDatabase(this, "city.db","city.db");
        } catch (IOException e) {
            e.printStackTrace();
        }
        LocalUtils localUtils = new LocalUtils(this, handler);
        localUtils.startLocation();

        PlatformConfig.setWeixin("wxf85c74c2ef0697bb", "bd6560ed816488efba483183342b449a");


        //微信 appid appsecret
        PlatformConfig.setSinaWeibo("490536809", "fdedda70cb18b2bdc493d0367a89b8ee");
        //新浪微博 appkey appsecret
        PlatformConfig.setQQZone("1105374334", "NGYVwlWYMgNZBQ5b");
        // QQ和Qzone appid appkey


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
