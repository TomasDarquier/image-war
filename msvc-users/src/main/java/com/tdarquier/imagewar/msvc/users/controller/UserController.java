package com.tdarquier.imagewar.msvc.users.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tdarquier.imagewar.msvc.users.entity.User;
import com.tdarquier.imagewar.msvc.users.service.UserService;

import jakarta.validation.Valid;

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

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody User user){
        return userService.deleteUser(user) ?
        ResponseEntity.ok().build():
        ResponseEntity.notFound().build();
    }

    @PutMapping("/vote")
    public ResponseEntity<?> addVote(@RequestBody User user){
        return userService.addVote(user) ?
        ResponseEntity.ok().build():
        ResponseEntity.notFound().build();
    }

    @PutMapping("/change_username/{newUsername}")
    public ResponseEntity<?> changeUsername(@Valid @RequestBody User user, BindingResult bindingResult, @PathVariable String newUsername){
        return userService.updateUsername(user, newUsername) ?
        ResponseEntity.ok().build():
        ResponseEntity.notFound().build();
    }
}
