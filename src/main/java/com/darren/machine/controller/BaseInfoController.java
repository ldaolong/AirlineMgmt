package com.darren.machine.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.darren.machine.domain.AirlineRuleEntity;
import com.darren.machine.domain.AirlineSourceEntity;
import com.darren.machine.domain.TemplateEntity;
import com.darren.machine.service.AirlineService;

@RestController
public class BaseInfoController
{
    private static Logger log = LoggerFactory.getLogger(BaseInfoController.class);
    
    @Autowired
    private AirlineService service;
    
    
    @RequestMapping(value = "/airlineSource", method = RequestMethod.GET)
    public ModelAndView datasourcesPage()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("entity", service.getAirlinesource((long) 1));
        modelAndView.setViewName("base/datasources");
        
        return modelAndView;
    }
    
    @RequestMapping(value = "/airlineSource", method = RequestMethod.POST)
    public ModelAndView registerPage(@ModelAttribute(value = "entity")
    AirlineSourceEntity entity)
    {
        String view = "base/datasources";
        ModelAndView modelAndView = new ModelAndView();
        
        try
        {
            service.saveAirlinesource(entity);
            modelAndView.addObject("successMsg", "保存数据成功");
        }
        catch (Exception e)
        {
            modelAndView.addObject("errorMsg", "保存数据失败");
            log.error(e.getMessage());
        }
        modelAndView.setViewName(view);
        return modelAndView;
    }
    
    @RequestMapping(value = "/template", method = RequestMethod.GET)
    public ModelAndView templatePage()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("entity", service.getAirlineTemplate((long) 1));
        modelAndView.setViewName("base/templatePage");
        
        return modelAndView;
    }
    
    @RequestMapping(value = "/template", method = RequestMethod.POST)
    public ModelAndView templatePage(@ModelAttribute(value = "entity")
    TemplateEntity entity)
    {
        String view = "base/templatePage";
        ModelAndView modelAndView = new ModelAndView();
        
        try
        {
            service.saveAirlineTemplate(entity);
            modelAndView.addObject("successMsg", "保存数据成功");
        }
        catch (Exception e)
        {
            modelAndView.addObject("errorMsg", "保存数据失败");
            log.error(e.getMessage());
        }
        modelAndView.setViewName(view);
        return modelAndView;
    }
    
    @RequestMapping(value = "/biz_rule", method = RequestMethod.GET)
    public ModelAndView bizRulePage()
    {
        ModelAndView modelAndView = new ModelAndView();
        try
        {
            modelAndView.addObject("entity", service.getAirlineRule(new AirlineRuleEntity((long) 1)));
        }
        catch (Exception e)
        {
            AirlineRuleEntity entity=  new AirlineRuleEntity();
            entity.setName("flight_number");
            entity.setRule("MU%");
            modelAndView.addObject("entity",entity);
        }
        modelAndView.setViewName("base/bizRulePage");
        
        return modelAndView;
    }
    
    @RequestMapping(value = "/biz_rule", method = RequestMethod.POST)
    public ModelAndView bizRulePage(@ModelAttribute(value = "entity")
    AirlineRuleEntity entity)
    {
        String view = "base/bizRulePage";
        ModelAndView modelAndView = new ModelAndView();
        
        try
        {
            service.saveAirlineRule(entity);
            modelAndView.addObject("successMsg", "保存数据成功");
        }
        catch (Exception e)
        {
            modelAndView.addObject("errorMsg", "保存数据失败");
            log.error(e.getMessage());
        }
        modelAndView.setViewName(view);
        return modelAndView;
    }
}
