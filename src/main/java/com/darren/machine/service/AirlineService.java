package com.darren.machine.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.darren.machine.dao.AirlineDao;
import com.darren.machine.dao.AirlineRuleDao;
import com.darren.machine.dao.AirlineSourceDao;
import com.darren.machine.dao.TemplateDao;
import com.darren.machine.domain.AirlineEntity;
import com.darren.machine.domain.AirlineRuleEntity;
import com.darren.machine.domain.AirlineSourceEntity;
import com.darren.machine.domain.EChartObject;
import com.darren.machine.domain.TemplateEntity;

@Service
public class AirlineService
{
    
    private Logger log = LoggerFactory.getLogger(AirlineService.class);
    
    @Autowired
    private AirlineSourceDao airlineSourceDao;
    
    @Autowired
    private AirlineRuleDao airlineRuleDao;
    
    @Autowired
    private AirlineDao airlineDao;
    
    @Autowired
    private TemplateDao templateDao;
    
    public AirlineSourceEntity getAirlinesource(Long id)
    {
        AirlineSourceEntity entity = null;
        
        entity = airlineSourceDao.selectById(id);
        
        return entity;
    }
    
    public int saveAirlinesource(AirlineSourceEntity entity)
    {
        return airlineSourceDao.updateBy(entity);
    }
    
    public List<AirlineEntity> getAirlines(AirlineEntity entity)
    {
        return airlineDao.selectBy(entity);
    }
    
    public int deleteAirlines(Date date)
    {
        return airlineDao.deleteByDate(date);
    }
    
    public int saveAirlines(List<AirlineEntity> list)
    {
        if (list == null || list.size() == 0)
            return 0;
        
        try
        {
            for (AirlineEntity entity : list)
            {
                airlineDao.insertBy(entity);
            }
        }
        catch (Exception e)
        {
            log.error("batch save Airline: " + e.getMessage());
            return 0;
        }
        
        return 1;
    }
    
    public String getStatistics(AirlineEntity entity){
        List<EChartObject> params = new ArrayList<>();
        List<AirlineEntity> list= airlineDao.getStatistics(entity);
        for(AirlineEntity tmp:list){
          if("过站清洁".equalsIgnoreCase(tmp.getType())){
              params.add(new EChartObject("过站清洁", tmp.getAir_cnt()));
          }else if ("航后清洁".equalsIgnoreCase(tmp.getType())){
              params.add(new EChartObject("航后清洁", tmp.getAir_cnt()));
          }else if("航前清洁".equalsIgnoreCase(tmp.getType())){
              params.add(new EChartObject("航前清洁", tmp.getAir_cnt()));
          }
        }
        return JSON.toJSONString(params,true);
    }
    
    public AirlineRuleEntity getAirlineRule(AirlineRuleEntity entity)
    {
        try
        {
            return airlineRuleDao.selectBy(entity).get(0);
        }
        catch (Exception e)
        {
            AirlineRuleEntity entity2=  new AirlineRuleEntity();
            entity.setName("flight_number");
            entity.setRule("MU%");
            return entity2;
        }
    }
    
    public int saveAirlineRule(AirlineRuleEntity entity)
    {   
        if(entity.getId() == null || entity.getId()<=0){
            return airlineRuleDao.insertBy(entity);
        }
        return airlineRuleDao.updateBy(entity);
    }
    
    public TemplateEntity getAirlineTemplate(Long id)
    {
        return templateDao.selectById(id);
    }
    
    public int saveAirlineTemplate(TemplateEntity entity)
    {
        return templateDao.updateBy(entity);
    }
    
    public List<AirlineEntity> dealData(List<AirlineEntity> entities){
        List<AirlineEntity> list = new ArrayList<AirlineEntity>();
        if(entities == null) return list;
        
        String device_number="";
        for(AirlineEntity entity:entities){
            if(StringUtils.isEmpty(device_number)){
                device_number = entity.getDevice_number();
            }else if (device_number.equals(entity.getDevice_number())){
                entity.setDevice_number("");
            }else{
                device_number = entity.getDevice_number();
            }
            
            list.add(entity);
        }
        
        return list;
        
    }
    public List<AirlineEntity> mockData()
    {
        List<AirlineEntity> list = new ArrayList<AirlineEntity>();
        
        list.add(new AirlineEntity("B1292", "MU2925", "南京", LocalTime.now(),
                "重庆", LocalTime.now(), "航后清洁"));
        list.add(new AirlineEntity("", "MU2925", "南京", LocalTime.now(), "重庆",
                LocalTime.now(), "航后清洁"));
        list.add(new AirlineEntity("", "MU2925", "南京", LocalTime.now(), "重庆",
                LocalTime.now(), "航后清洁"));
        list.add(new AirlineEntity("", "MU2925", "南京", LocalTime.now(), "重庆",
                LocalTime.now(), "航后清洁"));
        list.add(new AirlineEntity("B1028", "MU2925", "南京", LocalTime.now(),
                "重庆", LocalTime.now(), "航后清洁"));
        list.add(new AirlineEntity("", "MU2925", "南京", LocalTime.now(), "重庆",
                LocalTime.now(), "航后清洁"));
        list.add(new AirlineEntity("", "MU2925", "南京", LocalTime.now(), "重庆",
                LocalTime.now(), "航后清洁"));
        list.add(new AirlineEntity("", "MU2925", "南京", LocalTime.now(), "重庆",
                LocalTime.now(), "航后清洁"));
        list.add(new AirlineEntity("B1029", "MU2925", "南京", LocalTime.now(),
                "重庆", LocalTime.now(), "航后清洁"));
        list.add(new AirlineEntity("", "MU2925", "南京", LocalTime.now(), "重庆",
                LocalTime.now(), "航后清洁"));

        return list;
    }
    
    public static void main(String[] args){
        List<EChartObject> params = new ArrayList<>();
        params.add(new EChartObject("air_after_station_cnt", 12));
        params.add(new EChartObject("air_after_cnt", 12));
        params.add(new EChartObject("air_cnt", 12));
        
        System.out.println(JSON.toJSONString(params,true));
    }
}
