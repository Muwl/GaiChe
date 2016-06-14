package com.gaicheyunxiu.gaiche.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/27.
 */
public class CommodityEntity implements Serializable {

    public String id;
    public String name;
    public String briefImage;
    public String originalPrice;
    public String presentPrice;
    public String mValue;
    public String sales;
    public int num=1;

    @Override
    public String toString() {
        return "CommodityEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", briefImage='" + briefImage + '\'' +
                ", originalPrice='" + originalPrice + '\'' +
                ", presentPrice='" + presentPrice + '\'' +
                ", mValue='" + mValue + '\'' +
                ", sales='" + sales + '\'' +
                ", num=" + num +
                '}';
    }
}
