package com.bing.lan.project.userApi.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User implements Serializable {

    private Long id;

    private String phone;

    private String password;

    private String userName;

    private String nickname;

    private String isDelete;
}