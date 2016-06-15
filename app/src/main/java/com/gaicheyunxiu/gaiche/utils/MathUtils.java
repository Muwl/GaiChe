package com.gaicheyunxiu.gaiche.utils;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2016/6/15.
 */
public class MathUtils {

    public static String getMathDem(double price){
        DecimalFormat df = new DecimalFormat("#################################0.0");
        return df.format(price);
    }
}
