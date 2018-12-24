package com.darren.machine.controller;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.darren.machine.domain.AirlineEntity;
import com.darren.machine.domain.AirlineRuleEntity;
import com.darren.machine.report.AirlineReport;
import com.darren.machine.service.AirlineService;
import com.darren.machine.util.DateUtils;
import com.darren.machine.util.HTMLPageParser;
import com.darren.machine.util.PageImpl;

@RestController
public class DataDealController
{
 private final Logger log = LoggerFactory.getLogger(DataDealController.class);
    @Autowired
    private AirlineService service;
    
    @RequestMapping(value = "/import", method = RequestMethod.GET)
    public ModelAndView importPage()
    {
        ModelAndView modelAndView = new ModelAndView();
        AirlineEntity airline = new AirlineEntity();
        airline.setAirline_date(new Date());
        modelAndView.addObject("entity", airline);
        modelAndView.setViewName("imp/impPage");
        
        return modelAndView;
    }
    
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public ModelAndView importPage(@ModelAttribute(value = "entity")
    AirlineEntity formEntity)
    {
        String view = "imp/impPage";
        ModelAndView modelAndView = new ModelAndView();
        
        try
        {
            AirlineRuleEntity rule =service.getAirlineRule(service.getAirlineRule(new AirlineRuleEntity((long) 1)));
            setrules(rule,formEntity);
            List<AirlineEntity> airlineList = HTMLPageParser.parseAirlineUrl(service.getAirlinesource((long)1),rule,formEntity.getAirline_date());
            if(airlineList.size()>0){
                service.deleteAirlines(formEntity.getAirline_date());
                service.saveAirlines(airlineList);
            }
           
//            service.saveAirlines(fetchData(formEntity));
            modelAndView.addObject("successMsg", "保存数据成功");
        }
        catch (Exception e)
        {
            modelAndView.addObject("errorMsg", "保存数据失败");
        }
        modelAndView.setViewName(view);
        return modelAndView;
    }
    
    public List<AirlineEntity> fetchData(AirlineEntity formEntity){
        List<AirlineEntity> list = new ArrayList<AirlineEntity>();
        list.add(new AirlineEntity("B1292", "MU2925", "南京", LocalTime.now(), "重庆",
                LocalTime.now(), "航后清理",new Date()));
        list.add(new AirlineEntity("", "MU2925", "南京", LocalTime.now(), "重庆",
                LocalTime.now(), "航前清理",new Date()));
        list.add(new AirlineEntity("", "MU2925", "南京", LocalTime.now(), "重庆",
                LocalTime.now(), "过站清理",new Date()));
        
        
        return list;
    }
    
    @RequestMapping(value = "/impQuery", method = RequestMethod.POST)
    public ModelAndView impQueryPage(@ModelAttribute(value = "entity")
    AirlineEntity formEntity)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("dataGrid", new ArrayList<AirlineEntity>());
        modelAndView.setViewName("imp/impPage");
        
        return modelAndView;
    }
    
    @RequestMapping(value = "/ajaxImpData", method = RequestMethod.GET)
    public PageImpl getFlightData_imp(HttpServletRequest request,
            @ModelAttribute(value = "entity")
    AirlineEntity formEntity)
    {
        PageImpl<AirlineEntity> page =  new PageImpl<AirlineEntity>(request.getParameter("offset"),request.getParameter("limit"));
       // List<AirlineEntity> airlineList = service.getAirlines(formEntity);
        AirlineRuleEntity rule =service.getAirlineRule(service.getAirlineRule(new AirlineRuleEntity((long) 1)));
        setrules(rule,formEntity);
        List<AirlineEntity> airlineList = HTMLPageParser.parseAirlineUrl(service.getAirlinesource((long)1),rule,formEntity.getAirline_date());
        page.setPageRows(airlineList);
        return page;
    }
    
    @RequestMapping(value = "/ajaxExpData", method = RequestMethod.GET)
    public PageImpl getFlightData_exp(HttpServletRequest request,
            @ModelAttribute(value = "entity")
    AirlineEntity formEntity)
    {
        PageImpl<AirlineEntity> page =  new PageImpl<AirlineEntity>(request.getParameter("offset"),request.getParameter("limit"));
        
        List<AirlineEntity> airlineList = service.getAirlines(formEntity);
       
        page.setPageRows(airlineList);
        return page;
    }
    
    private void setrules(AirlineRuleEntity rule,AirlineEntity formEntity){
        if("device_number".equalsIgnoreCase(rule.getName())){
            formEntity.setDevice_number(rule.getRule());
        }else if ("flight_number".equalsIgnoreCase(rule.getName())){
            formEntity.setFlight_number(rule.getRule());
        }
    }
    
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public ModelAndView templatePage()
    {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());
        rightNow.add(Calendar.MONTH,-1);
        rightNow.add(Calendar.DAY_OF_YEAR,1);
        
        ModelAndView modelAndView = new ModelAndView();
        AirlineEntity airline = new AirlineEntity();
        airline.setDate_to(new Date());
        airline.setDate_from(rightNow.getTime());
        
        modelAndView.addObject("entity", airline);
        modelAndView.setViewName("exp/expPage");
        
        return modelAndView;
    }
    
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public void templatePage(@ModelAttribute(value = "entity")
    AirlineEntity entity, HttpServletResponse response)
    {
        ModelAndView modelAndView = new ModelAndView();
        String reportName="航班信息";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String from_date = sdf.format(entity.getDate_from());
        String to_date= sdf.format(entity.getDate_to());
        if(from_date.equals(to_date)){
            reportName = reportName+from_date;
        }else{
            reportName= reportName+"("+from_date+"-"+to_date+")";
        }
      
        try
        {
            log.error("1.before get data: " + DateUtils.getstr_current_date());
            AirlineReport report = new AirlineReport(service.getAirlineTemplate((long) 1).getFile_suffix(),reportName);
            log.error("2.after get data: " + DateUtils.getstr_current_date());
            report.generateReport(service.getAirlines(entity) );
            log.error("3.after generate: " + DateUtils.getstr_current_date());
            report.outToClient(response);
        }
        catch (Exception e)
        {
            modelAndView.addObject("errorMsg", "数据导出失败");
        }
    }
    
    @RequestMapping(value = "/expQuery", method = RequestMethod.POST)
    public ModelAndView expQueryPage(@ModelAttribute(value = "entity")
    AirlineEntity formEntity)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("dataGrid", new ArrayList<AirlineEntity>());
        modelAndView.setViewName("exp/expPage");
        
        return modelAndView;
    }
    
    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public ModelAndView statisticsPage()
    {
        ModelAndView modelAndView = new ModelAndView();
        AirlineEntity airline = new AirlineEntity();
        airline.setDate_from(new Date());
        airline.setDate_to(new Date());
        modelAndView.addObject("entity", airline);
        modelAndView.setViewName("exp/statisticsPage");
        
        return modelAndView;
    }
    
    @RequestMapping(value = "/ajaxStatistics", method = RequestMethod.GET)
    public String statisticsPage(@ModelAttribute(value = "entity")
    AirlineEntity entity)
    {
        return service.getStatistics(entity);
    }
    
}
