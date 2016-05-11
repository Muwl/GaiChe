package com.gaicheyunxiu.gaiche.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/11.
 */
public class MyCarEntity implements Serializable {
    public String carBrandId;
    public String carTypeId;
    public String carBrandName;
    public String carBrandLogo;
    public String productionPlace;
    public String type;
    public String displacement;
    public String productionDate;
    public boolean isDefault;

    @Override
    public String toString() {
        return "MyCarEntity{" +
                "carBrandId='" + carBrandId + '\'' +
                ", carTypeId='" + carTypeId + '\'' +
                ", carBrandName='" + carBrandName + '\'' +
                ", carBrandLogo='" + carBrandLogo + '\'' +
                ", productionPlace='" + productionPlace + '\'' +
                ", type='" + type + '\'' +
                ", displacement='" + displacement + '\'' +
                ", productionDate='" + productionDate + '\'' +
                ", isDefault='" + isDefault + '\'' +
                '}';
    }
}
