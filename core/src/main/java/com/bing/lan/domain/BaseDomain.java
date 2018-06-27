package com.bing.lan.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by 蓝兵 on 2018/6/26.
 */

@Data
public class BaseDomain implements Serializable {

    @JsonIgnore
    private String msg;
}
