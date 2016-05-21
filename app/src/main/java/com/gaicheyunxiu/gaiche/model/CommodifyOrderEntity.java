package com.gaicheyunxiu.gaiche.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/5/21.
 */
public class CommodifyOrderEntity implements Serializable {

    public String payWay;
    public String shopId;
    public double shopServicePrice;
    public String carTypeId;
    public String receiveProvince;
    public String receiveCity;
    public String receiveDistrict;
    public String receiveDetail;
    public String receiveProvinceId;
    public String receiveCityId;
    public String receiveDistrictId;
    public String name;
    public String mobile;
    public String isShoppingCart;
    public List<CommodifyOrderVo> vos;

}
