package com.gaicheyunxiu.gaiche.utils;

import android.content.Context;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
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
                return getDefaultCity(context);
            }
        } catch (DbException e) {
            e.printStackTrace();
            return  getDefaultCity(context);
        }

    }

    /**
     * 获取省
     * @param context
     * @return
     */
    public static List<CityEntity> getProvinces(Context context){
        DbUtils dbUtils=instance(context);
        try {
            List<CityEntity> cityEntities=dbUtils.findAll(Selector.from(CityEntity.class).where("level_type","=","1"));
            return  cityEntities;
        } catch (DbException e) {
            e.printStackTrace();
            return  new ArrayList<>();
        }

    }

    /**
     * 获取shi
     * @param context
     * @return
     */
    public static List<CityEntity> getCity(Context context){
        DbUtils dbUtils=instance(context);
        try {
            List<CityEntity> cityEntities=dbUtils.findAll(Selector.from(CityEntity.class).where("level_type","=","2"));
            return  cityEntities;
        } catch (DbException e) {
            e.printStackTrace();
            return  new ArrayList<>();
        }

    }

    /**
     * 获取热门市
     * @param context
     * @return
     */
    public static List<CityEntity> getHotCity(Context context){
        DbUtils dbUtils=instance(context);
        try {
            List<CityEntity> cityEntities=dbUtils.findAll(Selector.from(CityEntity.class).where("level_type", "=", "2").and("name", "=", "日照市").or("name","=","青岛市").or("name", "=", "济南市").or("name", "=", "临沂市").or("name", "=", "北京市").or("name", "=", "上海市").or("name", "=", "广州市"));
            return  cityEntities;
        } catch (DbException e) {
            e.printStackTrace();
            return  new ArrayList<>();
        }

    }

    /**
     * 获取热门市
     * @param context
     * @return
     */
    public static CityEntity getDefaultCity(Context context){
        DbUtils dbUtils=instance(context);
        try {
            CityEntity cityEntity=dbUtils.findFirst(Selector.from(CityEntity.class).where("level_type", "=", "2").and("name", "=", "日照市"));
            return  cityEntity;
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取市根据省
     * @param context
     * @return
     */
    public static List<CityEntity> getCity(Context context,String proId){
        DbUtils dbUtils=instance(context);
        try {
            List<CityEntity> cityEntities=dbUtils.findAll(Selector.from(CityEntity.class).where("level_type", "=", "2").and("parent_id", "=", proId));
            return  cityEntities;
        } catch (DbException e) {
            e.printStackTrace();
            return  new ArrayList<>();
        }

    }

    /**
     * 获取地区根据市
     * @param context
     * @return
     */
    public static List<CityEntity> getAreas(Context context,String cityId){
        DbUtils dbUtils=instance(context);
        try {
            List<CityEntity> cityEntities=dbUtils.findAll(Selector.from(CityEntity.class).where("level_type","=","3").and("parent_id","=",cityId));
            return  cityEntities;
        } catch (DbException e) {
            e.printStackTrace();
            return  new ArrayList<>();
        }

    }

}
