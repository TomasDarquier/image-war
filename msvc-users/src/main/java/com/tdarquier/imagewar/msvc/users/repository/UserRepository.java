package com.tdarquier.imagewar.msvc.users.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.tdarquier.imagewar.msvc.users.entity.User;


public interface UserRepository extends CrudRepository<User,Integer>{

    Optional<User> findByUsername(String username);
}
