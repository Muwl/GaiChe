package com.gaicheyunxiu.gaiche.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/20.
 */
public class PersonDynamicEntity implements Serializable {


    /**
     * msg : 200
     * result : {"balance":"0.0","sysSerImUsername":"C81E728D9D4C2F636F067F89CC14862C","sysSerIcon":"http://120.25.225.224:8011/group1/M00/00/00/eBnh4FaPKIWAHUyrAAABU93Jswc624.png","sysSerNickname":"管理员","shoppingCartNum":"0","commodityToPayOrderNum":"0","commodityToReceiveNum":"0","commodityToEvaluateNum":"0","serviceToPayOrderNum":"0","serviceToUseNum":"0","serviceToEvaluateNum":"0","crowdfundingToPayOrderNum":"0","crowdfundingToReceiveNum":"0","crowdfundingToEvaluateNum":"0","earings":"0","wallet":"0"}
     */

    public String msg;
    /**
     * balance : 0.0
     * sysSerImUsername : C81E728D9D4C2F636F067F89CC14862C
     * sysSerIcon : http://120.25.225.224:8011/group1/M00/00/00/eBnh4FaPKIWAHUyrAAABU93Jswc624.png
     * sysSerNickname : 管理员
     * shoppingCartNum : 0
     * commodityToPayOrderNum : 0
     * commodityToReceiveNum : 0
     * commodityToEvaluateNum : 0
     * serviceToPayOrderNum : 0
     * serviceToUseNum : 0
     * serviceToEvaluateNum : 0
     * crowdfundingToPayOrderNum : 0
     * crowdfundingToReceiveNum : 0
     * crowdfundingToEvaluateNum : 0
     * earings : 0
     * wallet : 0
     */

    public ResultBean result;

    public static class ResultBean {
        public String balance;
        public String sysSerImUsername;
        public String sysSerIcon;
        public String sysSerNickname;
        public String shoppingCartNum;
        public String commodityToPayOrderNum;
        public String commodityToReceiveNum;
        public String commodityToEvaluateNum;
        public String serviceToPayOrderNum;
        public String serviceToUseNum;
        public String serviceToEvaluateNum;
        public String crowdfundingToPayOrderNum;
        public String crowdfundingToReceiveNum;
        public String crowdfundingToEvaluateNum;
        public String earings;
        public String wallet;
    }
}
