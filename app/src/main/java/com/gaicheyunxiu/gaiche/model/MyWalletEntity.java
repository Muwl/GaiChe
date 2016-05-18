package com.gaicheyunxiu.gaiche.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/5/18.
 */
public class MyWalletEntity implements Serializable {
    public String balance;
    public List<BankCardEntity> bankCards;
}
