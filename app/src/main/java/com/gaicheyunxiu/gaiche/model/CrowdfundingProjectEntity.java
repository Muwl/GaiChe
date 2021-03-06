package com.gaicheyunxiu.gaiche.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/22.
 */
public class CrowdfundingProjectEntity implements Serializable {

    public String id;
    public String name;
    public String startDate;
    public String endDate;
    public String mobileImg;
    public String pcImg;
    public String expectMoney;
    public String completeMoney;

    @Override
    public String toString() {
        return "CrowdfundingProjectEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", mobileImg='" + mobileImg + '\'' +
                ", pcImg='" + pcImg + '\'' +
                ", expectMoney='" + expectMoney + '\'' +
                ", completeMoney='" + completeMoney + '\'' +
                '}';
    }
}
