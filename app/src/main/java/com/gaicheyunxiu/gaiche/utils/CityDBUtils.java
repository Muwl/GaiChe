package com.gaicheyunxiu.gaiche.utils;

import android.content.Context;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.List;

/**
 * Created by Administrator on 2016/5/14.
 */
public class CityDBUtils {

    private Context context;

    public static DbUtils instance(Context context){
        DbUtils.DaoConfig daoConfig = new DbUtils.DaoConfig(context);
        daoConfig.setDbName("city.db");
        DbUtils dbUtils = DbUtils.create(daoConfig);
        return  dbUtils;
    }


    /**
     * 根据城市名称获取city
     * @param context
     * @param name
     * @return
     */
    public static CityEntity getCityIdFromName(Context context,String name){
        DbUtils dbUtils=instance(context);
        try {
            List<CityEntity> cityEntities=dbUtils.findAll(Selector.from(CityEntity.class).where("name","=",name));
            if (cityEntities!=null && cityEntities.size()>0){
                return cityEntities.get(0);
            }else{
                return null;
            }
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }

    }
}
