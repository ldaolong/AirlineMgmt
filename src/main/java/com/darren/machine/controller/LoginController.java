package com.darren.machine.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.darren.machine.domain.UserEntity;
import com.darren.machine.service.UserService;
import com.darren.machine.util.Constants;

@RestController
public class LoginController
{
    @Autowired
    private UserService userSerivce;
    
    @RequestMapping(path = "/airline", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView index()
    {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        
        return modelAndView;
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView LoginPage(HttpSession session) {
        
          ModelAndView modelAndView = new ModelAndView();
          
          if(session.getAttribute(Constants.SESSION_USER) ==null){
              modelAndView.setViewName("common/loginPage");
          }else{
              modelAndView.setViewName("index");
          }
       
        return modelAndView;
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView LoginPage(@ModelAttribute UserEntity user,HttpSession session) {
        
          ModelAndView modelAndView = new ModelAndView();
          modelAndView.setViewName("index");
          
         session.setAttribute(Constants.SESSION_USER, user);
          
        return modelAndView;
    }
    
    
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView registerPage() {
          ModelAndView modelAndView = new ModelAndView();
          
          modelAndView.addObject("user", new UserEntity());
          modelAndView.setViewName("common/registerPage");
          
        return modelAndView;
    }
    
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView registerPage(@ModelAttribute(value="user") UserEntity user) {
        String view="common/registerPage";
          ModelAndView modelAndView = new ModelAndView();
          
          if(validateUser(user,modelAndView)){
              List list = this.userSerivce.findByName(user.getUserName());
              if(list == null || list.size() ==0){
                  this.userSerivce.insert(user);
                  view="index";
              }else{
                  modelAndView.addObject("errorMsg", "用户名已存在");
              }
          }else{
             
          }
          modelAndView.setViewName(view);
        return modelAndView;
    }
    
    public boolean validateUser(UserEntity user,ModelAndView modelAndView){
        boolean isValidate= true;
        if(user != null && !user.getPassword().equals(user.getConfirmPassword())){
            modelAndView.addObject("errorMsg", "密码不一致。");
            isValidate = false;
        }
       
        
        return isValidate;
    }
    
    @RequestMapping(value = "/changepwd", method = RequestMethod.GET)
    public ModelAndView changePasswordPage() {
        
          ModelAndView modelAndView = new ModelAndView();
          modelAndView.setViewName("login/loginPage");
        return modelAndView;
    }
    
    @RequestMapping(value = "/changepwd", method = RequestMethod.POST)
    public ModelAndView changePasswordPage(@ModelAttribute UserEntity user) {
        
          ModelAndView modelAndView = new ModelAndView();
          modelAndView.setViewName("common/loginPage");
        return modelAndView;
    }
   
    
    @RequestMapping(value = "/identifyCode", method = RequestMethod.GET)
    public ModelAndView getIdentifyCodePage() {
        
          ModelAndView modelAndView = new ModelAndView();
          modelAndView.setViewName("common/loginPage");
        return modelAndView;
    }
    
    @RequestMapping(value = "/license", method = RequestMethod.GET)
    public ModelAndView lincensePage(HttpSession session) {
        
          ModelAndView modelAndView = new ModelAndView();
          modelAndView.setViewName("nolicense");
          
        return modelAndView;
    }
    
}
