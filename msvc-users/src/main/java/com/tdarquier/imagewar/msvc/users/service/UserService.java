package com.tdarquier.imagewar.msvc.users.service;

import java.util.Optional;

import com.tdarquier.imagewar.msvc.users.entity.User;

public interface UserService {

    Optional<User> getUserByUsername(String username);

    boolean createUser(User user);

    boolean addVote(User user);

    boolean updateUsername(User user, String newUsername);

    boolean deleteUser(User user);

}
