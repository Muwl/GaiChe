package com.gaicheyunxiu.gaiche.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/17.
 */
public class ShopEvaluationEntity implements Serializable {

    public String id;
    public String userIcon;
    public String name;
    public String content;
    public String serviceScore;
    public String environmentScore;
    public String technologyScore;
    public String priceScore;
    public String createDate;
    public String star;

    @Override
    public String toString() {
        return "ShopEvaluationEntity{" +
                "id='" + id + '\'' +
                ", userIcon='" + userIcon + '\'' +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", serviceScore='" + serviceScore + '\'' +
                ", environmentScore='" + environmentScore + '\'' +
                ", technologyScore='" + technologyScore + '\'' +
                ", priceScore='" + priceScore + '\'' +
                ", createDate='" + createDate + '\'' +
                ", star='" + star + '\'' +
                '}';
    }
}
