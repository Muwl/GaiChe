package com.gaicheyunxiu.gaiche.utils;

import android.content.Context;

import com.gaicheyunxiu.gaiche.model.MyCarEntity;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/5.
 */
public class MyCarEntityUtils {

    private Context context;
    private DbUtils db;


    public MyCarEntityUtils(Context context) {
        this.context = context;
        db = DbUtils.create(context);
        db.configAllowTransaction(true);
        db.configDebug(true);
    }

    public List<MyCarEntity> getAllMyCar(){
        try {
            return db.findAll(MyCarEntity.class);
        } catch (DbException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public MyCarEntity getDefault(){
        try {
            MyCarEntity myCarEntity= db.findFirst(Selector.from(MyCarEntity.class).where("isdefault","=",1));
            if (myCarEntity!=null){
                return myCarEntity;
            }
            return  db.findFirst(MyCarEntity.class);
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveMyCar(MyCarEntity carEntity){
        try {
            db.save(carEntity);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void delMyCar(MyCarEntity carEntity){
        try {
            db.delete(carEntity);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void removeAllCar(){
        try {
            db.deleteAll(MyCarEntity.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    public void updateDefault(MyCarEntity carEntity){
        String sql="UPDATE mycar SET isdefault=0";
        try {
            db.execNonQuery(sql);
            db.update(carEntity);
        } catch (DbException e) {
            e.printStackTrace();
        }

    }


}
