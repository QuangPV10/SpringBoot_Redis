package com.example.demo_redis_cache.service;

import com.example.demo_redis_cache.entity.UserEntity;
import com.example.demo_redis_cache.vo.UserVO;

import java.util.List;

public interface UserService {

    UserEntity addUser(UserVO userVO);

    UserEntity findUserById(String id);

    List<UserEntity> findAllUsers();

    Boolean deleteUser(String id);

    UserEntity updateUser(UserVO userVO, String id);
}
