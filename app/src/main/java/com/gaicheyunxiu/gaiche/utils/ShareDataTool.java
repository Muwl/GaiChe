package com.gaicheyunxiu.gaiche.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.gaicheyunxiu.gaiche.model.MyCarEntity;
import com.gaicheyunxiu.gaiche.model.RegiterEntity;
import com.google.gson.Gson;

/**
 * @author Mu
 * @date 2015-8-5下午9:55:20
 * @description SharePrefence 工具类
 */
public class ShareDataTool {

    public static boolean SaveInfo(Context context, RegiterEntity regiterEntity) {
        SharedPreferences sp = context.getSharedPreferences("sp",
                Context.MODE_PRIVATE);
        Editor e = sp.edit();
        if (regiterEntity!=null){
            e.putString("token", regiterEntity.token);
            e.putString("userId", regiterEntity.userId);
            e.putString("phone", regiterEntity.phone);
            e.putString("imUsername", regiterEntity.imUsername);
            e.putString("imPassword", regiterEntity.imPassword);
            e.putString("loginState", regiterEntity.loginState);
            e.putString("gcCode", regiterEntity.gcCode);
            e.putString("mobile", regiterEntity.mobile);
            e.putString("icon", regiterEntity.icon);
            e.putString("nickname", regiterEntity.nickname);
        }else{
            e.putString("token", "");
            e.putString("userId","");
            e.putString("imUsername","");
            e.putString("imPassword", "");
            e.putString("loginState", "");
            e.putString("gcCode", "");
            e.putString("mobile","");
            e.putString("icon", "");
            e.putString("nickname", "");
        }

        return e.commit();
    }

    public static String getToken(Context context) {
        return context.getSharedPreferences("sp", Context.MODE_PRIVATE)

                .getString("token", "");
    }

    public static RegiterEntity getRegiterEntity(Context context) {
        RegiterEntity regiterEntity=new RegiterEntity();
        regiterEntity.token= context.getSharedPreferences("sp", Context.MODE_PRIVATE).getString("token", "");
        regiterEntity.userId= context.getSharedPreferences("sp", Context.MODE_PRIVATE).getString("userId", "");
        regiterEntity.imUsername= context.getSharedPreferences("sp", Context.MODE_PRIVATE).getString("imUsername", "");
        regiterEntity.imPassword= context.getSharedPreferences("sp", Context.MODE_PRIVATE).getString("imPassword", "");
        regiterEntity.loginState= context.getSharedPreferences("sp", Context.MODE_PRIVATE).getString("loginState", "");
        regiterEntity.gcCode= context.getSharedPreferences("sp", Context.MODE_PRIVATE).getString("gcCode", "");
        regiterEntity.mobile= context.getSharedPreferences("sp", Context.MODE_PRIVATE).getString("mobile", "");
        regiterEntity.icon= context.getSharedPreferences("sp", Context.MODE_PRIVATE).getString("icon", "");
        regiterEntity.nickname= context.getSharedPreferences("sp", Context.MODE_PRIVATE).getString("nickname", "");
        regiterEntity.phone= context.getSharedPreferences("sp", Context.MODE_PRIVATE).getString("phone", "");
        return regiterEntity;
    }

//    public static boolean saveCar(Context context, MyCarEntity myCarEntity) {
//        SharedPreferences sp = context.getSharedPreferences("sp",
//                Context.MODE_PRIVATE);
//        Editor e = sp.edit();
//        if (myCarEntity!=null){
//            Gson gson=new Gson();
//            e.putString("mycar", gson.toJson(myCarEntity));
//            return e.commit();
//        }
//        return false;
//    }
//
//    public static MyCarEntity getCar(Context context){
//        String s=context.getSharedPreferences("mycar", Context.MODE_PRIVATE).getString("token", "");
//        Gson gson=new Gson();
//        try {
//            return gson.fromJson(s,MyCarEntity.class);
//        }catch (Exception e){
//            return null;
//        }
//    }

    /**
     * 保存是否为第一次启动
     *
     * @param flag
     *            0第一次启动 1 以后启动
     * @return
     */
    public static boolean saveStart(Context context, int flag) {
        SharedPreferences sp = context.getSharedPreferences("start",
                Context.MODE_PRIVATE);
        Editor e = sp.edit();
        e.putInt("flag", flag);
        return e.commit();
    }

    /**
     * 获取是否为第一次启动
     *
     * @param context
     * @return
     */
    public static int getStart(Context context) {
        return context.getSharedPreferences("start", Context.MODE_PRIVATE)
                .getInt("flag", 0);
    }




}
