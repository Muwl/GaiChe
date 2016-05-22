package com.gaicheyunxiu.gaiche.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/5/22.
 */
public class SerOrderEntity implements Serializable {
    public String id;
    public String orderNo;
    public String totalPrice;
    public String createDate;
    public String orderState;
    public String shopId;
    public String shopName;
    public String longitude;
    public String latitude;
    public String serviceNum;
    public List<SerOrderVo> service;
}
