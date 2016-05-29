package com.gaicheyunxiu.gaiche.utils;

import java.util.Comparator;

/**
 * Created by Administrator on 2016/5/29.
 */
public class CityComparator implements Comparator<CityEntity> {
    @Override
    public int compare(CityEntity lhs, CityEntity rhs) {
        if (ToosUtils.isStringEmpty(lhs.pinyin) || ToosUtils.isStringEmpty(rhs.pinyin)){
            return 1;
        }
        return lhs.pinyin.toUpperCase().substring(0, 1).compareTo(rhs.pinyin.toUpperCase().substring(0,1));
    }
}
