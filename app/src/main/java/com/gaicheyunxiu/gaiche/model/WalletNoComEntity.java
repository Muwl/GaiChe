package com.gaicheyunxiu.gaiche.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/18.
 */
public class WalletNoComEntity implements Serializable {

    public  String isHavePayPwd;//是否设置支付密码（0：否，1：是）
    public String isHaveMobile;//是否绑定过手机号（为0时，表示没有绑定过，否则说明绑定过，此参数的值既是手机号）
}
