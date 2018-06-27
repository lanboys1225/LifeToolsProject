package com.bing.lan.project.userApi.domain;

import com.bing.lan.domain.BaseDomain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Created by 蓝兵 on 2018/6/27.
 */
@Builder
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordResult extends BaseDomain {
    private boolean isResetSuccess;
}
