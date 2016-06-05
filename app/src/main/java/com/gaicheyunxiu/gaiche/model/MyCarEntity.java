package com.gaicheyunxiu.gaiche.model;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/11.
 */
@Table(name = "mycar")
public class MyCarEntity implements Serializable {

    @Column(column = "carbrandid")
    public String carBrandId;

    @Id(column = "cartypeid")
    public String carTypeId;

    @Column(column = "carbrandname")
    public String carBrandName;

    @Column(column = "carbrandlogo")
    public String carBrandLogo;

    @Column(column = "productionplace")
    public String productionPlace;

    @Column(column = "type")
    public String type;

    @Column(column = "displacement")
    public String displacement;

    @Column(column = "productiondate")
    public String productionDate;

    @Column(column = "isdefault")
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
