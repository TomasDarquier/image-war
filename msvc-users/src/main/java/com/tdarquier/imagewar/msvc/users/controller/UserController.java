package com.tdarquier.imagewar.msvc.users.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tdarquier.imagewar.msvc.users.entity.User;
import com.tdarquier.imagewar.msvc.users.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/byUsername/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username){

        Optional<User> user = userService.getUserByUsername(username);

        return user.isPresent()?
        ResponseEntity.ok(user.get()):
        ResponseEntity.notFound().build();
    }
}
