package com.bing.lan.project.userApi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User implements Serializable {

    private Long id;

    private String phone;

    @JsonIgnore
    //@JSONField(serialize = false)
    private String password;

    private String userName;

    private String nickname;

    @JsonIgnore
    //@JSONField(serialize = false)
    private String isDelete;
}