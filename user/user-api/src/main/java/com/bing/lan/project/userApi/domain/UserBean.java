package com.bing.lan.project.userApi.domain;

import java.io.Serializable;

/**
 * Created by 蓝兵 on 2018/6/13.
 */

public class UserBean implements Serializable {

    //return "mobile: " + mobile + ", nickName: " + nickName + "login success!!";

    private String id;
    private String mobile;
    private String nickName;

    public UserBean(String id, String mobile, String nickName) {
        this.id = id;
        this.mobile = mobile;
        this.nickName = nickName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "id='" + id + '\'' +
                ", mobile='" + mobile + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
