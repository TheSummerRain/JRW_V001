package com.jryz.core.free;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pagination <T> implements java.io.Serializable {

    private static final long serialVersionUID = 8672765984400992911L;
    private Integer curPage = 1;
    private Integer pageSize = 100;
    private Integer totalPage = 0;
    private Integer totalCount = 0;
    private List<T> items;
    private Map<String, Object> param = new HashMap<String, Object>(); // 分页以外的 其他参数
    private Map<String, String> orderBy = new HashMap<>(); // key 为 asc)||desc 缺省 desc

    public Map<String, String> getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Map<String, String> orderBy) {
        this.orderBy = orderBy;
    }

    public Map<String, Object> getParam() {
        return param;
    }

    public void setParam(Map<String, Object> param) {
        this.param = param;
    }

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
