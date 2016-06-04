package com.gaicheyunxiu.gaiche.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/6/4.
 */
public class CostEntity implements Serializable {
    public double price;
    public double freight;
    public double mValue;
    public double shopServicePrice;
    public List<CostFreVo> freightVo;
}
