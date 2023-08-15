package com.example.demo_redis_cache.repository;

import com.example.demo_redis_cache.constant.Constant;
import com.example.demo_redis_cache.entity.UserEntity;
import com.example.demo_redis_cache.vo.UserVO;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class UserRepository {

    @Resource(name="redisTemplate")          // 'redisTemplate' is defined as a Bean in AppConfig.java
    private HashOperations<String, String, Object> hashOperations;

    public void saveUser(UserEntity userEntity) {
        try {
            hashOperations.put(Constant.CACHE_KEY, userEntity.getId(), userEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UserEntity findUserById(String id) {
        return (UserEntity) hashOperations.get(Constant.CACHE_KEY,id);
    }

    public List<UserEntity> findAllUsers() {
        List<UserEntity> list = new ArrayList<>();
        Map<String, Object> objectMap = hashOperations.entries(Constant.CACHE_KEY);

        for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
            list.add((UserEntity) entry.getValue());
        }
        return list;
    }

    public boolean deleteUser(String id) {
        return hashOperations.delete(Constant.CACHE_KEY,id) == 1L;
    }

    public UserEntity updateUser(UserVO userVO, String id) {
        UserEntity user = (UserEntity) hashOperations.get(Constant.CACHE_KEY,id);
        if (!Objects.isNull(user)) {
            user.setFirstName(userVO.getFirstName());
            user.setLastName(userVO.getLastName());
            user.setAge(userVO.getAge());
            user.setSex(userVO.getSex());

            hashOperations.put(Constant.CACHE_KEY,id,user);
            return user;
        }
      return null;
    }

}
