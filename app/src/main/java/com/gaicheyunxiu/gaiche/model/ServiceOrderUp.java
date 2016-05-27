package com.gaicheyunxiu.gaiche.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/5/18.
 */
public class ServiceOrderUp implements Serializable {
    public String shopId;
    public String carTypeId;
    public String defAddId;
    public String name;
    public String phone;
    public String payment;
    public List<ServiceOrderVo> service;
}
