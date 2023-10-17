package com.example.jpa.service;

import com.example.jpa.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void saveUser(UserEntity userEntity);

    Optional<UserEntity> getUser(String email);

    Optional<UserEntity> getReferenceUser(String email);

}
