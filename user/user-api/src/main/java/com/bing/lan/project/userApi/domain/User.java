package com.bing.lan.project.userApi.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.bing.lan.BaseDomain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

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
public class User extends BaseDomain implements Serializable {

    private Long id;

    private String phone;

    @JsonIgnore
    @JSONField(serialize = false)
    private String password;

    private String userName;

    private String nickname;

    @JsonIgnore
    private String isDelete;
}