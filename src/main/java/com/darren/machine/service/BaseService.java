package com.darren.machine.service;

import java.util.List;

import com.darren.machine.dao.BaseDao;


public abstract class BaseService<BaseVO> {
    public abstract BaseDao<BaseVO> getBaseDao();
    
    public List<BaseVO> findAll(){
        return getBaseDao().selectAll();
    }
    public BaseVO findById(Long id){
        return getBaseDao().selectById(id);
    }
    
    public List<BaseVO> findBy(BaseVO t){
        return getBaseDao().selectBy(t);
    }
    
    public List<BaseVO> findByName(String name){
        return getBaseDao().selectByName(name);
    }
    
    public BaseVO update(Long id,BaseVO baseObject){
         getBaseDao().updateBy(baseObject);
         return  getBaseDao().selectById(id);
    }
    public int deleteById(Long id){
        return getBaseDao().deleteById(id);
    }
    
    public int deleteBy(BaseVO t){
        return getBaseDao().deleteBy(t);
    }
    public int insert(BaseVO baseObject){
        return getBaseDao().insertBy(baseObject);
         
    }
    
    @SuppressWarnings("unchecked")
    public PageHelper.Page<BaseVO> findPageData(BaseVO t, int pageNumber,  
            int pageSize){
        PageHelper.startPage(pageNumber,pageSize);  
        getBaseDao().selectBy(t);  
        return PageHelper.endPage();
    } 
}