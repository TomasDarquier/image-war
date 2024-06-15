package com.tdarquier.imagewar.msvc.users.repository;

import org.springframework.data.repository.CrudRepository;

import com.tdarquier.imagewar.msvc.users.entity.User;


public interface UserRepository extends CrudRepository<User,Integer>{

    User findByUsername(String username);
}
