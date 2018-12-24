package com.darren.machine.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.darren.machine.domain.AirlineEntity;

@Mapper
public interface AirlineDao extends BaseDao<AirlineEntity> {

    public List<AirlineEntity> selectByDevice(String device_number);
    public List<AirlineEntity> getStatistics(AirlineEntity entity);
    public int deleteByDate(Date date);
}