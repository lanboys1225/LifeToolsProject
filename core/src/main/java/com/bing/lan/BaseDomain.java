package com.bing.lan;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * Created by 蓝兵 on 2018/6/26.
 */

@Data
public class BaseDomain {

    @JsonIgnore
    private String msg;
}
