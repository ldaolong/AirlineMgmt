package com.darren.machine.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.darren.machine.domain.UserEntity;
import com.darren.machine.util.Constants;

/**
 * 
 * @author DARRENLI
 *
 */
@RestController
public class MenuController
{
    
    @RequestMapping(value = "/navbar", method = RequestMethod.GET)
    public ModelAndView navbarPage()
    {
        //@SessionAttribute UserEntity session_user
        ModelAndView modelAndView = new ModelAndView();
     //   if(session_user == null) session_user= new UserEntity();
     //   modelAndView.addObject(Constants.SESSION_USER, session_user);
        
        modelAndView.setViewName("common/navbar");
        
        return modelAndView;
    }
    
    
    @RequestMapping(value = "/footer", method = RequestMethod.GET)
    public ModelAndView footerPage()
    {
        
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("common/footerPage");
        return modelAndView;
    }
}
