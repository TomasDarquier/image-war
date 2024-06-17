package com.tdarquier.imagewar.msvc.users.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdarquier.imagewar.msvc.users.entity.User;
import com.tdarquier.imagewar.msvc.users.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean createUser(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createUser'");
    }

    @Override
    @Transactional
    public boolean addVote(User user) {

        if(!userIsConsistent(user)){
            return false;
        }

        User validUser = user; 
        validUser.setVotesDone(validUser.getVotesDone() + 1);
        userRepository.save(validUser);
        
        return true;
    }

    @Override
    @Transactional
    public boolean updateUsername(User user, String newUsername) {

        if(!userIsConsistent(user) ||
            getUserByUsername(newUsername).isPresent() ||
            user.isChangedUsername()){
            return false;
        }

        User validUser = user;
        validUser.setUsername(newUsername);
        validUser.setChangedUsername(true);
        userRepository.save(validUser);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteUser(User user) {

        if(!userIsConsistent(user)){
            return false;
        }

        userRepository.delete(user);
        return true;    
    }


    private boolean userIsConsistent(User user) {

        Optional<User> userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb.isEmpty()) {
            return false;
        }

        return user.equals(userFromDb.get());
    }

}
