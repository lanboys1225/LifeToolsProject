package com.bing.lan.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by 蓝兵 on 2018/6/27.
 */

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommRequestParams extends BaseDomain {

    private String ip;

    private String version;

    private String deviceId;

    private String platform;

    private String channel;
}
