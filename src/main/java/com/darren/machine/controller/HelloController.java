package com.darren.machine.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.darren.machine.domain.AirlineEntity;
import com.darren.machine.domain.UserEntity;
import com.darren.machine.util.Constants;
import com.darren.machine.util.PageImpl;

@RestController
public class HelloController
{
    // @Value("${my.stage:unknown}")
    // private String stage;
    //  @Value("${my.jobs:worker}")
    //  private String jobs;
    
    @RequestMapping(path = "/", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView index()
    {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        
        return modelAndView;
    }
    
    @RequestMapping("/coffee")
    public String coffee()
    {
        return "coffee please";
    }
    
    @RequestMapping(path = "/testdata")
    public ModelAndView testdata()
    {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("20181107");
        
        return modelAndView;
    }
    
    @RequestMapping(path = "/flightDynamicAction")
    public ModelAndView flightDynamicAction()
    {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("flightDynamicAction");
        return modelAndView;
    }
    
    @RequestMapping("/list")
    public ModelAndView list()
    {
        ModelAndView modelAndView = new ModelAndView();
        
        modelAndView.setViewName("/test/list");
        
        return modelAndView;
    }
    
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public ModelAndView mainPage()
    {
        
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        
        List<UserEntity> userList = new ArrayList<UserEntity>();
        UserEntity user = new UserEntity();
        user.setUserName("Darren Li");
        user.setDepartment("Java D");
        user.setPhoneNumber("#16547892");
        user.setStatus("Active");
        userList.add(user);
        
        user = new UserEntity();
        user.setUserName("Daolong Li");
        user.setDepartment("Java B");
        user.setPhoneNumber("#16547892");
        user.setStatus("Block");
        userList.add(user);
        modelAndView.addObject("userList", userList);
        
        modelAndView.addObject("hello", "欢迎进入HTML页面");
        return modelAndView;
        //  return "redirect:/index.html";
    }
    
    @RequestMapping(value = "/getUserData", method = RequestMethod.GET)
    //  @ResponseBody
    public PageImpl getUserData(HttpServletRequest request,
            HttpServletResponse response)
    {
        
        List<UserEntity> userList = new ArrayList<UserEntity>();
        UserEntity user = new UserEntity();
        user.setUserId("100");
        user.setUserName("Darren Li");
        user.setDepartment("Java D");
        user.setPhoneNumber("#16547892");
        user.setStatus("Active");
        userList.add(user);
        
        for (int i = 0; i < 30; i++)
        {
            user = new UserEntity();
            user.setUserId(String.valueOf(i));
            user.setUserName("Daolong Li");
            user.setDepartment("Java B");
            user.setPhoneNumber("#16547892");
            user.setStatus("Block");
            userList.add(user);
        }
        PageImpl<UserEntity> page =  new PageImpl<UserEntity>("0","10");
       // List<AirlineEntity> airlineList = service.getAirlines(formEntity);
        page.setPageRows(userList);
        return page;
    }
}