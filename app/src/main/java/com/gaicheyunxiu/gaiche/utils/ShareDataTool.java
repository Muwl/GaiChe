package com.gaicheyunxiu.gaiche.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.gaicheyunxiu.gaiche.model.RegiterEntity;

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
        e.putString("token", regiterEntity.token);
        e.putString("userId", regiterEntity.userId);
        e.putString("imUsername", regiterEntity.imUsername);
        e.putString("imPassword", regiterEntity.imPassword);
        e.putString("loginState", regiterEntity.loginState);
        e.putString("gcCode", regiterEntity.gcCode);
        e.putString("mobile", regiterEntity.mobile);
        e.putString("icon", regiterEntity.icon);
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
        return regiterEntity;
    }



}
