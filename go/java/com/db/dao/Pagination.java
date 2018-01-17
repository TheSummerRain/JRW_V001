package com.db.dao;

import java.util.List;

public class Pagination implements java.io.Serializable {

    private static final long serialVersionUID = 8672765984400992911L;
    private Integer curPage = 1;
    private Integer pageSize = 100;
    private Integer totalPage = 0;
    private Integer totalCount = 0;
    private List items;

    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
        this.totalPage = (int) Math.ceil(totalCount / (double) pageSize);
    }

    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }

}
