package com.bing.lan.project.userApi.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginLog {

    private Long id;

    private Long userId;

    private String phone;

    private String userName;

    private String status;

    private String token;

    private Date tokenExpireTime;

    private Date date;

    private String ip;

    private String version;

    private String deviceId;

    private String platform;

    private String channel;
}