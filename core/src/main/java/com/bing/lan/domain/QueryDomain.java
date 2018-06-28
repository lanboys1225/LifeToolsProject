package com.bing.lan.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Created by 蓝兵 on 2018/6/27.
 */
@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryDomain<T> extends BaseDomain {

    private int totalSize;
    private int pageSize = 10;
    private int currentPage = 1;
    private List<T> list;

    public QueryDomain(Integer pageSize, Integer currentPage) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }

    //current 从1开始
    @JsonIgnore
    public int getOffset() {
        int i = (currentPage - 1) * pageSize;
        if (i < 0) {
            i = 0;
        }
        return i;
    }

    public int getTotalPages() {

        int i = totalSize / pageSize;
        int j = totalSize % pageSize;
        if (j != 0) {
            i += 1;
        }
        return i;
    }
}
