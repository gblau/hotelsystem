package com.gblau.engine.mapper;

import java.util.List;

/**
 * Created by gblau on 2016-11-12.
 */
public interface Mapper<T> {
    int deleteByPrimaryKey(String id);
    int insert(T record);
    T selectByPrimaryKey(String id);
    List<T> selectAllElements();
    int updateByPrimaryKey(T record);
}
