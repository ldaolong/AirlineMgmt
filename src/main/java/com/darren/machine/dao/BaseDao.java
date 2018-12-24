package com.darren.machine.dao;

import java.util.List;

public interface BaseDao<T> {
    public List<T> selectAll();
    public T selectById(Long id);
    public List<T> selectByName(String name);
    public List<T> selectBy(T t);
    
    public int updateBy(T entity);
    public int deleteById(Long id);
    public int deleteBy(T t);
    public int removeBy(T t);
    
    public int insertBy(T entity);
}