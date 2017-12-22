package com.fly.chat.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * @author Fly
 * @Description
 * @Date Created in 10:07 2017/10/27
 * @Modified by
 */
public class Query implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private int offset;

    @JsonIgnore
    private int limit;

    /**
     * 升序或者降序
     */
    @JsonIgnore
    private String order;

    /**
     * 排序的字段名
     */
    @JsonIgnore
    private String sort;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @JsonIgnore
    public int getPageIndex(){
        return this.offset / this.limit;
    }
}
