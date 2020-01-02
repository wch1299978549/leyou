package com.leyou.common.po;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {
    private Long total;//一共有多少条数据
    private Long totalPage;//一共有多少页
    private List<T> items;//当前页数据

    public PageResult() {
    }

    public PageResult(Long total, Long totalPage, List<T> items) {
        this.total = total;
        this.totalPage = totalPage;
        this.items = items;
    }

}
