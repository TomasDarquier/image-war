package com.tdarquier.imagewar.msvc.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tdarquier.imagewar.msvc.users.entity.User;
import com.tdarquier.imagewar.msvc.users.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

    //replace by service    
    @Autowired
    UserRepository userRepository;

    @GetMapping("/byUsername/{username}")
    public User getUserByUsername(@PathVariable String username){
        //return userRepository.findByUsername(username); 
        return userRepository.findByUsername(username);
    }
}
