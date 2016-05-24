package com.gaicheyunxiu.gaiche.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/5/12.
 * 商品详情
 */
public class ShopDetailEntity implements Serializable {
    public String id;
    public String merchantId;
    public String merchantName;
    public String name;
    public double originalPrice;
    public double presentPrice;
    public double wholesalePrice;
    public String distributionType;//配送方式（0:物流 1：自主配送）
    public String distributionDetail;
    public double weight;
    public String standard;
    public String unit;
    public int inventory;
    public String introduction;
    public String briefImage;
    public List<String> detailImage;
    public String specification;
    public String afterSalesService;
    public String commodityCategory;
    public String commodityType;
    public String brand;
    public int sales;
    public int evaluationNum;
    public double MValue;
    public int paymentNum;
    public double evaluationScore;

    public int num;


}
