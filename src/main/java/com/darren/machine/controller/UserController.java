package com.darren.machine.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.darren.machine.domain.UserEntity;
import com.darren.machine.service.UserService;

@RestController
public class UserController
{
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/users")
    public UserEntity addUser(@RequestBody
    UserEntity user)
    {
        userService.insert(user);
       
        //TODO
        return user;
    }
    
    @PutMapping("/users/{id}")
    public UserEntity updateUser(@PathVariable(value = "id")
    Long userId, @Valid
    @RequestBody
    UserEntity user)
    {
        return userService.update(userId, user);
    }
    
    @GetMapping("/users")
    public List<UserEntity> getAll()
    {
        return userService.findAll();
    }
    
    @GetMapping("/users/{id}")
    public UserEntity findById(@PathVariable(value = "id", required = true)
    Long id)
    {
        return userService.findById(id);
    } // http://localhost:8080/user/2    @GetMapping("/users/findByUserId/{uId}")    public UserEntity findByUserId(@PathVariable(value = "uId",required = true) String uId) {    	return userService.findUserByUserId(uId);    }        // http://localhost:8080/user/"Jack"    @GetMapping("/users/findByName/{name}")    public List<UserEntity> findUserByName(@PathVariable(value = "name",required = true) String uname) {    	return userService.findUserByName(uname);    }    @DeleteMapping("/users/{id}")    public ResponseEntity<?> deleteUserById(@PathVariable(value = "id", required = true) long uId) {        return userService.deleteUserById(uId);    }    @PostMapping("/users/all")    public  ResponseEntity<?> saveAll(@Valid @RequestBody List<UserEntity> users){        userService.saveAll(users);        return ResponseEntity.ok().build();    }
}