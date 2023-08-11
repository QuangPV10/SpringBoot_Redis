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
    private UserRepository userRepository;

    @Override
    public UserEntity addUser(UserVO userVO) {
        if (!Objects.isNull(userVO)) {
            UserEntity user = new UserEntity();
            user.setId(UUID.randomUUID().toString());
            user.setFirstName(userVO.getFirstName());
            user.setLastName(userVO.getLastName());
            user.setAge(userVO.getAge());
            user.setSex(userVO.getSex());

            userRepository.saveUser(user);

            return user;
        }
        return null;
    }

    @Override
    public UserEntity findUserById(String id) {
        return userRepository.findUserById(id);
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
        return userRepository.updateUser(userVO,id);
    }
}
