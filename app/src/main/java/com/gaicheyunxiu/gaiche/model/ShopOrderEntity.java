package com.gaicheyunxiu.gaiche.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/5/23.
 */
public class ShopOrderEntity implements Serializable{
    public String orderId;
    public String orderNo;
    public String price;
    public String createDate;
    public String orderState;
    public String split;
    public List<ShopOrderVo> orderListVos;
    public List<ShopOrderVo> vos;
}
