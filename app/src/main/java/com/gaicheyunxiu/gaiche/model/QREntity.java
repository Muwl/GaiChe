package com.gaicheyunxiu.gaiche.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/2.
 */
public class QREntity implements Serializable {
    public String id;
    public String name;
    public String icon;
    public String isHavePayPwd;//是否已经设置支付密码（0：否，1：是）
    public String isHaveMobile;//是否已经绑定手机号，为0，则没有设置，否则已经设置
}
