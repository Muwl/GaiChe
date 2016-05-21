package com.gaicheyunxiu.gaiche.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/5/21.
 */
public class OrderCommodityVo implements Serializable {

    public String shopId;
    public String receiveProvince;
    public String receiveCity;
    public String receiveDistrict;
    public String receiveDetail;
    public List<OrderCommodityDigestEntity> list;
}
