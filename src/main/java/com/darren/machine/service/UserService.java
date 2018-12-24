package com.darren.machine.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.darren.machine.dao.BaseDao;
import com.darren.machine.dao.UserDao;
import com.darren.machine.domain.UserEntity;
import com.darren.machine.exception.ResourceNotFoundException;

/**
 */
@Service
public class UserService extends BaseService<UserEntity> {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    
    @Autowired
    private UserDao userDao;    
    
    @Transactional
    @PostConstruct
    public void init() {

    }

    @Override
    public BaseDao getBaseDao()
    {
        return userDao;
    }
}
