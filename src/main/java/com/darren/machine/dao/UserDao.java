package com.darren.machine.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.darren.machine.domain.UserEntity;

@Mapper
public interface UserDao extends BaseDao<UserEntity> {

}