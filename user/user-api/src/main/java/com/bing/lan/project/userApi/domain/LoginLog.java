package com.bing.lan.project.userApi.domain;

import com.bing.lan.BaseDomain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginLog extends BaseDomain {

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