package com.gaicheyunxiu.gaiche.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @author Mu
 * @date 2015-8-5下午9:55:20
 * @description SharePrefence 工具类
 */
public class ShareDataTool {

    public static boolean SaveInfo(Context context, String token) {
        SharedPreferences sp = context.getSharedPreferences("sp",
                Context.MODE_PRIVATE);
        Editor e = sp.edit();
        e.putString("token", token);
        return e.commit();
    }

    public static String getToken(Context context) {

        return context.getSharedPreferences("sp", Context.MODE_PRIVATE)

                .getString("token", "");
    }


}
