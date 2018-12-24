package com.darren.machine.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class FileUploadController
{
    private final Logger log =LoggerFactory.getLogger(FileUploadController.class);
    
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public ModelAndView uploadPage(HttpSession session) {
        
          ModelAndView modelAndView = new ModelAndView();
          modelAndView.setViewName("index");
        return modelAndView;
    }
    
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView uploadPage( @RequestParam("fileName") MultipartFile file, Model model) {
        if (file.isEmpty()){
            model.addAttribute("message", "The file is empty!");
        }
        try{
            byte[] bytes = file.getBytes();
            Path path = Paths.get("C:\\tmp/" + file.getOriginalFilename());
            Files.write(path, bytes);
            model.addAttribute("message", "succes");

        }catch (Exception e){
            log.error(e.getMessage());
        }
        
          ModelAndView modelAndView = new ModelAndView();
          modelAndView.setViewName("index");
          
        return modelAndView;
    }
    
    
    
    
}
