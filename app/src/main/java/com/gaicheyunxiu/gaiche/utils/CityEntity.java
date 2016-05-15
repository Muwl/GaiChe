package com.gaicheyunxiu.gaiche.utils;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/14.
 */
@Table(name="tb_city")
public class CityEntity implements Serializable{

    @Id(column = "id")
    public String id;
    @Column(column = "name")
    public String name;
    @Column(column = "parent_id")
    public String parent_id;
    @Column(column = "short_name")
    public String short_name;
    @Column(column = "level_type")
    public String level_type;
    @Column(column = "city_code")
    public String city_code;
    @Column(column = "zip_code")
    public String zip_code;
    @Column(column = "merger_name")
    public String merger_name;
    @Column(column = "longitude")
    public double longitude;
    @Column(column = "latitude")
    public double latitude;
    @Column(column = "pinyin")
    public String pinyin;
    @Column(column = "create_by")
    public String create_by;
    @Column(column = "create_date")
    public String create_date;
    @Column(column = "update_by")
    public String update_by;
    @Column(column = "update_date")
    public String update_date;
    @Column(column = "remarks")
    public String remarks;
    @Column(column = "del_flag")
    public String del_flag;

    public double locallongitude;

    public double locallatitude;
}
