package com.gaicheyunxiu.gaiche.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/23.
 */
public class ShopOrderVo implements Serializable{
    public String id;
    public String businessName;
    public String name;
    public String presentPrice;
    public String mValue;
    public String wholesalePrice;
    public String inventory;
    public String commodityTypeName;
    public String brand;
    public String briefImage;
    public String originalPrice;
    public String sales;
    public String state;//0：未发货1：已发货2：已完成（已收货、待评价）3：已评4：申请退货5：退货成功（已退货）6：退货失败 7：退货中（商家已同意退货申请，但是退款还未完成）


}
