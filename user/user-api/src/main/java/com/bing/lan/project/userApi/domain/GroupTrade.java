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
public class GroupTrade extends BaseDomain {

    private Long id;

    private Long groupId;

    private Long tradeUserId;

    private Long tradeAmount;

    private String tradeType;

    private Long auditUserId;

    private String auditStatus;

    private String auditFailReason;

    @JsonIgnore
    private Date updateTime;

    @JsonIgnore
    private Date createTime;
}