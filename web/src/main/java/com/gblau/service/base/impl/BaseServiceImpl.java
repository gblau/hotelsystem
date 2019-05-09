package com.gblau.service.base.impl;

import com.gblau.service.base.BaseService;
import com.gblau.engine.mapper.Mapper;

import java.util.List;

/**
 * Created by gblau on 2016-11-12.
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {
    private Mapper<T> dao;

    public void setDataAccessObject(Mapper<T> dao) {
        this.dao = dao;
    }

    @Override
    public T findByPrimaryKey(String id) {
        return dao.selectByPrimaryKey(id);
    }

    @Override
    public List<T> findAllElements() {
        return dao.selectAllElements();
    }

    @Override
    public int deleteByPrimaryKey(String id) {
        return dao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(T record) {
        return dao.insert(record);
    }

    @Override
    public int updateByPrimaryKey(T record) {
        return dao.updateByPrimaryKey(record);
    }
}
