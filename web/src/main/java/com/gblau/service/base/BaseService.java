package com.gblau.service.base;

import java.util.List;

/**
 * Created by gblau on 2016-11-12.
 */
public interface BaseService<T> {
    void setBaseMapper();

    int deleteByPrimaryKey(String id);
    int insert(T record);
    T findByPrimaryKey(String id);
    List<T> findAllElements();
    int updateByPrimaryKey(T record);
}