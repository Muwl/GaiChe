package com.gaicheyunxiu.gaiche.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/6/5.
 */
public class ReFundDetailEntity implements Serializable{
    public  String money;
    public  String account;//退款账号（0：钱包，1：支付宝，2：微信，3：银联）
    public List<ReFundDetailVo> detail;
}
