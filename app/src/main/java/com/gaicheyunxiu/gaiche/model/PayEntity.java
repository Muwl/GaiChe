package com.gaicheyunxiu.gaiche.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/25.
 */
public class PayEntity implements Serializable {

    public  String parentId;
    public  String appid;
    public  String prepayid;
    public  String noncestr;
    public  String timestamp;
    public  String returnCode;
    public  String content;
    public  String paySign;
    public  String payType;
}
