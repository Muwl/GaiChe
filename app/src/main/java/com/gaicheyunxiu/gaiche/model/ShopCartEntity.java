package com.gaicheyunxiu.gaiche.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/5/20.
 */
public class ShopCartEntity implements Serializable {

    public String carTypeId;
    public String carBrand;
    public String carType;
    public String productionPlace;
    public String displacement;
    public String productionDate;
    public List<ShopCartCommodityEntity> cartCommodityVOs;
    public OutSelEntity outSelEntity;
}
