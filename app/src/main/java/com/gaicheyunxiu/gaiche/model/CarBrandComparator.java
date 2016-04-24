package com.gaicheyunxiu.gaiche.model;

import com.gaicheyunxiu.gaiche.utils.ToosUtils;

import java.util.Comparator;

/**
 * Created by Administrator on 2016/4/24.
 */
public class CarBrandComparator implements Comparator<CarBrandEntity> {
    @Override
    public int compare(CarBrandEntity lhs, CarBrandEntity rhs) {

        if (ToosUtils.isStringEmpty(lhs.letter)){
            lhs.letter="#";
        }

        if (ToosUtils.isStringEmpty(rhs.letter)){
            lhs.letter="#";
        }

        if ("#".equals(lhs.letter)){
            return 1;
        }



        return lhs.letter.compareTo(rhs.letter);
    }
}
