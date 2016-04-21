package com.gaicheyunxiu.gaiche.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/21.
 */
public class PersonDataEntity implements Serializable {


    /**
     * msg : 200
     * result : {"id":"752b0dcf910d41f9bbf69e0a73cfe1ad","icon":"http://120.25.225.224:8011/group1/M00/00/00/eBnh4FaPKIWAHUyrAAABU93Jswc624.png","gcCode":"GC100012","nickname":"","mobile":"","email":"2222@333.com"}
     */

    public String msg;
    /**
     * id : 752b0dcf910d41f9bbf69e0a73cfe1ad
     * icon : http://120.25.225.224:8011/group1/M00/00/00/eBnh4FaPKIWAHUyrAAABU93Jswc624.png
     * gcCode : GC100012
     * nickname :
     * mobile :
     * email : 2222@333.com
     */
    public PersonData result;
    public static class PersonData {
        public String id;
        public String icon;
        public String gcCode;
        public String nickname;
        public String mobile;
        public String email;
    }
}
