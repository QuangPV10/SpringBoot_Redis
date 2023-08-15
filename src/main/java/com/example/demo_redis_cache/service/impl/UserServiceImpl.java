package com.example.demo_redis_cache.service.impl;

import com.example.demo_redis_cache.entity.UserEntity;
import com.example.demo_redis_cache.repository.UserRepository;
import com.example.demo_redis_cache.service.UserService;
import com.example.demo_redis_cache.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity addUser(UserVO userVO) {
        UserEntity user = new UserEntity();
        user.setId(UUID.randomUUID().toString());
        user.setFirstName(userVO.getFirstName());
        user.setLastName(userVO.getLastName());
        user.setAge(userVO.getAge());
        user.setSex(userVO.getSex());

        userRepository.saveUser(user);

        return user;
    }

    @Override
    public UserEntity findUserById(String id) {
        UserEntity userEntity = userRepository.findUserById(id);
        if (!Objects.isNull(userEntity)) {
            return userEntity;
        }
        return null;
    }

    @Override
    public List<UserEntity> findAllUsers() {
        return userRepository.findAllUsers();
    }

    @Override
    public Boolean deleteUser(String id) {
        return userRepository.deleteUser(id);
    }

    @Override
    public UserEntity updateUser(UserVO userVO, String id) {
        UserEntity userEntity = userRepository.updateUser(userVO,id);
        if (!Objects.isNull(userEntity)) {
            return userEntity;
        }
        return null;
    }
}
