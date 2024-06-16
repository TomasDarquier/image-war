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
    public void createUser(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createUser'");
    }

    @Override
    public void addVote(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addVote'");
    }

    @Override
    @Transactional
    public boolean updateUsername(User user, String newUsername) {
        //check if the username is available
        if(userRepository.findByUsername(newUsername).isPresent()){
            return false;
        }

        User updatedUsername = user;
        updatedUsername.setUsername(newUsername);
        userRepository.save(updatedUsername);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteUser(User user) {

        //for security reasons all the user atributes are checked to be ok
        Optional<User> userToDelete = userRepository.findById(user.getId());
        if(userToDelete.isPresent() && userToDelete.get().equals(user)){
            userRepository.delete(user);
            return true;
        }

        return false;    
    }

}
