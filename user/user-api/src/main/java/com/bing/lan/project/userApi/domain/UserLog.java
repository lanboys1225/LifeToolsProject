package com.bing.lan.project.userApi.domain;

import com.bing.lan.domain.BaseDomain;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class UserLog extends BaseDomain {

    private Long id;

    private String logType;

    private Long userId;

    private String phone;

    private String userName;

    private String loginStatus;

    private String token;

    private Date tokenExpireTime;

    private Date createTime;

    @JsonIgnore
    private Date updateTime;

    private String ip;

    private String version;

    private String deviceId;

    private String platform;

    private String channel;

    private String comment;
}